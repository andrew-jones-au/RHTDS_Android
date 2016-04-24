package RuralHealthAPIClient.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by John on 2/04/2016.
 */

@DatabaseTable(tableName = "address")
public class Address implements Serializable {

    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField
    private int id;

    @DatabaseField
    private int unitNum;

    @DatabaseField
    private int streetNum;

    @DatabaseField
    private String streetName;

    @DatabaseField
    private String townSuburbCity;

    @DatabaseField
    private int postcode;

    @DatabaseField
    private String country;

    @DatabaseField
    private String state;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh=true)
    private Coordinate coordinates;

    @Override
    public String toString()
    {
        return streetNum + " " + streetName + " " + townSuburbCity + " " + state +" " + postcode + " " + country;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }
}
