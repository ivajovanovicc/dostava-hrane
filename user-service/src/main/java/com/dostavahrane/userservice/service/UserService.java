package com.dostavahrane.userservice.service;

import com.dostavahrane.userservice.exception.DuplicateResourceException;
import com.dostavahrane.userservice.exception.ResourceNotFoundException;
import com.dostavahrane.userservice.model.User;
import com.dostavahrane.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Korisnik sa id=" + id + " ne postoji"));
    }

    public User createUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(existing -> {
            throw new DuplicateResourceException("Korisnik sa email-om " + user.getEmail() + " vec postoji");
        });
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedData) {
        User existing = getUserById(id);
        userRepository.findByEmail(updatedData.getEmail()).ifPresent(other -> {
            if (!other.getId().equals(existing.getId())) {
                throw new DuplicateResourceException("Korisnik sa email-om " + updatedData.getEmail() + " vec postoji");
            }
        });
        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setEmail(updatedData.getEmail());
        existing.setPhone(updatedData.getPhone());
        existing.setAddress(updatedData.getAddress());
        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        User existing = getUserById(id);
        userRepository.delete(existing);
    }
}
