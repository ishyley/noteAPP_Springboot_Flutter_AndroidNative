package com.example.noteapp.note.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


// Configures Cross-Origin Resource Sharing (CORS) for the application.
@Configuration
public class CorsConfig {

    // Creates a CorsFilter bean managed by Spring.
    @Bean
    public CorsFilter corsFilter() {

        // Stores all CORS configurations.
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Holds the CORS rules.
        CorsConfiguration config =
                new CorsConfiguration();

        // Allows requests from the Flutter Web application.
        config.setAllowedOrigins(
                List.of("http://localhost:8005"));

        // Allows these HTTP methods.
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE"));

        // Allows these request headers.
        config.setAllowedHeaders(
                List.of("Authorization", "Content-Type"));

        // Allows credentials such as cookies or authentication information.
        config.setAllowCredentials(true);

        // Applies this configuration to every endpoint.
        source.registerCorsConfiguration("/**", config);

        // Creates the CorsFilter using the above configuration.
        return new CorsFilter();
    }

}

/*
===============================================================================
                            DETAILED EXPLANATION
===============================================================================

1. WHAT IS CORS?
----------------

CORS stands for Cross-Origin Resource Sharing.

To understand CORS, we first need to understand what an Origin is.

An Origin consists of three parts:

    Protocol + Domain + Port

Example:

    http://localhost:8005

    Protocol -> http
    Domain   -> localhost
    Port     -> 8005

Another example:

    http://localhost:8080

Although both use "localhost", they have different ports.

Therefore, they are considered DIFFERENT ORIGINS.

===============================================================================

2. WHY DOES CORS EXIST?
-----------------------

Imagine anyone on the internet could send requests to your bank account
using your browser.

That would be dangerous.

Modern browsers therefore implement something called the
Same-Origin Policy.

It says:

"A website can only communicate with the same origin it was loaded from."

Example:

Flutter Web
http://localhost:8005

tries to call

Spring Boot
http://localhost:8080

The browser notices that:

8005 != 8080

Different origin.

The browser blocks the request.

Not Spring Boot.

The Browser.

===============================================================================

3. HOW DOES CORS FIX THIS?
--------------------------

Spring Boot sends special HTTP headers telling the browser:

"I trust this website."

For example:

Access-Control-Allow-Origin:
http://localhost:8005

The browser then allows the request.

===============================================================================

4. WHAT IS CorsConfiguration?
-----------------------------

CorsConfiguration is simply an object that stores all CORS rules.

Think of it as a list of permissions.

It answers questions like:

Who may access my backend?

Which HTTP methods are allowed?

Which headers are allowed?

Can credentials be sent?

===============================================================================

5. WHAT IS UrlBasedCorsConfigurationSource?
------------------------------------------

This object stores one or more CorsConfiguration objects.

Imagine your application has many APIs.

You can configure different CORS rules.

Example:

/auth/**

Only allow login page.

--------------------------------

/admin/**

Only company website allowed.

--------------------------------

/public/**

Allow everyone.

In our project we use:

"/**"

which means

Every endpoint uses the same CORS rules.

===============================================================================

6. WHAT DOES setAllowedOrigins() DO?
------------------------------------

config.setAllowedOrigins(

    List.of("http://localhost:8005")

);

This tells Spring:

"If a request comes from localhost:8005,
allow it."

If the request comes from:

http://evil.com

it will be rejected by the browser.

===============================================================================

7. WHAT DOES setAllowedMethods() DO?
------------------------------------

HTTP methods describe what the client wants to do.

GET

Read data

POST

Create data

PUT

Update data

DELETE

Delete data

If Flutter sends:

PATCH

The browser rejects it because PATCH
was never allowed.

===============================================================================

8. WHAT DOES setAllowedHeaders() DO?
------------------------------------

Headers carry extra information.

Example:

Authorization

Contains the JWT.

Content-Type

Tells Spring the body is JSON.

Without allowing these headers,
the browser refuses to send them.

===============================================================================

9. WHAT DOES setAllowCredentials(true) DO?
------------------------------------------

Credentials include:

Cookies

HTTP Authentication

TLS Client Certificates

Our Notes App uses JWT.

JWT is normally sent inside the Authorization header,
not as a cookie.

Although this setting doesn't directly enable JWT,
it is commonly used when authenticated browser requests
need to include credentials.
*/
