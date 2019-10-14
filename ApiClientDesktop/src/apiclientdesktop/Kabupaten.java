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
public class Kabupaten {
    private String id;
    private String name;
    private int populations;
    private String province_id;
    private String id_weather;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulations() {
        return populations;
    }

    public void setPopulations(int populations) {
        this.populations = populations;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getId_weather() {
        return id_weather;
    }

    public void setId_weather(String id_weather) {
        this.id_weather = id_weather;
    }
    
    
}
