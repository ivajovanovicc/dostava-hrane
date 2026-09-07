package com.dostavahrane.userservice.exception;

// Nasa SOPSTVENA klasa greske, umesto opste RuntimeException. Prednost:
// GlobalExceptionHandler moze da je PREPOZNA po TIPU i zna tacno koji HTTP
// status da vrati (404) - sa golom RuntimeException, ne bismo mogli da
// razlikujemo "nije nadjeno" od "nesto sasvim drugo je puklo".
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
