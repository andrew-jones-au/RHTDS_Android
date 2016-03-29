package rhtds.ruralhealthtasdirectoryservices;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Home extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViewItems();
    }

    /*
    Setup items here, such as adding action listeners
     */
    private void initViewItems()
    {
        Button categoryButton = (Button) findViewById(R.id.homeCategoriesButton);
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Categories.class);
                startActivity(i);
            }
        });

        Button nearbyButton = (Button) findViewById(R.id.homeNearbyButton);
        nearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearbyButtonPressed();
            }
        });

        Button searchButton = (Button) findViewById(R.id.homeSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Search.class);
                startActivity(i);
            }
        });
    }

    /*
    Nearby button was pressed, so get long/lat and then use that as a filter for Services.java
     */
    private void nearbyButtonPressed()
    {
        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            Intent i = new Intent(Home.this, Services.class);

            i.putExtra("NEARBY", true);
            i.putExtra("LONGITUDE", longitude);
            i.putExtra("LATITUDE", latitude);

            startActivity(i);
        }
        catch (Exception e){
            Context context = getApplicationContext();
            CharSequence text = "Unable to retrieve current location, check that location access is enabled.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
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
