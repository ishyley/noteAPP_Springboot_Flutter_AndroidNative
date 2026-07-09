package com.example.noteapp.note.service;

import com.example.noteapp.note.dto.request.LoginRequest;
import com.example.noteapp.note.dto.request.RegisterRequest;
import com.example.noteapp.note.exception.EmailAlreadyExistsException;
import com.example.noteapp.note.model.EmailVerificationToken;
import com.example.noteapp.note.model.User;
import com.example.noteapp.note.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterRequest request) {
        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(
                    "Email already exists."
            );
        }
        User user = new User()
                .setEmail(request.getEmail())
                .setUsername(request.getUsername())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setEmailVerified(false);

//        userRepository.save(user);
//
//        String token = UUID.randomUUID().toString();
//
//        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
//
//        emailVerificationToken.setToken(token);
//        emailVerificationToken.setUser(user);
//        emailVerificationToken.setExpiryDate(LocalDateTime.now().plus(10));

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        //TODO: check whether the password is correct
        return userRepository.findByEmail(request.getEmail())
                .orElseThrow();
    }

    public List<User> allUser() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    /*
     users::add is called a method reference in Java.

It is simply a shorter way of writing a lambda expression.

For example:

users.forEach(user -> usersList.add(user));

can be written as:

users.forEach(usersList::add);

Both do exactly the same thing. */
}
