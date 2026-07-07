package com.example.noteapp.note.config;

import com.example.noteapp.note.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username ->
                userRepository.findByEmail(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        .anyRequest()
                        .authenticated()
                )

                .authenticationProvider(authenticationProvider());

        return http.build();
    }

}

/*
===============================================================================
                        SPRING SECURITY CONFIGURATION
===============================================================================

What is SecurityConfig?

SecurityConfig is the central place where Spring Security is configured.
It tells Spring Security:

1. How users should be authenticated.
2. Where to find users.
3. How passwords are verified.
4. Which endpoints are public.
5. Which endpoints require login.
6. Whether to use Sessions or JWT.

Without this class, Spring Boot uses its default security configuration,
which:
- creates a default login page
- protects every endpoint
- creates HTTP Sessions
- doesn't know about our database or JWT.

===============================================================================
APPLICATION STARTUP
===============================================================================

When the application starts, Spring Boot scans the project.

            Spring Boot Starts
                    │
                    ▼
          Finds @Configuration
                    │
                    ▼
         Creates every @Bean object
                    │
                    ▼
        Security system becomes ready

Every method annotated with @Bean is executed ONCE when the application starts.
Spring stores the returned object inside its IoC Container and reuses it
whenever needed.

===============================================================================
USER REPOSITORY
===============================================================================

private final UserRepository userRepository;

Why do we inject UserRepository?

Authentication always starts by finding a user in the database.

Suppose someone logs in:

Email:
john@gmail.com

Password:
123456

Spring needs to ask:

"Does this email exist?"

To answer that question, Spring uses UserRepository.

Flow:

Login Request
      │
      ▼
UserRepository.findByEmail(email)
      │
      ▼
Database
      │
      ▼
Returns User or Empty

===============================================================================
USERDETAILSSERVICE
===============================================================================

@Bean
UserDetailsService userDetailsService()

This bean is one of the most important in Spring Security.

Spring Security NEVER talks directly to the database.

Instead it asks:

"Can someone load the user for me?"

UserDetailsService is responsible for loading users.

Our implementation:

return username ->
        userRepository.findByEmail(username)
                .orElseThrow(...);

Even though the parameter is called "username",
we're actually using EMAIL as the username.

So internally:

loadUserByUsername("john@gmail.com")

becomes

SELECT *
FROM users
WHERE email='john@gmail.com'

If found:

Return User

If not:

Throw UsernameNotFoundException

Our User entity implements UserDetails,
so Spring Security can use it directly.

===============================================================================
PASSWORD ENCODER
===============================================================================

@Bean
PasswordEncoder passwordEncoder()

Passwords should NEVER be stored as plain text.

BAD

Database

john@gmail.com
123456

If someone hacks the database,
they immediately know everyone's password.

Instead we use BCrypt.

User registers:

Password:

123456

↓

BCrypt

↓

$2a$10$kaflP....

Database stores ONLY the encrypted version.

During login:

User types

123456

↓

BCrypt checks

↓

Matches stored hash?

↓

Yes

The original password is NEVER decrypted.

BCrypt compares hashes.

===============================================================================
AUTHENTICATION PROVIDER
===============================================================================

@Bean
AuthenticationProvider authenticationProvider()

Think of AuthenticationProvider as the authentication engine.

It knows HOW to authenticate a user.

When a login request arrives:

Email
Password

↓

AuthenticationProvider

↓

Load user from database

↓

Retrieve encrypted password

↓

Use BCrypt to compare passwords

↓

Authenticated?
Yes or No

This bean needs TWO things.

1. UserDetailsService

Where are users stored?

Database.

2. PasswordEncoder

How were passwords encrypted?

BCrypt.

Without these,
Spring has no idea how to authenticate users.

===============================================================================
AUTHENTICATION MANAGER
===============================================================================

@Bean
AuthenticationManager authenticationManager()

Think of AuthenticationManager as the boss.

Your controller does NOT verify passwords itself.

Instead it says:

AuthenticationManager,
please authenticate this user.

Flow:

Login Request

↓

AuthenticationManager

↓

AuthenticationProvider

↓

UserDetailsService

↓

Database

↓

PasswordEncoder

↓

Authenticated

The AuthenticationManager coordinates everything.

===============================================================================
SECURITY FILTER CHAIN
===============================================================================

@Bean
SecurityFilterChain securityFilterChain()

This is the heart of Spring Security.

EVERY incoming HTTP request passes through this filter chain BEFORE reaching
your controller.

Flutter

↓

GET /notes

↓

Security Filter Chain

↓

Allowed?

↓

Controller

This is where Spring checks:

- Is the endpoint public?
- Is authentication required?
- Is there a JWT?
- Is the JWT valid?

===============================================================================
CSRF
===============================================================================

.csrf(AbstractHttpConfigurer::disable)

CSRF = Cross Site Request Forgery.

It protects browser-based applications.

Example:

You log into your bank.

Another website secretly submits

POST /transfer-money

using your browser session.

CSRF prevents this.

Our Flutter application DOES NOT use browser sessions.

Instead it sends

Authorization:
Bearer eyJhbGc...

Therefore CSRF protection is unnecessary.

===============================================================================
SESSION MANAGEMENT
===============================================================================

.sessionManagement(
session -> session.sessionCreationPolicy(STATELESS))

Traditional Spring Security works like this.

User logs in

↓

Server creates Session

↓

Browser receives JSESSIONID cookie

↓

Every request uses the Session

JWT works differently.

User logs in

↓

JWT generated

↓

Flutter stores JWT

↓

Flutter sends JWT on every request

↓

Server verifies JWT

↓

No Session stored

STATELESS means:

Never create Sessions.

===============================================================================
AUTHORIZE HTTP REQUESTS
===============================================================================

.authorizeHttpRequests(...)

This determines WHO can access WHICH endpoints.

Example:

.requestMatchers("/auth/**")
.permitAll()

means

/auth/login

/auth/register

/auth/verify

can be accessed WITHOUT logging in.

Why?

Because users need to register and log in before they have a JWT.

Everything else:

.anyRequest()
.authenticated()

means

/notes

/profile

/settings

require a valid JWT.

If JWT is missing:

401 Unauthorized

===============================================================================
AUTHENTICATION PROVIDER REGISTRATION
===============================================================================

.authenticationProvider(authenticationProvider())

This tells Spring:

Use MY AuthenticationProvider.

Without this line,
Spring would use its default provider.

===============================================================================
HTTP BUILD
===============================================================================

return http.build();

After adding all security rules,
Spring builds the Security Filter Chain.

Think of this as:

Configuration Complete

↓

Create Security System

↓

Application Ready

===============================================================================
COMPLETE LOGIN FLOW
===============================================================================

Flutter

↓

POST /auth/login

↓

AuthController

↓

AuthenticationManager

↓

AuthenticationProvider

↓

UserDetailsService

↓

UserRepository

↓

Database

↓

User Found?

↓

PasswordEncoder

↓

Password Correct?

↓

JwtService

↓

Generate JWT

↓

Return JWT

===============================================================================
COMPLETE REQUEST FLOW
===============================================================================

Flutter

↓

GET /notes

↓

Authorization:
Bearer eyJhbGc...

↓

JwtAuthenticationFilter

↓

JwtService

↓

Validate JWT

↓

Extract Email

↓

UserDetailsService

↓

Load User

↓

SecurityContext

↓

NoteController

===============================================================================
SUMMARY
===============================================================================

@Configuration
    Marks this class as a Spring configuration class.

@Bean
    Creates an object that Spring manages.

UserDetailsService
    Loads users from the database.

PasswordEncoder
    Encrypts and verifies passwords.

AuthenticationProvider
    Performs authentication.

AuthenticationManager
    Coordinates the authentication process.

SecurityFilterChain
    Protects all HTTP requests.

csrf().disable()
    Disables CSRF for REST APIs.

sessionCreationPolicy(STATELESS)
    Tells Spring not to use Sessions because JWT is used.

requestMatchers("/auth/**").permitAll()
    Allows public endpoints like login and register.

anyRequest().authenticated()
    Requires JWT for every other endpoint.

authenticationProvider(...)
    Registers our custom authentication provider.

http.build()
    Builds the complete security system.

===============================================================================
*/
