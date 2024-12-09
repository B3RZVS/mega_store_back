package com.tpi_pais.mega_store.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración de seguridad de la aplicación.
 * Configura el filtro de seguridad, las políticas CORS y el codificador de contraseñas.
 */
@Configuration
@EnableAspectJAutoProxy // Activa el soporte de AspectJ para AOP (Programación Orientada a Aspectos)
public class SecurityConfig {

    /**
     * Configura los filtros de seguridad, incluyendo reglas para habilitar o denegar acceso a ciertas rutas.
     *
     * @param http Objeto HttpSecurity para configurar reglas de seguridad HTTP.
     * @return Configuración de seguridad para la aplicación web.
     * @throws Exception Si ocurre algún error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desactiva la protección CSRF, útil para pruebas o APIs sin sesiones.
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/products/**").permitAll() // Permite el acceso público a las rutas de productos.
                        .requestMatchers("/auth/**").permitAll() // Permite el acceso público a las rutas de autenticación.
                        .anyRequest().permitAll() // Permite el acceso público a cualquier otra ruta.
                )
                .cors(Customizer.withDefaults()); // Activa CORS con la configuración por defecto.

        return http.build(); // Construye y devuelve la configuración de seguridad.
    }

    /**
     * Configura la política CORS para permitir solicitudes desde orígenes específicos.
     *
     * @return La fuente de configuración de CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite el acceso desde las direcciones específicas (en este caso, las direcciones de desarrollo frontend)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173"));
        // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
        configuration.setAllowedMethods(List.of("*"));
        // Permite los headers de autorización y content-type.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // Registra la configuración de CORS para todas las rutas de la aplicación.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source; // Devuelve la configuración de CORS.
    }

    /**
     * Configura el codificador de contraseñas para la aplicación.
     *
     * @return El codificador de contraseñas BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Utiliza BCryptPasswordEncoder para la encriptación de contraseñas.
    }
}
