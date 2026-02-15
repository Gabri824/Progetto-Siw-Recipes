package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Transactional
    public Ingrediente saveIngrediente(Ingrediente ingrediente) {
        return this.ingredienteRepository.save(ingrediente);
    }

    public Ingrediente getIngrediente(Long id) {
        return this.ingredienteRepository.findById(id).orElse(null);
    }

    public List<Ingrediente> getAllIngredienti() {
        return this.ingredienteRepository.findAll();
    }

    @Transactional
    public void deleteIngrediente(Long id) {
        this.ingredienteRepository.deleteById(id);
    }

}
