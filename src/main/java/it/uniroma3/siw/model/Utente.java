package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;

    @OneToOne(mappedBy = "utente")
    private Credentials credentials;

    private boolean banned = false; // Per gestire il blocco utente

    @OneToMany(mappedBy = "autore") // Un utente può avere molte ricette
    private List<Ricetta> ricette;

    @OneToMany(mappedBy = "autore") // Un utente può avere molte recensioni
    private List<Recensione> recensioni;
}
