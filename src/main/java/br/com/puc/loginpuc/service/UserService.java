package br.com.puc.loginpuc.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.puc.loginpuc.dto.RegisterForm;
import br.com.puc.loginpuc.model.User;
import br.com.puc.loginpuc.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(RegisterForm form) {
        User user = new User(
            form.getUsername(),
            form.getEmail(),
            passwordEncoder.encode(form.getPassword())
        );

        userRepository.save(user);
    }
}