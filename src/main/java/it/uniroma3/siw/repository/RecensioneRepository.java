package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

    // Trova tutte le recensioni di una specifica ricetta
    List<Recensione> findByRicetta(Ricetta ricetta);

    // Trova tutte le recensioni scritte da un utente
    List<Recensione> findByAutore(Utente autore);

    boolean existsByAutoreAndRicetta(Utente autore, Ricetta ricetta);
}
