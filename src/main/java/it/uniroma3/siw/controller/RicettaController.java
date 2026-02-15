package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RicettaService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private CredentialsService credentialsService;

    // Mostra la lista di tutte ricette
    @GetMapping("/elencoRicette")
    public String listaRicette(Model model, @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String difficoltà) {

        List<Ricetta> ricette;

        if (keyword != null && !keyword.isEmpty()) {
            ricette = this.ricettaService.searchRicetteGlobal(keyword);
            model.addAttribute("keyword", keyword);
        } else if (difficoltà != null && !difficoltà.isEmpty()) {
            ricette = this.ricettaService.searchRicetteDifficoltà(difficoltà);
            model.addAttribute("difficoltà", difficoltà);
        } else {
            ricette = this.ricettaService.getAllRicette();
        }

        model.addAttribute("ricette", ricette);
        return "ricetta/elencoRicette";
    }

    // Mostra una ricetta specifica
    @GetMapping("/ricetta/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.getRicetta(id));
        return "ricetta/dettaglioRicetta";
    }

    // Crea una nuova ricetta
    @GetMapping("/formRicetta")
    public String showFormRicetta(Model model) {
        model.addAttribute("ricetta", new Ricetta());
        return "ricetta/formRicetta";
    }

    // Gestisci i dati della ricetta
    @PostMapping("/formRicetta")
    public String saveRicetta(@ModelAttribute("ricetta") Ricetta ricetta,
            @RequestParam(value = "file", required = false) MultipartFile file, Model model,
            RedirectAttributes redirectAttributes)
            throws IOException {
        if (ricetta.getTitolo() == null || ricetta.getTitolo().trim().isEmpty()) {
            model.addAttribute("messaggioErrore", "Titolo non valido!");
            return "ricetta/formRicetta";
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        Utente autore = credentials.getUtente();

        if (autore.isBanned()) {
            redirectAttributes.addFlashAttribute("messaggioErrore", "Utente Bannato");
            return "redirect:/elencoRicette";
        }

        // Salva l'immagine se presente
        if (file != null && !file.isEmpty()) {
            ricetta.setImmagine(file.getBytes());
        }

        this.ricettaService.saveRicetta(ricetta, autore);
        return "redirect:/elencoRicette";
    }

    @GetMapping("/ricetta/delete/{id}")
    public String deleteRicetta(@PathVariable("id") Long idRicetta,
            @RequestParam(required = false) Long fromProfile, Model model, RedirectAttributes redirectAttributes) {
        Ricetta ricetta = this.ricettaService.getRicetta(idRicetta);

        if (!isAuthorOrAdmin(ricetta)) {
            redirectAttributes.addFlashAttribute("messaggioErrore", "Non hai i permessi per cancellare questa ricetta");
            return "redirect:/ricetta/" + idRicetta;
        }

        this.ricettaService.deleteRicetta(idRicetta);
        redirectAttributes.addFlashAttribute("messaggioSuccesso", "Hai cancellato con successo questa ricetta");

        // Se eliminato dal profilo, torna al profilo
        if (fromProfile != null) {
            return "redirect:/utente/" + fromProfile;
        }
        return "redirect:/elencoRicette";
    }

    @GetMapping("/ricetta/edit/{id}")
    public String showFormEditRicetta(@PathVariable("id") Long idRicetta, Model model,
            RedirectAttributes redirectAttributes) {
        Ricetta ricetta = this.ricettaService.getRicetta(idRicetta);

        if (!isAuthorOrAdmin(ricetta)) {
            redirectAttributes.addFlashAttribute("messaggioErrore", "Non hai i permessi per modificare questa ricetta");
            return "redirect:/ricetta/" + idRicetta;
        }

        model.addAttribute("ricetta", ricetta);
        return "ricetta/formModificaRicetta";
    }

    @PostMapping("/ricetta/edit/{id}")
    public String updateRicetta(@PathVariable("id") Long idRicetta, @ModelAttribute("ricetta") Ricetta ricettaForm,
            @RequestParam(value = "file", required = false) MultipartFile file, Model model,
            RedirectAttributes redirectAttributes) throws IOException {
        Ricetta ricettaEsistente = this.ricettaService.getRicetta(idRicetta);

        if (!isAuthorOrAdmin(ricettaEsistente)) {
            redirectAttributes.addFlashAttribute("messaggioErrore", "Non hai i permessi per modificare questa ricetta");
            return "redirect:/ricetta/" + idRicetta;
        }

        ricettaEsistente.setTitolo(ricettaForm.getTitolo());
        ricettaEsistente.setDescrizione(ricettaForm.getDescrizione());
        ricettaEsistente.setProcedimento(ricettaForm.getProcedimento());
        ricettaEsistente.setTempoPreparazione(ricettaForm.getTempoPreparazione());
        ricettaEsistente.setDifficoltà(ricettaForm.getDifficoltà());
        ricettaEsistente.setCategoria(ricettaForm.getCategoria());

        // Aggiorna l'immagine solo se ne viene caricata una nuova
        if (file != null && !file.isEmpty()) {
            ricettaEsistente.setImmagine(file.getBytes());
        }

        this.ricettaService.updateRicetta(ricettaEsistente, ricettaForm.getIngredienti());
        redirectAttributes.addFlashAttribute("messaggioSuccesso", "Hai modificato con successo la ricetta");
        return "redirect:/ricetta/" + idRicetta;
    }

    /* Metodo privato per il controllo se l'utente loggato è autore o admin */
    private boolean isAuthorOrAdmin(Ricetta ricetta) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        Utente utenteCorrente = credentials.getUtente();
        boolean isAdmin = credentials.getRole().equals("ADMIN");
        boolean isAuthor = ricetta.getAutore().equals(utenteCorrente);
        return isAdmin || isAuthor;
    }

}
