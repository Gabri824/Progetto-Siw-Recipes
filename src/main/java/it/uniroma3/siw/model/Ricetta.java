package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ricetta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titolo;

    @Lob
    private byte[] immagine;

    private String descrizione;
    @Column(columnDefinition = "TEXT")
    private String procedimento;
    private int tempoPreparazione; // minuti
    private String difficoltà; // Facile, Media, Difficile
    private String categoria; // Antipasti, Primi, Secondi, Dolci, Vegetariani, ...
    private LocalDate dataPubblicazione = LocalDate.now();

    @ManyToOne // Molte ricette per un utente
    @JoinColumn(name = "utente_id")
    private Utente autore;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.REMOVE) // Una ricetta può avere molti ingredienti
    private List<Ingrediente> ingredienti;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.REMOVE) // Una ricetta può avere molti ingredienti
    private List<Recensione> recensioni;
}
