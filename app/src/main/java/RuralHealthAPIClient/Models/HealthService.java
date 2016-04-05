package RuralHealthAPIClient.Models;

import java.util.ArrayList;
import java.util.HashMap;

import RuralHealthAPIClient.HealthServiceDBContract;
import RuralHealthAPIClient.HealthServiceDBHelper;
import RuralHealthAPIClient.Interfaces.HealthServiceModelInterface;

/**
 * Created by John on 2/04/2016.
 */
public class HealthService implements HealthServiceModelInterface {

    private int id;
    private String title;
    private String description;
    private String phone;
    private String altPhone;
    private String imgURI;
    private String website;
    private String email;
    private ArrayList<Category> categories;
    private Address address;

    public int getId() {
        return id;
    }

    public HashMap<String, String> toHashMap()
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_TITLE, title);
        hashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_ALT_PHONE, altPhone);
        hashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_PHONE, phone);
        hashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_DESCRIPTION, description);
        hashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_EMAIL, email);
        hashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_WEBSITE, website);
        hashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_IMGURI, imgURI);

        return hashMap;
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

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
