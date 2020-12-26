package it.unisa.c07.biblionet.model.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Questa classe rappresenta un form
 * contenente i dati di un evento
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EventoForm {

    /**
     * Il nome dell'evento.
     */
    @NonNull
    private String nome;

    /**
     * Una descrizione dell'evento.
     */
    @NonNull
    private String descrizione;

    /**
     * La data dell'evento.
     */
    @NonNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate data;

    /**
     * L'ora dell'evento.
     */
    @NonNull
    @DateTimeFormat(pattern="HH:mm")
    private LocalTime ora;

    /**
     * Il libro associato all'evento.
     */
    private Integer libro;
}
