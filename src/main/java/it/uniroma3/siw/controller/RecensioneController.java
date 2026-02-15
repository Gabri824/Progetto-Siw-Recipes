package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.RicettaService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/ricetta/{id}/recensioni")
    public String listaRecensioni(Model model) {
        model.addAttribute("recensioni", this.recensioneService.getAllRecensioni());
        return "recensioni";
    }

    @GetMapping("/ricetta/{ricettaId}/formRecensione")
    public String showFormRecensione(@PathVariable("ricettaId") Long ricettaId, Model model) {
        model.addAttribute("recensione", new Recensione());
        model.addAttribute("ricetta", this.ricettaService.getRicetta(ricettaId));
        return "recensione/formRecensione";
    }

    @PostMapping("/ricetta/{ricettaId}/formRecensione")
    public String saveRecensione(@PathVariable("ricettaId") Long ricettaId,
            @ModelAttribute("recensione") Recensione recensione, Model model) {

        // Explicitly clear ID to prevent accidental updates if binding occurs
        recensione.setId(null);

        // Recupero ricetta
        Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);

        // Recupero Utente Loggato
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        Utente autore = credentials.getUtente();

        if (autore.isBanned()) {
            return "redirect:/ricetta/" + ricettaId;
        }

        // Verifica voto valido
        if (recensione.getVoto() < 1 || recensione.getVoto() > 5) {
            model.addAttribute("messaggioErrore", "Il voto deve essere compreso tra 1 e 5");
            model.addAttribute("ricetta", ricetta);
            return "recensione/formRecensione";
        }

        // Verifica se l'utente ha già recensito questa ricetta
        if (this.recensioneService.recensioneEsistentePerUtente(ricetta, autore)) {
            model.addAttribute("messaggioErrore", "Hai già recensito questa ricetta!");
            model.addAttribute("ricetta", ricetta);
            return "recensione/formRecensione";
        }

        try {
            this.recensioneService.saveRecensione(recensione, ricetta, autore);
        } catch (Exception e) {
            model.addAttribute("messaggioErrore", "Errore durante il salvataggio della recensione. Riprova.");
            model.addAttribute("ricetta", ricetta);
            return "recensione/formRecensione";
        }

        return "redirect:/ricetta/" + ricettaId;
    }

    @GetMapping("/ricetta/{id}/recensione/delete/{recensioneId}")
    public String deleteRecensione(@PathVariable("id") Long ricettaId, @PathVariable("recensioneId") Long recensioneId,
            @RequestParam(required = false) Long fromProfile, Model model, RedirectAttributes redirectAttributes) {
        Recensione recensione = this.recensioneService.getRecensione(recensioneId);

        if (!isAuthorOrAdmin(recensione)) {
            redirectAttributes.addFlashAttribute("messaggioErrore",
                    "Non hai i permessi per cancellare questa recensione");
            return "redirect:/ricetta/" + ricettaId;
        }

        this.recensioneService.deleteRecensione(recensioneId);
        redirectAttributes.addFlashAttribute("messaggioSuccesso", "Recensione cancellata con successo");

        // Se eliminato dal profilo, torna al profilo
        if (fromProfile != null) {
            return "redirect:/utente/" + fromProfile;
        }
        return "redirect:/ricetta/" + ricettaId;
    }

    @GetMapping("/ricetta/{id}/recensione/edit/{recensioneId}")
    public String showFormEditRecensione(@PathVariable("id") Long idRicetta,
            @PathVariable("recensioneId") Long recensioneId, Model model, RedirectAttributes redirectAttributes) {
        Recensione recensione = this.recensioneService.getRecensione(recensioneId);

        if (!isAuthorOrAdmin(recensione)) {
            redirectAttributes.addFlashAttribute("messaggioErrore",
                    "Non hai i permessi per modificare questa recensione");
            return "redirect:/ricetta/{id}/recensione/" + recensioneId;
        }

        model.addAttribute("recensione", recensione);
        return "recensione/formModificaRecensione";
    }

    @PostMapping("/ricetta/{id}/recensione/edit/{recensioneId}")
    public String updateRecensione(@PathVariable("id") Long idRicetta, @PathVariable("recensioneId") Long recensioneId,
            @ModelAttribute("recensione") Recensione recensioneForm, Model model,
            RedirectAttributes redirectAttributes) {
        Recensione recensioneEsistente = this.recensioneService.getRecensione(recensioneId);

        if (!isAuthorOrAdmin(recensioneEsistente)) {
            redirectAttributes.addFlashAttribute("messaggioErrore",
                    "Non hai permessi per modificare questa recensione");
            return "redirect:/ricetta/{id}/recensione/" + recensioneId;
        }

        recensioneEsistente.setTesto(recensioneForm.getTesto());
        recensioneEsistente.setVoto(recensioneForm.getVoto());

        this.recensioneService.saveRecensione(recensioneEsistente, this.ricettaService.getRicetta(idRicetta),
                recensioneEsistente.getAutore());

        return "redirect:/ricetta/" + idRicetta;
    }

    private boolean isAuthorOrAdmin(Recensione recensione) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        Utente utenteCorrente = credentials.getUtente();
        boolean isAdmin = credentials.getRole().equals("ADMIN");
        boolean isAuthor = recensione.getAutore().equals(utenteCorrente);
        return isAdmin || isAuthor;
    }
}
