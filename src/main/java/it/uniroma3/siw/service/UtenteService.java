package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public Utente saveUtente(Utente utente) {
        return this.utenteRepository.save(utente);
    }

    @Transactional
    public Utente getUtente(Long id) {
        return this.utenteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Utente getUtenteWithCollections(Long id) {
        Utente utente = this.utenteRepository.findById(id).orElse(null);
        if (utente != null) {
            // Inizializza le collezioni per evitare LazyInitializationException e problemi
            // con i LOB
            if (utente.getRicette() != null)
                utente.getRicette().size();
            if (utente.getRecensioni() != null)
                utente.getRecensioni().size();
        }
        return utente;
    }

    public List<Utente> getAllUtenti() {
        return this.utenteRepository.findAll();
    }

    public List<Utente> searchUtenti(String keyword) {
        return this.utenteRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(keyword, keyword);
    }

    public long countUtenti() {
        return this.utenteRepository.count();
    }
}
