package RuralHealthAPIClient.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by John on 2/04/2016.
 */

@DatabaseTable(tableName = "coordinate")
public class Coordinate implements Serializable
{
    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField
    int id;

    @DatabaseField
    String longitude;

    @DatabaseField
    String latitude;

    @Override
    public String toString()
    {
        return "Coordinate: Lat" + latitude + " long: " + longitude;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
