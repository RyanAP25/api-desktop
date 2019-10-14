/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiclientdesktop;
/**
 *
 * @author Asus
 */
public class ApiClientDesktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataHandler dh = new DataHandler();
        Provinsi prov = dh.getProvinsi("72");
        System.out.println("id Weather : "+prov.getId_weather());
    }
    
}
