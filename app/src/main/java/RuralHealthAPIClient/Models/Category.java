package RuralHealthAPIClient.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by John on 2/04/2016.
 */
@DatabaseTable(tableName = "category")
public class Category implements Serializable {

    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String description;

    @DatabaseField(foreign = true, columnName = "health_service", foreignAutoCreate=true, foreignAutoRefresh=true)
    private HealthService healthService;

    @Override
    public String toString()
    {
        return "Category: " + name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HealthService getHealthService() {
        return healthService;
    }

    public void setHealthService(HealthService healthService) {
        this.healthService = healthService;
    }
}
