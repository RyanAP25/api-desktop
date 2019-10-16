/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiclientdesktop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Asus
 */
public class DataHandler {
   //    Provinsi
    public ArrayList<Provinsi> getAllProvinsi() {
        ArrayList<Provinsi> data = new ArrayList<>();
        try {
            URL url = new URL("http://localhost/api-server/api/provinsi");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            StringBuilder result = new StringBuilder();
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            org.json.simple.parser.JSONParser par = new org.json.simple.parser.JSONParser();
            JSONArray obj = (JSONArray) par.parse(result.toString());
            conn.disconnect();
            in.close();
            for(int i = 0; i < obj.size(); i++) {
                JSONObject pr = (JSONObject) obj.get(i);
                Provinsi p = new Provinsi();
                p.setId((String) pr.get("id"));
                p.setName((String) pr.get("name"));
                p.setPopulations(Integer.parseInt((String) pr.get("populations")));
                p.setId_weather((String) pr.get("id_weather"));
                data.add(p);
                
            }

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

        return data;
    }
    
    public Provinsi getProvinsi(String id)   {
        Provinsi p = new Provinsi();
        try {
            URL u = new URL("http://localhost/api-server/api/provinsi?idprov="+id);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            JSONParser par = new JSONParser();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            if (conn.getResponseCode() != 200) {
                BufferedReader er = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line, pesan = null;
                while((line = er.readLine()) != null) {
                    pesan+=line;
                }
                pesan = pesan.substring(4);
                JSONObject gagal = (JSONObject) par.parse(pesan);
                System.out.println(gagal.get("message"));
                
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            StringBuilder result = new StringBuilder();
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            JSONArray obj = (JSONArray) par.parse(result.toString());
            conn.disconnect();
            in.close();
            JSONObject kb = (JSONObject) obj.get(0);
            p.setId(kb.get("id").toString());
            p.setName(kb.get("name").toString());
            p.setPopulations(Integer.parseInt(kb.get("populations").toString()));
            p.setId_weather(kb.get("id_weather").toString());
        } catch (Exception e) {
//            
        }
        return p;
    }
}
