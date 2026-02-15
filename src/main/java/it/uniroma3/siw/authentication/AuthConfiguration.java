package it.uniroma3.siw.authentication;

import javax.sql.DataSource;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

        @Autowired
        private DataSource dataSource;

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        /*
         * Qui definiamo le query SQL per dire a Spring Security come cercare gli utenti
         * nel DB.
         */
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.jdbcAuthentication()
                                .dataSource(dataSource)
                                .passwordEncoder(passwordEncoder())
                                // Cerca utente per email e password dalla tabella credentials c
                                // Poi fai un JOIN con la tabella utente u
                                // Se u.banned = true, restituiamo 0 (disabilitato), altrimenti 1 (abilitato)
                                .usersByUsernameQuery(
                                                "SELECT c.email, c.password, CASE WHEN u.banned = true THEN 0 ELSE 1 END "
                                                                +
                                                                "FROM credentials c JOIN utente u ON c.utente_id = u.id "
                                                                +
                                                                "WHERE c.email = ?")
                                // Cerca il ruolo dell'utente
                                .authoritiesByUsernameQuery("SELECT email, role FROM credentials WHERE email = ?");
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests((requests) -> requests
                                                // Pagine pubbliche accessibile a tutti
                                                .requestMatchers("/", "/index", "/register", "/login", "/css/**",
                                                                "/images/**", "/elencoRicette", "/dettaglioRicetta",
                                                                "/error")
                                                .permitAll()
                                                // Visualizzazione ricette, immagini e recensioni pubblica (solo GET)
                                                .requestMatchers(HttpMethod.GET, "/ricetta/*", "/ricetta/*/immagine",
                                                                "/ricetta/*/recensioni")
                                                .permitAll()
                                                // Pagine solo per ADMIN
                                                .requestMatchers("/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
                                                // Tutto il resto richiede il login
                                                .anyRequest().authenticated())
                                .formLogin((form) -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/", true)
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout((logout) -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                .permitAll());

                return http.build();
        }

}
