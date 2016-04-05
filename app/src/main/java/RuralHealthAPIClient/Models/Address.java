package RuralHealthAPIClient.Models;

import java.util.HashMap;

import RuralHealthAPIClient.HealthServiceDBContract;
import RuralHealthAPIClient.Interfaces.HealthServiceModelInterface;

/**
 * Created by John on 2/04/2016.
 */
public class Address implements HealthServiceModelInterface {
    private int id;
    private int unitNum;
    private int streetNum;
    private String streetName;
    private String townSuburbCity;
    private int postcode;
    private String country;
    private String state;
    private Coordinate coordinate;

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_UNIT_NUM, String.valueOf(unitNum));
        hashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_STREET_NUM, String.valueOf(streetNum));
        hashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_STREET_NAME, String.valueOf(streetName));
        hashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_TOWN_SUBURB_CITY,townSuburbCity);
        hashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_COUNTRY, country);
        hashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_POSTCODE, String.valueOf(postcode));
        hashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_STATE, state);

        return hashMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getTownSuburbCity() {
        return townSuburbCity;
    }

    public void setTownSuburbCity(String townSuburbCity) {
        this.townSuburbCity = townSuburbCity;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
