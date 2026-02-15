package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class ImageController {

    @Autowired
    private RicettaService ricettaService;

    /**
     * Endpoint per servire l'immagine della ricetta dal database.
     * L'immagine è memorizzata come byte[] con @Lob nell'entità Ricetta.
     */
    @GetMapping("/ricetta/{id}/immagine")
    public ResponseEntity<byte[]> getImmagineRicetta(@PathVariable("id") Long id) {
        Ricetta ricetta = this.ricettaService.getRicetta(id);

        if (ricetta == null || ricetta.getImmagine() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Assumiamo JPEG, ma funziona anche per PNG

        return new ResponseEntity<>(ricetta.getImmagine(), headers, HttpStatus.OK);
    }
}
