package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    // Potrebbe servirti se vuoi cercare tutti gli ingredienti di una ricetta
    // senza caricare l'intero oggetto Ricetta (ottimizzazione)
    List<Ingrediente> findByRicetta(Ricetta ricetta);

}
