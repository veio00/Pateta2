/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Will
 */
public class Envio {

    private static String lerString(InputStream stream) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(stream)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] tmp = new byte[1024];
            int total;
            do {
                total = in.read(tmp);
                if (total > 0)
                    out.write(tmp, 0, total);
            } while (total > 0);
            return new String(out.toByteArray(), "utf-8");
        }
    }
    
    public static String envioColeta(String classe, String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        con.setRequestMethod("POST");
        con.getOutputStream().write(classe.getBytes("utf-8"));
        //add request header
        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            return lerString(con.getInputStream());
        } else if (responseCode == 204) {
            return null;
        }
        throw new IOException("Erro HTTP: " + responseCode);
    }

    
    public static <T> T envioColeta(String classe, String url, Class<T> clazz) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        con.setRequestMethod("POST");
        con.getOutputStream().write(classe.getBytes("utf-8"));
        //add request header
        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            return new Gson().fromJson(lerString(con.getInputStream()), clazz);
        } else if (responseCode == 204) {
            return null;
        }
        throw new IOException("Erro HTTP: " + responseCode);
    }    
}
