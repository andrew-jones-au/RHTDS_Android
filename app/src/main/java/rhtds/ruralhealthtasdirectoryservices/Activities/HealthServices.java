package rhtds.ruralhealthtasdirectoryservices.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import RuralHealthAPIClient.ActionBarOrmLiteActivity;
import RuralHealthAPIClient.ApiHelper;
import RuralHealthAPIClient.Models.Category;


import RuralHealthAPIClient.Models.HealthService;
import RuralHealthAPIClient.OrmLiteActivity;
import rhtds.ruralhealthtasdirectoryservices.Activities.Categories;
import rhtds.ruralhealthtasdirectoryservices.ArrayAdapters.CategoriesArrayAdapter;
import rhtds.ruralhealthtasdirectoryservices.ArrayAdapters.HealthServicesArrayAdapter;
import rhtds.ruralhealthtasdirectoryservices.R;

/**
 * Created by Andrew on 11/16/2015.
 */
public class HealthServices extends OrmLiteActivity
{
    private static final String TAG = HealthService.class.getName();

    HealthServicesArrayAdapter adapter;
    List<String> listItems=new ArrayList<String>();

    List<HealthService> healthServices = new ArrayList<HealthService>();
    String categoryName;
    String searchTerm;
    private boolean retrievedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Intent intent = getIntent();

        categoryName = intent.getExtras().getString("CATEGORY");
        searchTerm = intent.getExtras().getString("SEARCH_TERM");

        loadData(searchTerm, categoryName);
    }

    private void loadData(String searchString, String category)
    {
        try
        {
            healthServices.clear();
            listItems.clear();

            List<HealthService> healthServices = getHelper().getHealthServiceDao().queryForAll();

            if(category != null){
                List<HealthService> newHealthServices = new ArrayList<HealthService>();

                for(HealthService healthService :healthServices)
                {
                    boolean keepInList = false;
                    Iterator<Category> categoryIterator = healthService.getCategoryForeignCollection().iterator();

                    while(categoryIterator.hasNext())
                    {
                        Category currentcat = categoryIterator.next();
                        if(currentcat.getName().equalsIgnoreCase(category))
                        {
                            keepInList = true;
                        }
                    }

                    if(keepInList)
                    {
                        newHealthServices.add(healthService);
                    }
                }

                healthServices = newHealthServices;
            }

            if(searchString != null){
                List<HealthService> newHealthServices = new ArrayList<HealthService>();

                for(HealthService healthService :healthServices)
                {
                    boolean keepInList = false;
                    Iterator<Category> categoryIterator = healthService.getCategoryForeignCollection().iterator();

                    while(categoryIterator.hasNext())
                    {
                        Category currentcat = categoryIterator.next();
                        if(currentcat.getName().toLowerCase().contains(searchString.toLowerCase()))
                        {
                            keepInList = true;
                        }
                    }

                    if(healthService.getTitle().toLowerCase().contains(searchString.toLowerCase()))
                        keepInList = true;

                    if(healthService.getDescription().toLowerCase().contains(searchString.toLowerCase()))
                        keepInList = true;

                    if(keepInList)
                    {
                        newHealthServices.add(healthService);
                    }
                }

                healthServices = newHealthServices;
            }

            this.healthServices = healthServices;

            for(HealthService healthService : this.healthServices)
            {
                listItems.add(healthService.getTitle());
            }

            initViewItems();
        }
        catch(SQLException e)
        {
            Log.e(TAG, e.toString());
        }
    }

    /*
    Setup items here, such as adding action listeners
     */
    private void initViewItems()
    {

        adapter = new HealthServicesArrayAdapter(getApplicationContext(), listItems, healthServices);
        ListView listView = (ListView) findViewById(R.id.servicesListView);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                viewHealthService(position);
            }
        });
    }


    private void viewHealthService(int position)
    {
        Intent i = new Intent(this, HealthServiceDetails.class);

        i.putExtra("HEALTH_SERVICE_ID", healthServices.get(position).get_id());
        startActivity(i);
    }

    private void searchButtonPressed(View v) {
        //todo
    }


   /* @Override
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
    }*/
}