package it.unisa.c07.biblionet.comunicazioneEsperto.service;

import it.unisa.c07.biblionet.model.dao.utente.EspertoDAO;
import it.unisa.c07.biblionet.model.entity.Genere;
import it.unisa.c07.biblionet.model.entity.utente.Biblioteca;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import it.unisa.c07.biblionet.model.entity.utente.UtenteRegistrato;
import it.unisa.c07.biblionet.preferenzeDiLettura.service.PreferenzeDiLetturaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ComunicazioneEspertoServiceImplTest {


    /**
     * Inject del service per simulare le operazioni.
     */
    @InjectMocks
    private ComunicazioneEspertoServiceImpl comunicazioneEspertoService;

    @Mock
    private EspertoDAO espertoDAO;

    @Test
    @DisplayName("Non entra al primo for")
    public void getEspertiByGeneri1(){

        ArrayList<UtenteRegistrato> utente= new ArrayList<>();
        utente.add(new Biblioteca());
        when(espertoDAO.findAll()).thenReturn(utente);
        assertEquals(new ArrayList<>(),
            comunicazioneEspertoService.getEspertiByGeneri(new ArrayList<>()));

    }

    @Test
    @DisplayName("Non entra al primo if")
    public void getEspertiByGeneri2(){

        when(espertoDAO.findAll()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(),
                comunicazioneEspertoService.getEspertiByGeneri(new ArrayList<>()));

    }

    @ParameterizedTest
    @MethodSource("provideEsperto")
    @DisplayName("Entra al primo for")
    public void getEspertiByGeneri3(Esperto esperto,Genere genere){
        esperto.setGeneri(Arrays.asList(genere));
        List<UtenteRegistrato> esperti = Arrays.asList(esperto);
        when(espertoDAO.findAll()).thenReturn(esperti);
        assertEquals(new ArrayList<>(),
                comunicazioneEspertoService.getEspertiByGeneri(new ArrayList<>()));

    }


    /**
     * Simula i dati inviati da un metodo
     * http attraverso uno stream.
     *
     * @return Lo stream di dati.
     */
    private static Stream<Arguments> provideEsperto() {

        return Stream.of(
                Arguments.of(
                        new Esperto(
                                "eliaviviani@gmail.com",
                                "EspertoPassword",
                                "Napoli",
                                "Torre del Greco",
                                "Via Roma 2",
                                "2345678901",
                                "Espertissimo",
                                "Elia",
                                "Viviani",
                                null
                        ),
                        new Genere("Test","Test")

                )
        );

    }

}
