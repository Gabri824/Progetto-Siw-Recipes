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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/profilo")
    public String profilo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        return "redirect:/utente/" + credentials.getUtente().getId();
    }

    @GetMapping("/utente/{id}")
    public String getUtente(@PathVariable("id") Long id, Model model) {
        Utente utente = this.utenteService.getUtenteWithCollections(id);
        if (utente == null) {
            return "redirect:/"; // o una pagina di errore 404
        }

        model.addAttribute("utente", utente);
        return "utente/profilo";
    }

    @GetMapping("/utente/{id}/recensioni")
    public String getRecensioniUtente(@PathVariable("id") Long idUtente, Model model) {
        model.addAttribute("recensioni", this.utenteService.getUtenteWithCollections(idUtente).getRecensioni());
        return "recensioniUtente";
    }

    @GetMapping("/utente/{id}/ricette")
    public String getRicetteUtente(@PathVariable("id") Long idUtente, Model model) {
        model.addAttribute("ricette", this.utenteService.getUtenteWithCollections(idUtente).getRicette());
        return "ricetteUtente";
    }

    @GetMapping("/utente/edit/{id}")
    public String showFormEditUtente(@PathVariable("id") Long idUtente, Model model) {
        model.addAttribute("utente", this.utenteService.getUtente(idUtente));
        return "utente/formModificaUtente";
    }

    @PostMapping("/utente/edit/{id}")
    public String updateUtente(@PathVariable("id") Long idUtente, @ModelAttribute("utente") Utente utenteForm,
            Model model, RedirectAttributes redirectAttributes) {
        Utente utenteEsistente = this.utenteService.getUtente(idUtente);

        utenteEsistente.setNome(utenteForm.getNome());
        utenteEsistente.setCognome(utenteForm.getCognome());
        utenteEsistente.setDataDiNascita(utenteForm.getDataDiNascita());

        this.utenteService.saveUtente(utenteEsistente);
        redirectAttributes.addFlashAttribute("messaggioSuccesso",
                "Hai modificato con successo i dati del profilo utente");

        return "redirect:/utente/" + idUtente;
    }
}
