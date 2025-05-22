// src/main/java/com/example/blog/config/CorsConfig.java
package com.example.blog.config; // <- Une seule déclaration de package ici

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indique que c'est une classe de configuration Spring
public class CorsConfig {

    @Value("${cors.allowed.origins}") // Récupère la valeur de 'cors.allowed.origins' depuis application.properties
    private String allowedOrigins;

    @Bean // Définit un bean géré par Spring
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Configure les règles CORS pour toutes les URL de l'API (/api/**)
                registry.addMapping("/api/**")
                        .allowedOrigins(allowedOrigins.split(",")) // Autorise les origines définies dans application.properties
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
                        .allowedHeaders("*") // Tous les en-têtes sont autorisés
                        .allowCredentials(true); // Autorise l'envoi de cookies d'authentification
            }
        };
    }
}
// <- Aucune accolade fermante supplémentaire ici