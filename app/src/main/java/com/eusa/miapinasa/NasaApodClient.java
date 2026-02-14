package com.eusa.miapinasa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class NasaApodClient {

    // Endpoint de la NASA para la foto astronómica del día (APOD)
    private static final String NASA_APOD_API_URL =
            "https://api.nasa.gov/planetary/apod";

    // Mi API key de la NASA
    private static final String API_KEY = "43IrFdbaLlqlKwW3O5TqxX0eWjwNvtSQRqCVJVuo";

    // Hace la llamada a la API y devuelve el JSON con toda la info
    public JSONObject getApodData() throws IOException, JSONException {

        // Fecha random de los últimos 10 años para que no salga siempre la de hoy
        String randomDate = getRandomDate();

        // URL con la key y la fecha
        String urlWithParams = NASA_APOD_API_URL
                + "?api_key=" + API_KEY
                + "&date=" + randomDate;

        // Conecto y hago la petición
        URL url = new URL(urlWithParams);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Si no es 200, algo ha ido mal
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error en la solicitud: " + responseCode);
        }

        // Leo la respuesta en texto
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        conn.disconnect();

        // Convierto el texto a JSON y lo devuelvo
        return new JSONObject(response.toString());
    }

    // Genera una fecha aleatoria dentro de los últimos 10 años en formato yyyy-MM-dd
    private String getRandomDate() {

        // Fecha actual
        Calendar calendar = Calendar.getInstance();

        // Fecha de hace 10 años
        Calendar tenYearsAgo = Calendar.getInstance();
        tenYearsAgo.add(Calendar.YEAR, -10);

        long startMillis = tenYearsAgo.getTimeInMillis();
        long endMillis = calendar.getTimeInMillis();

        // Saco un número aleatorio entre esas dos fechas
        long randomMillis = startMillis +
                (long) (new Random().nextDouble() * (endMillis - startMillis));

        calendar.setTimeInMillis(randomMillis);

        // Lo formateo como lo quiere la NASA
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }
}
