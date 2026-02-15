package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class AdminController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private RecensioneService recensioneService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUtenti", this.utenteService.countUtenti());
        model.addAttribute("totalRicette", this.ricettaService.countRicette());
        model.addAttribute("totalRecensioni", this.recensioneService.countRecensioni());
        return "admin/dashboard";
    }

    @GetMapping("/admin/utenti")
    public String listaUtenti(Model model, @RequestParam(required = false) String keyword) {
        List<Utente> utenti;

        if (keyword != null && !keyword.isEmpty()) {
            utenti = this.utenteService.searchUtenti(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            utenti = this.utenteService.getAllUtenti();
        }

        model.addAttribute("utenti", utenti);
        return "admin/utenti";
    }

    @PostMapping("/admin/ban/{id}")
    public String banUtente(@PathVariable("id") Long idUtente) {
        Utente utente = this.utenteService.getUtente(idUtente);
        utente.setBanned(true);
        this.utenteService.saveUtente(utente);
        return "redirect:/admin/utenti";
    }

    @PostMapping("/admin/unban/{id}")
    public String unbanUtente(@PathVariable("id") Long idUtente) {
        Utente utente = this.utenteService.getUtente(idUtente);
        utente.setBanned(false);
        this.utenteService.saveUtente(utente);
        return "redirect:/admin/utenti";
    }

    @GetMapping("/admin/ricette")
    public String listaRicette(Model model) {
        model.addAttribute("ricette", this.ricettaService.getAllRicette());
        return "admin/ricette";
    }

    @GetMapping("/admin/ricette/{id}/delete")
    public String deleteRicetta(@PathVariable("id") Long idRicetta) {
        this.ricettaService.deleteRicetta(idRicetta);
        return "redirect:/admin/ricette";
    }
}
