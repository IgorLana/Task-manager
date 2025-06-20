// Localização: src/main/java/com/tarefas/security/SecurityConfig.java
package com.tarefas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    // O Spring irá injetar seu JwtFilter aqui
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Habilita e configura o CORS usando o Bean 'corsConfigurationSource' abaixo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Define a política de gerenciamento de sessão como stateless, essencial para APIs REST com JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define as regras de autorização para os endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        // Qualquer outra requisição para /api/** precisa de autenticação
                        .requestMatchers("/api/**").authenticated()
                        // Qualquer outra requisição pode ser permitida ou negada conforme sua regra
                        .anyRequest().permitAll()
                )
                // Adiciona seu filtro JWT antes do filtro padrão de autenticação do Spring
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean que define as configurações de CORS para a aplicação.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite requisições da origem do seu frontend Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        // Lista de métodos HTTP permitidos (incluindo OPTIONS para preflight)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Lista de cabeçalhos permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // Permite o envio de credenciais (necessário para cabeçalhos de autorização)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuração a todos os endpoints do projeto
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Bean para o codificador de senhas.
     * (Movido de PasswordEncoderConfig para cá)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para o gerenciador de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}