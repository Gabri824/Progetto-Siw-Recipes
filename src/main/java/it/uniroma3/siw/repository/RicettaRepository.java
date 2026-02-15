package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;

public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

    // Trova tutte le ricette scritte da un certo Utente
    List<Ricetta> findByAutore(Utente autore);

    // Barra di ricerca: Trova ricette dal titolo
    // Containing = LIKE %titolo% (cerca anche parole parziali)
    // IgnoreCase = se cerchi "PASTA" trova anche "pasta"
    List<Ricetta> findByTitoloContainingIgnoreCaseOrDescrizioneContainingIgnoreCaseOrCategoriaContainingIgnoreCase(
            String titolo, String descrizione, String categoria);

    List<Ricetta> findByDifficoltàContainingIgnoreCase(String difficoltà);
}
