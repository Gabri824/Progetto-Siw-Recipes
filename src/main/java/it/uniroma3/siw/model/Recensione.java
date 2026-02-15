package it.uniroma3.siw.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String testo;
    @Min(value = 1, message = "Voto minimo è 1")
    @Max(value = 5, message = "Voto massimo è 5")
    private int voto;

    private LocalDate dataPubblicazione = LocalDate.now();

    @ManyToOne // Molte recensioni scritte da un utente
    @JoinColumn(name = "utente_id")
    private Utente autore;

    @ManyToOne // Molte recensioni per una ricetta
    @JoinColumn(name = "ricetta_id")
    private Ricetta ricetta;
}
