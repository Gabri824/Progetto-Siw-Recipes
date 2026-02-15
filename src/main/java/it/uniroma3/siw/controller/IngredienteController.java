package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.service.IngredienteService;

@Controller
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping("/ingrediente/delete/{id}")
    public String deleteIngrediente(@PathVariable("id") Long id) {
        Ingrediente ingrediente = this.ingredienteService.getIngrediente(id);
        Long idRicetta = ingrediente.getRicetta().getId();

        this.ingredienteService.deleteIngrediente(id);

        // Torna alla pagina di modifica della ricetta
        return "redirect:/ricetta/edit/" + idRicetta;
    }

}
