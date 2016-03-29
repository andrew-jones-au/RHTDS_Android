package rhtds.ruralhealthtasdirectoryservices;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Andrew on 11/16/2015.
 */
public class Services  extends ActionBarActivity
{
    ServicesArrayAdapter adapter;
    ArrayList<String> listItems = new ArrayList<String>();
    Filter filters = new Filter();
    Bundle savedInstanceState;

    private boolean retrievedData = false;

    private class Filter {
        private ArrayList<String> categories = new ArrayList<String>();
        private double latitude = 0.0;
        private double longitude = 0.0;
        private boolean useLocation = false;
        private String organisationName = "";
        private String address = "";
        private int categoryId = 0;
    }


    ArrayList<Service> servicesResponse = new ArrayList<Service>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        this.savedInstanceState = savedInstanceState;
        setupFilters();
        initViewItems();

        new AsyncTask<Void, Void, Void>() {
            protected void onPreExecute() {
                // Pre Code
            }
            protected Void doInBackground(Void... unused) {
                setupData();

                return null;
            }
            protected void onPostExecute(Void unused) {

                initViewItems();
            }
        }.execute();
    }

    /*
    Setup items here, such as adding action listeners
     */
    private void initViewItems()
    {
        // list view stuff
        //adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        adapter = new ServicesArrayAdapter(getApplicationContext(), listItems, servicesResponse);
        ListView listView = (ListView) findViewById(R.id.servicesListView);
        listView.requestFocus();

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if(retrievedData)
                    viewService(position);
            }
        });
        //end of listview stuff


        //Uncomment out this for debugg info
        /*TextView category = (TextView) findViewById(R.id.servicesCategoryTextView);
        String categoryText = "";

        for(int i = 0; i < filters.categories.size(); i++) {
            categoryText += filters.categories.get(i) + ", ";
        }
        if(categoryText.length() > 0) //if something was added
            categoryText = categoryText.substring(0, categoryText.length() - 2);


        if(categoryText.equals(""))
            categoryText = "All";
        category.setText("Category: " + categoryText);

        TextView location = (TextView) findViewById(R.id.servicesLocationsTextView);
        if(filters.useLocation) {
            location.setText("Location: " + filters.address);
        }
        else
            location.setText("Location: All");*/
    }

    /*
    Used for filters that may be passed from other views, such as what category to use
     */
    private void setupFilters()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getString("CATEGORY") != null) {
                filters.categories.add(extras.getString("CATEGORY"));
            }

            if(extras.getInt("CATEGORY_ID") != 0) {
                filters.categoryId = extras.getInt("CATEGORY_ID");
                Log.v("rhtds", "CATEGORY_ID is: " + filters.categoryId);
            }

            if(extras.getDouble("LONGITUDE") != 0.0 && extras.getDouble("LATITUDE") != 0.0) {
                filters.useLocation = true;
                filters.longitude = extras.getDouble("LONGITUDE");
                filters.latitude = extras.getDouble("LATITUDE");
            }

            if(extras.getBoolean("NEARBY") == true) {
                filters.address = "Current Location";
            } else if(extras.getString("ADDRESS") != null) {
                filters.address = extras.getString("ADDRESS");
            }

            if(extras.getInt("NUM_CATEGORIES") > 0) {
                Log.v("rhtds", "Num categories is: " + extras.getInt("NUM_CATEGORIES"));
                for(int i = 0; i < extras.getInt("NUM_CATEGORIES"); i++) {
                    filters.categories.add(extras.getString("CATEGORY_" + i));
                }
            }

            if(extras.getString("ORGANISATION_NAME") != null) {
                filters.organisationName = extras.getString("ORGANISATION_NAME");
            }

        }
    }

    /*
    Fill in the list with data, based on filters
     */
    private void setupData()
    {
        //use filters object
        listItems.add(0, "Loading services...");

        JSONArray message = DirectoryRequests.requestServices(filters.categories,
                filters.longitude, filters.latitude, filters.useLocation, filters.address,
                filters.organisationName, filters.categoryId, getApplicationContext());


        listItems.clear();

        if(message != null)
        {
            retrievedData = true;

            try {
                //JSONArray results = message.getJSONArray("GetAllHealthServicesJSONResult");
                   Log.v("rhtds", "Length is: " + message.length());

                for (int i = 0; i < message.length(); i++) {
                    JSONObject child = message.getJSONObject(i);
                    String title = (String) child.get("title");

                    listItems.add(title);
                    /*Bitmap bitmap;
                    try {
                        URL url = new URL(DirectoryRequests.IMAGE_BASE_LOCATION + child.getString("imgURI"));//TODO change this to the address from response -- done?
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());//this makes seperate request, should be done elsewhere?
                    }
                    catch(Exception e){
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.abc_ab_share_pack_mtrl_alpha);
                        e.printStackTrace();
                    }*/

                    int id = child.getInt("id");
                    String website = child.getString("website");
                    String email = child.getString("email");
                    String phone = child.getString("phone");

                    JSONObject addressJSON = child.getJSONObject("address");
                    String address = addressJSON.getString("as_string");

                    servicesResponse.add(new Service(id,title, website, email, phone, address));
                }
            }
            catch(Exception e) {
                Log.v("rhtds", "COOKED AS");
                e.printStackTrace();
            }


        }
        else {
            /*(retrievedData = true;
            for(int i = 0; i < 5; i++) {
                listItems.add("Service " + i);
                servicesResponse.add(new Service(i, "Service " + i));
            }*/
            listItems.add("Failed to retrieve services.");
        }
    }

    /*
    View the service that was clicked on
     */
    private void viewService(int position)
    {

        Intent i = new Intent(Services.this, ServiceDetails.class);

        i.putExtra("SERVICE", servicesResponse.get(position).getId());

        startActivity(i);

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