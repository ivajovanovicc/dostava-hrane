package com.dostavahrane.orderservice.service;

import com.dostavahrane.orderservice.dto.*;
import com.dostavahrane.orderservice.event.OrderCreatedEvent;
import com.dostavahrane.orderservice.exception.ResourceNotFoundException;
import com.dostavahrane.orderservice.messaging.OrderEventPublisher;
import com.dostavahrane.orderservice.model.Order;
import com.dostavahrane.orderservice.model.OrderItem;
import com.dostavahrane.orderservice.model.OrderStatus;
import com.dostavahrane.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ExternalDataService externalDataService;
    private final OrderEventPublisher orderEventPublisher;

    // PROMENA: dodata podrazumevana vrednost ":250" posle dvotacke. Ako
    // Config Server iz BILO KOG razloga nije dostupan kad se ova aplikacija
    // pokrene (npr. spor start u Docker-u), aplikacija se VISE NECE RUSITI -
    // samo ce koristiti ovu vrednost umesto da padne u pokusaju. Ovo je
    // "mreza za slucaj pada", ne zamena za Config Server - kad je Config
    // Server dostupan (normalan slucaj), NJEGOVA vrednost i dalje pobedjuje.
    @Value("${delivery.default-fee:250}")
    private BigDecimal deliveryFee;

    public OrderService(OrderRepository orderRepository,
                         ExternalDataService externalDataService,
                         OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.externalDataService = externalDataService;
        this.orderEventPublisher = orderEventPublisher;
    }

    // ============================================================
    // POSLOVNA LOGIKA - osnovni CRUD
    // ============================================================

    public Order createOrder(OrderRequest request) {
        UserDto user = externalDataService.getUser(request.getUserId());
        RestaurantDto restaurant = externalDataService.getRestaurant(request.getRestaurantId());

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setRestaurantId(request.getRestaurantId());

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            MenuItemDto menuItem = externalDataService.getMenuItem(itemReq.getMenuItemId());

            if (menuItem.getRestaurant() == null
                    || !Objects.equals(menuItem.getRestaurant().getId(), request.getRestaurantId())) {
                throw new IllegalArgumentException(
                        "Jelo id=" + itemReq.getMenuItemId()
                                + " ne pripada restoranu id=" + request.getRestaurantId());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(menuItem.getId());
            orderItem.setMenuItemName(menuItem.getName());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setQuantity(itemReq.getQuantity());

            order.addItem(orderItem);

            total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        total = total.add(deliveryFee);

        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        orderEventPublisher.publishOrderCreated(new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getRestaurantId(),
                savedOrder.getTotalPrice()
        ));

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Porudzbina sa id=" + id + " ne postoji"));
    }

    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    // ============================================================
    // AGREGACIONI ENDPOINTI
    // ============================================================

    public OrderDetailsResponse getOrderDetails(Long orderId) {
        Order order = getOrderById(orderId);
        UserDto user = externalDataService.getUser(order.getUserId());
        RestaurantDto restaurant = externalDataService.getRestaurant(order.getRestaurantId());

        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus());
        response.setTotalPrice(order.getTotalPrice());
        response.setCreatedAt(order.getCreatedAt());
        response.setCustomerName(user.getFirstName() + " " + user.getLastName());
        response.setDeliveryAddress(user.getAddress());
        response.setRestaurantName(restaurant.getName());
        response.setItems(order.getItems());

        return response;
    }

    public List<OrderSummaryResponse> getOrderSummariesForUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderSummaryResponse> result = new ArrayList<>();

        for (Order order : orders) {
            RestaurantDto restaurant = externalDataService.getRestaurant(order.getRestaurantId());

            OrderSummaryResponse summary = new OrderSummaryResponse();
            summary.setOrderId(order.getId());
            summary.setRestaurantName(restaurant.getName());
            summary.setStatus(order.getStatus());
            summary.setTotalPrice(order.getTotalPrice());
            summary.setCreatedAt(order.getCreatedAt());

            result.add(summary);
        }

        return result;
    }
}
