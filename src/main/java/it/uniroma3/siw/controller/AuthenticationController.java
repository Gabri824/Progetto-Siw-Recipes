package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostra la pagina login
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // Mostra la pagina registrazione
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utente", new Utente());
        model.addAttribute("credentials", new Credentials());

        return "auth/register";
    }

    // Gestisce i dati della registrazione
    @PostMapping("/register")
    public String registerUtente(@ModelAttribute("utente") Utente utente,
            @ModelAttribute("credentials") Credentials credentials, Model model) {

        // Verifica che l'email non esista già
        if (this.credentialsService.existsByEmail(credentials.getEmail())) {
            model.addAttribute("messaggioErrore", "Questa email è già registrata!");
            return "auth/register";
        }

        // Se non esiste, salva le credenziali e l'utente
        credentials.setUtente(utente);
        this.credentialsService.saveCredentials(credentials);
        return "redirect:/login";
    }

    @GetMapping("/profilo/credentials")
    public String showFormModificaCredentials(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

        model.addAttribute("credentials", credentials);
        return "auth/formModificaCredentials";
    }

    @PostMapping("/profilo/credentials")
    public String updateCredentials(@RequestParam("oldPassword") String oldPassword,
            @ModelAttribute("credentials") Credentials credentialsForm, Model model,
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentialsEsistenti = this.credentialsService.getCredentials(userDetails.getUsername());

        if (!this.passwordEncoder.matches(oldPassword, credentialsEsistenti.getPassword())) {
            redirect.addFlashAttribute("messaggioErrore", "La vecchia password non è corretta");
            return "redirect:/profilo/credentials";
        }

        if (credentialsForm != null && !credentialsForm.getPassword().isEmpty()) {
            credentialsEsistenti.setPassword(credentialsForm.getPassword());
            this.credentialsService.updateCredentials(credentialsEsistenti);
            redirect.addFlashAttribute("messaggioSuccesso", "Hai cambiato con successo la tua password");
        }

        // Logout manuale
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("messaggio", "Benvenuto su SiwRecipes!");
        return "index";
    }

}
