package RuralHealthAPIClient.Models;

import android.util.Log;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by John on 2/04/2016.
 */
@DatabaseTable(tableName = "health_service")
public class HealthService implements Serializable {

    private final static String TAG = HealthService.class.getName();

    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String phone;

    @DatabaseField
    private String altPhone;

    @DatabaseField
    private String imgURI;

    @DatabaseField
    private String website;

    @DatabaseField
    private String email;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Category> categoryForeignCollection;

    private List<Category> categories;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh=true)
    private Address address;

    @Override
    public String toString()
    {
        return "Health Service: " + title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAltPhone() {
        return altPhone;
    }

    public void setAltPhone(String altPhone) {
        this.altPhone = altPhone;
    }

    public String getImgURI() {
        return imgURI;
    }

    public void setImgURI(String imgURI) {
        this.imgURI = imgURI;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getTAG() {
        return TAG;
    }

    public ForeignCollection<Category> getCategoryForeignCollection() {
        return categoryForeignCollection;
    }

    public void setCategoryForeignCollection(ForeignCollection<Category> categoryForeignCollection) {
        this.categoryForeignCollection = categoryForeignCollection;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
