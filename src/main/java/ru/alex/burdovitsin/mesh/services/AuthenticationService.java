package ru.alex.burdovitsin.mesh.services;

public interface AuthenticationService {
    String createAuthenticationToken(String username, String password);
}
