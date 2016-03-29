package rhtds.ruralhealthtasdirectoryservices;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CacheRequest;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Andrew on 11/20/2015.
 */
public class DirectoryRequests
{

    //https://john-kendall.cloud.tyk.io/rural-health-api/RuralHealthDirectoryService/healthservices.json?
    final private static String domain = "http://ruralhealth.johnkendall.net/"; //http://192.168.1.30:8000/
    final private static String subAddress = domain + "ruralhealthdirectoryservice/"; //"ruralhealthdirectoryservice/";
    final private static String categoriesAddress = "categories.json";
    final private static String serviceInfoAddress = "healthservices/";
    final private static String servicesAddress = "healthservices.json";
    //simple-query=Rural

    private static boolean storedInCache = false;



    public final static String IMAGE_BASE_LOCATION = domain + "static/uploads/";
    //final private static String allServicesAddress = "GetAllHealthServicesJSON";

    //modified from https://dylansegna.wordpress.com/2013/09/19/using-http-requests-to-get-json-objects-in-android/
    private static String getJSONString(String address)
    {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();

        try
        {
            URI website = new URI(address);
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(website);

            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }
            }
            else {
                Log.e("rhtds", "Failed to get JSON object");
            }
        }
        catch(ClientProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    private static JSONArray getJSONArray(String s)
    {
        try
        {
            //Log.v("rhtds", builder.toString());
            return new JSONArray(s);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject getJSONObject(String s)
    {
        try
        {
            //Log.v("rhtds", builder.toString());
            return new JSONObject(s);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray requestCategories(Context context)
    {
        String s = "";
        if(!hasInternetAvailable()) { //TODO: || !websiteAvailable()
            s = DirectoryCache.requestAllCategories(context);
        }
        else {
            s = getJSONString(subAddress + categoriesAddress);

            if(!storedInCache) {
                Log.v("RHTDS", "here");

                String services = getAllServices();

                DirectoryCache.writeCategories(s, services, context);//write the newest categories, should have some exception catching here, and only write once per app running

                storedInCache = true;
            }
        }

        return getJSONArray(s);

    }

    private static String getAllServices()
    {
        String query = subAddress + servicesAddress;
        String s = getJSONString(query);
        Log.v("rhtds", s);
        return s;

    }

    //TODO: This is querying categories wrong ?
    public static JSONArray requestServices(ArrayList<String> categories, double longitude,
                double latitude, boolean useLocation, String address, String organisationName, int categoryId,
                Context context) //TODO?: make categoryId an array for searching
    {
        if(true)//!hasInternetAvailable()) //need to make this work with the search stuff
        { //TODO: || !websiteAvailable()
            //s = DirectoryCache.requestAllCategories(context);

            return DirectoryCache.requestServices(categories, longitude, latitude, useLocation,
                    address, organisationName, categoryId, context);
        }
        else
        {
            String query = subAddress + servicesAddress + "?";

            if(categories.size() > 0) {
                query += "category=";
                for (int i = 0; i < categories.size(); i++)
                    query += categories.get(i).toString().replace(" ", "%20") + ","; //my%20category,
            }
            else {
                query += "categories=all&";//TODO change this?
            }

            if(useLocation) {
                query += "longitude=" + longitude + "&";
                query += "latitude=" + latitude + "&";
            }

            if(!address.equals("") && !address.equals("Current Location")) {
                query += "address=" + address + "&";
            }

            if(!organisationName.equals("")) {
                query += "organisationName=" + organisationName + "&";
            }

            if(!query.equals(servicesAddress + "?")) {
                query = query.substring(0, query.length() - 1);
            }

            Log.v("rhtds", query);

            //query = domainAddress + allServicesAddress;
            String s = getJSONString(query);
            //Log.v("rhtds", s);
            return getJSONArray(s);
        }

    }

    public static JSONObject requestServiceInfo(int serviceId)
    {
        String s = getJSONString(subAddress + serviceInfoAddress + serviceId + ".json");
        return getJSONObject(s);

    }

    //http://stackoverflow.com/questions/9570237/android-check-internet-connection
    //returns true if the user currently has internet
    //TOOD: check if website is up?
    private static boolean hasInternetAvailable()
    {
        try {
            InetAddress ipAddr = InetAddress.getByName("vps.ruralhealth.ssdhosts.com.au"); //You can replace it with your name

            return !ipAddr.equals(""); //returns false if == "", true otherwise

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    private ArrayList<String> categories = new ArrayList<String>();
    private double latitude = 0.0;
    private double longitude = 0.0;
    private boolean useLocation = false;
    private String organisationName = "";
    private String address = "";

}
