package rhtds.ruralhealthtasdirectoryservices;

import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andrew on 11/16/2015.
 */
public class Search  extends ActionBarActivity
{
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();

    List<String> addressNames = new ArrayList<String>();
    List<Address> addresses = new ArrayList<Address>();

    List<String> organisationNames = new ArrayList<String>();
    List<Address> organisations = new ArrayList<Address>();

    private boolean retrievedData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listItems);
        ListView listView = (ListView) findViewById(R.id.searchCategoryListView);
        if(retrievedData)
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        Button searchButton = (Button) findViewById(R.id.searchSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    searchButtonPressed();
            }
        });


        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView location = (AutoCompleteTextView) findViewById(R.id.searchLocationEditText);
        // Get the string array
        addressNames.add("abdfgdfg");
        addressNames.add("bfsdfsdf");
        addressNames.add("bsgdfgfd");
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> locationAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, addressNames);
        location.setThreshold(1);         //to start working from first character
        location.setAdapter(locationAdapter);

        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLocationList(s, start, before, count);
            }
        });


        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView organisationName = (AutoCompleteTextView) findViewById(R.id.searchOrganisationNameEditText);
        // Get the string array
        organisationNames.add("asdgdfghdghfghfgh");
        organisationNames.add("bsadfdfgydghfg");
        organisationNames.add("cdfgghdhgd");
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> organisationNameAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, organisationNames);
        organisationName.setThreshold(1);         //to start working from first character
        organisationName.setAdapter(organisationNameAdapter);

        organisationName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateOrganisationNameList(s, start, before, count);
            }
        });



    }

    /*
    Fill in the list with data
     */
    private void setupData()
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
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        else {
            //for(int i = 0; i < 5; i++)
                //listItems.add("Category " + i);
            listItems.add("Failed to retrieve categories.");
        }
    }

    /*
    View the service that was clicked on
     */
    private void searchButtonPressed()
    {
        ListView listView = (ListView) findViewById(R.id.searchCategoryListView);
        EditText name = (EditText) findViewById(R.id.searchOrganisationNameEditText);
        EditText address = (EditText) findViewById(R.id.searchLocationEditText);

        SparseBooleanArray checked = listView.getCheckedItemPositions();

        Intent intent = new Intent(Search.this, Services.class);

        if(name.getText().length() > 0)
            intent.putExtra("ORGANISATION_NAME", name.getText());

        if(name.getText().length() > 0)
            intent.putExtra("ADDRESS", address.getText());

        intent.putExtra("NUM_CATEGORIES", checked.size());
        for(int i = 0; i < listItems.size(); i++) {
            if (checked.get(i)) {
                intent.putExtra("CATEGORY_" + i, listItems.get(i));
            }
        }

        startActivity(intent);
    }

    /*
    Update list of potential addresses based on what the user has entered
     */
    private void updateLocationList(CharSequence s, int start, int before, int count)
    {
        if(count == 0) {
            addressNames.clear();
        }
        else
        {
            //get list here
            //clear list
            //iterate and add here
        }
    }

    /*
    Update list of potential addresses based on what the user has entered
     */
    private void updateOrganisationNameList(CharSequence s, int start, int before, int count)
    {
        if(count == 0)
            organisationNames.clear();
        else
        {
            //get list here
            //clear list
            //iterate and add here
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