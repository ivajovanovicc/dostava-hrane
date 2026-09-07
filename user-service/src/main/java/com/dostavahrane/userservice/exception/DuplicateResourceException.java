package com.dostavahrane.userservice.exception;

// Za poslovna pravila tipa "ovo vec postoji" - drugaciji HTTP status (409)
// od "nije nadjeno" (404). Konkretno kod nas: pokusaj kreiranja korisnika
// sa email-om koji vec postoji.
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
