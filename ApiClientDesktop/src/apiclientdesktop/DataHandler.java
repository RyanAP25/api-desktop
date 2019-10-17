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
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
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
   
    public String addProvinsi(String id, String name, int populations, String id_weather) {
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("id", id);
        postDataParams.put("name", name);
        postDataParams.put("populations", String.valueOf(populations));
        postDataParams.put("id_weather", id_weather);
        JSONParser par = new JSONParser();
        String mesg = null;
        try {

            URL u = new URL("http://localhost/api-server/api/provinsi");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                String line, response = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                response = response.substring(4);
                Document document = Jsoup.parse(response);
                Element table = document.select("table").first();
                String td = table.select("td").text();
                mesg = td;
            }
            if (responseCode == 400) {
                String line, response = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                response = response.substring(4);
                Document document = Jsoup.parse(response);
                Element table = document.select("table").first();
                String td = table.select("td").text();
                mesg = td;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mesg;
    }
    
    public String updateProvinsi(String id, String name, int populations, String id_wether) {
        String mesg = null;
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("id", id);
        postDataParams.put("name", name);
        postDataParams.put("populations", String.valueOf(populations));
        postDataParams.put("id_weather", id_wether);
        try {
            URL u = new URL("http://localhost/api-server/api/provinsi");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                String line, response = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line = br.readLine()) != null) {
                    response+=line;
                }
                response = response.substring(4);
                Document document = Jsoup.parse(response);
                Element table = document.select("table").first();
                String td = table.select("td").text();
                mesg = td;
            }
            if(responseCode == 400){
                String line, response = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while((line = br.readLine()) != null) {
                    response+=line;
                }
                response = response.substring(4);
                Document document = Jsoup.parse(response);
                Element table = document.select("table").first();
                String td = table.select("td").text();
                mesg = td;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mesg;
    }
    
    public String deleteProvinsi(String id) {
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("id", id);
        String mesg = null;
        try {
            URL u = new URL("http://localhost/api-server/api/provinsi");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            
             OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                String line, response = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line = br.readLine()) != null) {
                    response+=line;
                }
                response = response.substring(4);
                Document document = Jsoup.parse(response);
                Element table = document.select("table").first();
                String td = table.select("td").text();
                mesg = td;
            }
            if(responseCode == 404){
                String line, response = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while((line = br.readLine()) != null) {
                    response+=line;
                }
                response = response.substring(4);
                Document document = Jsoup.parse(response);
                Element table = document.select("table").first();
                String td = table.select("td").text();
                mesg = td;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mesg;
    }
    
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
