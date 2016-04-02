package rhtds.ruralhealthtasdirectoryservices;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Andrew on 11/16/2015.
 */
public class Categories extends ActionBarActivity
{
    private static final String TAG = "CategoriesActivity";

    CategoriesArrayAdapter adapter;
    ArrayList<String> listItems=new ArrayList<String>();

    public class Category {
        private int id;
        private String name = "NONE";

        public Category(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() { return name; }
    }
    ArrayList<Category> categoryResponse = new ArrayList<Category>();

    private boolean retrievedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initViewItems();

        //TODO: Testing ConfigManager
        try
        {
            new RuralHealthAPIClient.Client(getApplicationContext());
        }
        catch( Exception e)
        {
            Log.e(TAG, e.toString());
        }

        new AsyncTask<Void, Void, Void>() {
            protected void onPreExecute() {
                // Pre Code
            }
            protected Void doInBackground(Void... unused) {
                setupCategories();

                return null;
            }
            protected void onPostExecute(Void unused)
            {
                initViewItems(); //update the ui
            }
        }.execute();

        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.sponser1), 5000);
        animation.addFrame(getResources().getDrawable(R.drawable.sponser2), 5000);
        animation.setOneShot(false);

        ImageView imageAnim =  (ImageView) findViewById(R.id.sponser);
        imageAnim.setBackground(animation);

        animation.start();
    }

    /*
    Setup items here, such as adding action listeners
     */
    private void initViewItems()
    {

        adapter = new CategoriesArrayAdapter(getApplicationContext(), listItems, categoryResponse);
        ListView listView = (ListView) findViewById(R.id.categoriesListView);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if(retrievedData)
                    viewCategory(position);
            }
        });

        Button searchBarButton = (Button) findViewById(R.id.searchBarButton);

        searchBarButton.setClickable(true);
        searchBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchButtonPressed(v);
            }
        });


    }

    /*
    Fills the categories list with categories
     */
    private void setupCategories()
    {
        listItems.add(0, "Loading categories...");

        JSONArray message = DirectoryRequests.requestCategories(this.getApplicationContext());
        //TODO: iterate over message here

        listItems.clear();

        if(message != null) {
            retrievedData = true;

            for (int i = 0; i < message.length(); i++) {


                try {
                    JSONObject child = message.getJSONObject(i);
                    String category = (String) child.get("name");
                    listItems.add(category);

                    categoryResponse.add(new Category((int) child.get("id"), category));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        else {
            /*for(int i = 0; i < 5; i++) {
                categoryResponse.add(new Category((int) child.get("id"),category));
                listItems.add("Category " + i);
            }*/

            listItems.add("Failed to retrieve categories.");
        }

    }

    private void viewCategory(int position)
    {
        Intent i = new Intent(Categories.this, Services.class);

        //TODO if name at pos isnt what it should be
        //then iterate over till find name

        i.putExtra("CATEGORY", listItems.get(position)); //Integer.toString(categoryResponse.get(position).id)
        i.putExtra("CATEGORY_ID", categoryResponse.get(position).id);
        Log.v("rhtds", "HERE. value is: " + categoryResponse.get(position).id);
        startActivity(i);
    }

    private void searchButtonPressed(View v) {
        //todo
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