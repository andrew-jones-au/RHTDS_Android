package rhtds.ruralhealthtasdirectoryservices;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Andrew on 11/16/2015.
 */
public class ServiceDetails  extends FragmentActivity implements OnMapReadyCallback
{
    private class ServiceInfo {
        String name = "Loading...";
        String phone = "Loading...";
        String address = "Loading...";
        String description = "Loading...";
        String imageLocation;
        double longitude = 0;
        double latitude = 0;
        int id = -1;
        boolean hasCoords = true;

    }

    private ServiceInfo serviceInfo;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Bitmap bitmap; //holds the bitmap retrieved by a http request (if it works, null if not)

    private boolean retrievedData = false;
    private boolean mapReady = false;
    private boolean viewingDefaultLocation = false;//mapReady but not retrievedData
    private boolean viewsSetup = false; //only init once

    LatLng tasCoords = new LatLng(-42.0000, 146.5000);
    LatLng defaultCoords  = new LatLng(-41.0636, 145.8753);
    private int zoomCloseLevel = 15;
    private int zoomFarLevel = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        serviceInfo = new ServiceInfo();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            serviceInfo.id = extras.getInt("SERVICE");

            new AsyncTask<Void, Void, Void>() {
                protected void onPreExecute() {
                    // Pre Code
                }
                protected Void doInBackground(Void... unused) {
                    setupDetails();
                    return null;
                }
                protected void onPostExecute(Void unused) {
                    initViewItems(); //update the ui

                    if(mapReady)
                        setupMap();
                }
            }.execute();
        }

        if(!viewsSetup)
            initViewItems();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /*
    The callback is triggered when the map is ready to be used.
    It provides a non-null instance of GoogleMap.
    You can use the GoogleMap object to set the view options for the map or add a marker, for example.
     */
    @Override
    public void onMapReady(GoogleMap map)
    {
        mapReady = true;

        if(retrievedData)
        {
            setupMap(map);
        }
        else
        {  //map ready before getting info, so handle something here

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(tasCoords, zoomFarLevel));
            map.addMarker(new MarkerOptions().position(defaultCoords).title("Loading information...")).showInfoWindow();
            viewingDefaultLocation = true;
        }
    }

    private void setupMap()
    {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        setupMap(mapFragment.getMap());

    }

    private void setupMap(GoogleMap map)
    {
        LatLng businessLocation = new LatLng(serviceInfo.latitude, serviceInfo.longitude);
        int zoomLevel = serviceInfo.hasCoords ? zoomCloseLevel : zoomFarLevel; //whether to zoom in or not
        map.addMarker(new MarkerOptions().position(businessLocation).title(serviceInfo.name)).showInfoWindow();
        if(viewingDefaultLocation) { //zoom to new location
            map.clear();

            if(serviceInfo.hasCoords) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(businessLocation)
                        .zoom(zoomLevel).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
        else //start at the business location
        {
            if(serviceInfo.hasCoords)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(businessLocation, zoomLevel));
            else
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(tasCoords, zoomLevel));
        }

    }

    /*
    Setup items here, such as adding action listeners
     */
    private void initViewItems()
    {
        viewsSetup = true;

        TextView serviceName = (TextView) findViewById(R.id.serviceNameTextView);
        serviceName.setText(serviceInfo.name);

        TextView servicePhone = (TextView) findViewById(R.id.servicePhoneTextView);
        servicePhone.setText(serviceInfo.phone);

        TextView serviceAddress = (TextView) findViewById(R.id.serviceAddressTextView);
        serviceAddress.setText(serviceInfo.address);

        TextView serviceDescription = (TextView) findViewById(R.id.serviceDescriptionTextView);
        serviceDescription.setText( serviceInfo.description);

        if(bitmap != null) {
            ImageView imageView = (ImageView) findViewById(R.id.serviceImageView);
            imageView.setImageBitmap(bitmap);
        }
        else { //TODO: should put "failed to load" image or something

        }

        /*TextView serviceLongitude = (TextView) findViewById(R.id.serviceLongitudeTextView);
        serviceLongitude.setText("Longitude: " + serviceInfo.longitude);

        TextView serviceLatitude = (TextView) findViewById(R.id.serviceLatitudeTextView);
        serviceLatitude.setText("Latitude: " + serviceInfo.latitude);*/
    }

    private boolean setupDetails()
    {
        boolean success = requestInfo();
        if(success)
        {
            if (serviceInfo.phone.equals(""))
                serviceInfo.phone = "No phone number listed.";

            if(serviceInfo.address.equals(""))
                serviceInfo.address = "No address listed.";

            if(serviceInfo.description.equals(""))
                serviceInfo.description = "No description listed.";

            return true;
        }
        else //handle something here, wasnt success in getting data
        {
            return false;
        }
    }

    /*
    HTTP request for info, returns true if success, false otherwise
     */
    private boolean requestInfo()
    {
        retrievedData = true;
        boolean hasCoords = false;
        //get info here and handle it


        try {
            JSONObject message = DirectoryRequests.requestServiceInfo(serviceInfo.id);
            JSONObject address = message.getJSONObject("address");

            try {
                JSONObject coords = message.getJSONObject("coordinates");
                hasCoords = true; //if above line doesnt fail, theres some coords
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            serviceInfo.phone = message.getString("phone");
            serviceInfo.name = message.getString("title");
            serviceInfo.address = address.getString("as_string");
            serviceInfo.description =  message.getString("description");
            serviceInfo.imageLocation = DirectoryRequests.IMAGE_BASE_LOCATION + message.getString("imgURI");

            if(hasCoords) {
                serviceInfo.longitude = message.getDouble("longitude");
                serviceInfo.latitude = message.getDouble("latitude");
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //handle info retrieved from http request //all the stuff below should be changed
        if (serviceInfo.phone.equals("Loading..."))
            serviceInfo.phone = "No phone number listed.";

        if(serviceInfo.address.equals("Loading..."))
            serviceInfo.address = "No address listed.";

        if(serviceInfo.description.equals("Loading..."))
            serviceInfo.description = "No description listed.";

        if(!hasCoords) {
            serviceInfo.latitude = defaultCoords.latitude;
            serviceInfo.longitude = defaultCoords.longitude;
            serviceInfo.hasCoords = false;

        }

        try {
            URL url = new URL(serviceInfo.imageLocation);//TODO change this to the address from response
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());//this makes seperate request, should be done elsewhere?
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}