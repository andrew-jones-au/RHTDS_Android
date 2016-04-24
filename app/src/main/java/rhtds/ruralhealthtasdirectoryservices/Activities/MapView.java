package rhtds.ruralhealthtasdirectoryservices.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;

import RuralHealthAPIClient.ActionBarOrmLiteActivity;
import RuralHealthAPIClient.Config;
import RuralHealthAPIClient.ConfigurationManager;
import RuralHealthAPIClient.Models.HealthService;
import RuralHealthAPIClient.OrmLiteActivity;
import RuralHealthAPIClient.OrmLiteFragmentActivity;
import rhtds.ruralhealthtasdirectoryservices.R;

/**
 * Created by Andrew on 11/16/2015.
 */
public class MapView extends OrmLiteFragmentActivity implements OnMapReadyCallback
{
    private static final String TAG = MapView.class.getName();

    HealthService healthService;

    com.google.android.gms.maps.MapView mapView;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        int id = intent.getExtras().getInt("HEALTH_SERVICE_ID");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfragment);

        mapFragment.getMapAsync(this);

        loadData(id);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        double lat, longi;

        lat = Double.valueOf(healthService.getAddress().getCoordinates().getLatitude());
        longi = Double.valueOf(healthService.getAddress().getCoordinates().getLongitude());

        LatLng centerPoint = new LatLng(lat, longi);

        CameraUpdate center= CameraUpdateFactory.newLatLng(centerPoint);
        googleMap.addMarker(new MarkerOptions().position(centerPoint).title(healthService.getTitle()));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }

    private void loadData(int healthServiceId)
    {
        try
        {
            healthService = getHelper().getHealthServiceDao().queryForId(healthServiceId);
        }
        catch(SQLException e)
        {
            Log.e(TAG, e.toString());
        }
    }


/*
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
*/

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}