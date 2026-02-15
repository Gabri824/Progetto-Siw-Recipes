package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Transactional
    public void saveRecensione(Recensione recensione, Ricetta ricetta, Utente autore) {
        recensione.setRicetta(ricetta);
        recensione.setAutore(autore);
        this.recensioneRepository.save(recensione);
    }

    @Transactional
    public void deleteRecensione(Long id) {
        this.recensioneRepository.deleteById(id);
    }

    public Recensione getRecensione(Long id) {
        return this.recensioneRepository.findById(id).orElse(null);
    }

    public List<Recensione> getAllRecensioni() {
        return this.recensioneRepository.findAll();
    }

    public long countRecensioni() {
        return this.recensioneRepository.count();
    }

    public boolean recensioneEsistentePerUtente(Ricetta ricetta, Utente autore) {
        return this.recensioneRepository.existsByAutoreAndRicetta(autore, ricetta);
    }
}
