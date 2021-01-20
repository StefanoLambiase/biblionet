package it.unisa.c07.biblionet.comunicazioneEsperto.service;

import it.unisa.c07.biblionet.BiblionetApplication;
import it.unisa.c07.biblionet.model.entity.utente.Esperto;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BiblionetApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ComunicazioneEspertoServiceImplIntegrationTest implements
                                                    ApplicationContextAware {
    @Autowired
    @Setter
    @Getter
    private ApplicationContext applicationContext;

    @Autowired
    private ComunicazioneEspertoService comunicazioneEspertoService;


    @BeforeEach
    public void init() {
        BiblionetApplication.init(applicationContext);
    }

    @Test
    public void getEspertiByNome() {
        List<Esperto> espertoList = comunicazioneEspertoService.getEsperiByName("a");
        Assertions.assertTrue(espertoList.size() > 1);
    }

    @Test
    public void getEspertoByGenereNameForTrue() {
        List<Esperto> espertoList = comunicazioneEspertoService.visualizzaEspertiPerGenere("Horror");
        Assertions.assertTrue(espertoList.size() > 1);
    }

    @Test
    public void getEspertoByGenereNameForFalse() {
        List<Esperto> espertoList = comunicazioneEspertoService.visualizzaEspertiPerGenere("Spacet");
        Assertions.assertTrue(espertoList.size() == 0);
    }
}
