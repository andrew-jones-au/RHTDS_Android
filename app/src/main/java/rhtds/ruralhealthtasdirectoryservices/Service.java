package rhtds.ruralhealthtasdirectoryservices;

import android.graphics.Bitmap;

public class Service {
    private int id;
    private String name;
    private Bitmap bitmap;
    private String website;
    private String email;
    private String phone;
    private String address;

    public Service(int id, String name, String website, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        //this.bitmap = bitmap;
        this.website = website;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getWebsite() { return website; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }
}