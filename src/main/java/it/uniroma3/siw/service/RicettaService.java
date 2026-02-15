package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Transactional
    public Ricetta saveRicetta(Ricetta ricetta) {
        return this.ricettaRepository.save(ricetta);
    }

    // salva ricetta con autore e ingredienti
    @Transactional
    public Ricetta saveRicetta(Ricetta ricetta, Utente autore) {
        ricetta.setAutore(autore);
        Ricetta savedRicetta = this.ricettaRepository.save(ricetta);

        // Salva gli ingredienti associandoli alla ricetta
        if (ricetta.getIngredienti() != null) {
            for (Ingrediente ingrediente : ricetta.getIngredienti()) {
                if (ingrediente.getNome() != null && !ingrediente.getNome().trim().isEmpty()) {
                    ingrediente.setRicetta(savedRicetta);
                    this.ingredienteRepository.save(ingrediente);
                }
            }
        }

        return savedRicetta;
    }

    @Transactional
    public Ricetta getRicetta(Long id) {
        return this.ricettaRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Ricetta> getAllRicette() {
        return this.ricettaRepository.findAll();
    }

    public long countRicette() {
        return this.ricettaRepository.count();
    }

    @Transactional
    public List<Ricetta> searchRicetteGlobal(String keyword) {
        return this.ricettaRepository
                .findByTitoloContainingIgnoreCaseOrDescrizioneContainingIgnoreCaseOrCategoriaContainingIgnoreCase(
                        keyword, keyword, keyword);
    }

    @Transactional
    public List<Ricetta> searchRicetteDifficoltà(String difficoltà) {
        return this.ricettaRepository.findByDifficoltàContainingIgnoreCase(difficoltà);
    }

    @Transactional
    public void deleteRicetta(Long id) {
        this.ricettaRepository.deleteById(id);
    }

    // Aggiorna una ricetta esistente con i nuovi ingredienti
    @Transactional
    public Ricetta updateRicetta(Ricetta ricettaEsistente, List<Ingrediente> nuoviIngredienti) {
        // Rimuovi gli ingredienti esistenti
        if (ricettaEsistente.getIngredienti() != null) {
            this.ingredienteRepository.deleteAll(ricettaEsistente.getIngredienti());
            this.ingredienteRepository.flush(); // Force immediate deletion to free up IDs
            ricettaEsistente.getIngredienti().clear();
        }

        // Salva la ricetta aggiornata
        Ricetta savedRicetta = this.ricettaRepository.save(ricettaEsistente);

        // Salva i nuovi ingredienti
        if (nuoviIngredienti != null) {
            for (Ingrediente ingrediente : nuoviIngredienti) {
                if (ingrediente.getNome() != null && !ingrediente.getNome().trim().isEmpty()) {
                    ingrediente.setRicetta(savedRicetta);
                    ingrediente.setId(null);
                    this.ingredienteRepository.save(ingrediente);
                }
            }
        }

        return savedRicetta;
    }

}
