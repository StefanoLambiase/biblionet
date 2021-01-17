package it.unisa.c07.biblionet.prenotazioneLibri.service.bookApiAdapter;

import it.unisa.c07.biblionet.model.entity.Libro;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Implementa l'intefaccia del design pattern Adapter per
 * l'interfacciamento con la Google API Books
 * Esegue la chiamata alla API e riceve un JSON che viene
 * trasformato in un oggetto Libro di BiblioNet.
 * Documentazione di google sulla API:
 * https://developers.google.com/books/docs/overview.
 */
@Service
public class GoogleBookApiAdapterImpl implements BookApiAdapter {

    /**
     * L'URL dell'api.
     */
    String googleApiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    /**
     * Lo status code per controllare la richiesta.
     */
    int errorCode = 299;

    /**
     * Implementa la funzionalità che converte la risposta
     * ottenuta dall'interrogazione della google API
     * per i libri, tramite ISBN, in un oggetto della
     * classe Libro, definita in BiblioNet.
     *
     * @param isbn Il codice ISBN a 13 cifre del libro che si vuole cercare
     * @return L'oggetto Libro contenente le informazioni sul libro cercato
     */
    @Override
    public Libro getLibroDaBookApi(final String isbn) {
        try {
            // Compose url string
            String formattedIsbn = isbn.replace("-", "");
            String urlString = googleApiUrl + formattedIsbn;
            URL url = new URL(urlString);

            // Connection part
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Check the response status code
            int status = con.getResponseCode();
            Reader sr = null;
            if (status > errorCode) {
                sr = new InputStreamReader(con.getErrorStream());
                //? Dovrebbe ritornare il messaggio di errore nel service?
                return null;
            } else {
                sr = new InputStreamReader(con.getInputStream());
                BufferedReader bf = new BufferedReader(sr);
                String inputLine;
                StringBuilder stringBuilder = new StringBuilder();
                while ((inputLine = bf.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                bf.close();
                con.disconnect();

                return creaLibroDaResponse(stringBuilder, isbn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Il metodo che si occupa di tradurre la risposta
     * JSON in oggetto Libro.
     * @param stringBuilder la stringa JSON
     * @param isbn l'isbn con cui si effettua la richiesta
     * @return il libro creato
     */
    private Libro creaLibroDaResponse(final StringBuilder stringBuilder,
                                      final String isbn) {
        JSONParser parser = new JSONParser();
        Libro libro = new Libro();
        try {
            //Parsing in Object dello StringBuilder che rappresenta il JSON
            Object obj = parser.parse(stringBuilder.toString());
            //Parsing dell'object in JSONObject
            JSONObject jsonData = (JSONObject) obj;
            //Recupero dei dati del libro dal JSON
            JSONArray items = (JSONArray) jsonData.get("items");
            if (items == null) {
                return null;
            }
            JSONObject bookInfo = (JSONObject) items.get(0);
            JSONObject volumeInfo = (JSONObject) bookInfo.get("volumeInfo");

            //Creazione descrizione da categorie
            String descrizione = "";
            JSONArray categories = (JSONArray) volumeInfo.get("categories");
            if (categories.isEmpty()) {
                descrizione = "NA";
            }
            int i = 0;
            for (Object c : categories) {
                if (i == 0) {
                    descrizione += "Questo libro parla di " + c.toString();
                    i++;
                } else {
                    descrizione += ", " + c.toString();
                }
            }

            //Parsing dei campi del JSON in stringhe
            String titolo = (String) volumeInfo.get("title");
            String casaEditrice = (String) volumeInfo.get("publisher");
            String annoPubblicazione = (String) volumeInfo.get("publishedDate");
            JSONArray autori = (JSONArray) volumeInfo.get("authors");
            JSONObject images = (JSONObject) volumeInfo.get("imageLinks");
            String copertina = (String) images.get("smallThumbnail");
            String autore = autori.get(0).toString();
            LocalDateTime annoPubblicazioneDateTime;
            if (annoPubblicazione == null) {
                annoPubblicazioneDateTime =
                        LocalDateTime.of(1, 1, 1, 1, 1);
            } else {
                annoPubblicazioneDateTime = LocalDateTime.of(
                        Integer.parseInt(annoPubblicazione.substring(0, 4)),
                        1,
                        1,
                        0,
                        0);

            }

            String base64Image = "";
            try {
                URL url = new URL(copertina);
                BufferedInputStream bis =
                        new BufferedInputStream(url.openConnection()
                                .getInputStream());
                byte[] imageData = new byte[8192];
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                int read = 0;
                while ((read = bis.read(imageData)) != -1) {
                    output.write(imageData, 0, read);
                }
                byte[] bytes = output.toByteArray();
                base64Image = Base64.getEncoder().encodeToString(bytes);
            } catch (Exception e) {
                System.out.println(e);
            }
            //Creazione dell'oggetto Libro
            libro.setTitolo(titolo);
            libro.setDescrizione(descrizione);
            if (casaEditrice == null) {
                libro.setCasaEditrice("NA");
            } else {
                libro.setCasaEditrice(casaEditrice);
            }
            libro.setAutore(autore);
            libro.setAnnoDiPubblicazione(annoPubblicazioneDateTime);
            libro.setIsbn(isbn);
            libro.setImmagineLibro(base64Image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libro;
    }

}
