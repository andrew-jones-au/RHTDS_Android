package rhtds.ruralhealthtasdirectoryservices.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import RuralHealthAPIClient.ActionBarOrmLiteActivity;
import RuralHealthAPIClient.Config;
import RuralHealthAPIClient.ConfigurationManager;
import RuralHealthAPIClient.Models.Category;
import RuralHealthAPIClient.Models.HealthService;
import RuralHealthAPIClient.OrmLiteActivity;
import rhtds.ruralhealthtasdirectoryservices.ArrayAdapters.HealthServicesArrayAdapter;
import rhtds.ruralhealthtasdirectoryservices.R;

/**
 * Created by Andrew on 11/16/2015.
 */
public class HealthServiceDetails extends OrmLiteActivity
{
    private static final String TAG = HealthServiceDetails.class.getName();

    HealthService healthService;

    TextView phone, address, email, website, title, description, mapButton;
    ImageView imageView;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        phone = (TextView)findViewById(R.id.servicePhoneTextView);
        address = (TextView)findViewById(R.id.serviceAddressTextView);
        email = (TextView)findViewById(R.id.serviceEmailTextView);
        website = (TextView)findViewById(R.id.serviceWebsiteTextView);
        imageView = (ImageView)findViewById(R.id.serviceImageView);
        title = (TextView)findViewById(R.id.serviceNameTextView);
        description = (TextView)findViewById(R.id.serviceDescriptionTextView);
        mapButton = (TextView)findViewById(R.id.MapButton);

        config = new ConfigurationManager(getApplicationContext()).getConfig();

        Intent intent = getIntent();

        int id = intent.getExtras().getInt("HEALTH_SERVICE_ID");
        loadData(id);
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

        initViewItems();
    }

    /*
    Setup items here, such as adding action listeners
     */
    private void initViewItems()
    {
        phone.setText(healthService.getPhone());
        address.setText(healthService.getAddress().toString());
        website.setText(healthService.getWebsite());
        email.setText(healthService.getEmail());
        title.setText(healthService.getTitle());
        description.setText(healthService.getDescription());

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapButtonClicked();
            }
        });

        new DownloadImageTask(imageView).execute(config.getStaticContentEndpoint() + "/uploads/" + healthService.getImgURI());
    }

    public void onMapButtonClicked()
    {
        Intent intent = new Intent(this, MapView.class);
        intent.putExtra("HEALTH_SERVICE_ID", healthService.get_id());
        startActivity(intent);
    }

/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
*/

    /*@Override
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
                InputStream in = new java.net.URL(urldisplay).openStream();
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