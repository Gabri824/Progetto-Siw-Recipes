package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    // Cerca le credenziali per email
    Optional<Credentials> findByEmail(String email); // Uso optional per evitare un null pointer exception

    // Serve in fase di registrazione per vedere se la mail è già occupata
    boolean existsByEmail(String email);
}
