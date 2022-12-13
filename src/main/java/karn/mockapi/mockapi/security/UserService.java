package karn.mockapi.mockapi.security;


import karn.mockapi.mockapi.entity.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getByUsername(String username);
}
