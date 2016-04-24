package rhtds.ruralhealthtasdirectoryservices.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import RuralHealthAPIClient.ActionBarOrmLiteActivity;
import RuralHealthAPIClient.ApiHelper;
import RuralHealthAPIClient.Models.Category;


import RuralHealthAPIClient.Models.HealthService;
import rhtds.ruralhealthtasdirectoryservices.ArrayAdapters.CategoriesArrayAdapter;
import rhtds.ruralhealthtasdirectoryservices.R;
import rhtds.ruralhealthtasdirectoryservices.Activities.HealthServices;
import rhtds.ruralhealthtasdirectoryservices.Utils;

/**
 * Created by Andrew on 11/16/2015.
 */
public class Categories extends ActionBarOrmLiteActivity
{
    private static final String TAG = "CategoriesActivity";

    CategoriesArrayAdapter adapter;
    ArrayList<String> listItems=new ArrayList<String>();

    ArrayList<Category> categoryResponse = new ArrayList<Category>();

    LinearLayout containerLayout;

    TextView searchTextView;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ApiHelper apiHelper = new ApiHelper(getApplicationContext()){
            @Override
            public void getHealthServicesResponse(List<HealthService> healthServices) {
                super.getHealthServicesResponse(healthServices);

                onDataRetrieved();
            }
        };

        apiHelper.getHealthServices();

        searchTextView = (TextView)findViewById(R.id.searchBarEditText);
        searchButton = (Button)findViewById(R.id.searchBarButton);
        containerLayout = (LinearLayout)findViewById(R.id.container_view);

        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.sponser1), 5000);
        animation.addFrame(getResources().getDrawable(R.drawable.sponser2), 5000);
        animation.setOneShot(false);

        ImageView imageAnim =  (ImageView) findViewById(R.id.sponser);
        imageAnim.setBackground(animation);

        animation.start();
    }

    private void onDataRetrieved()
    {
        try
        {
            categoryResponse.clear();
            listItems.clear();

            for (Category category :getHelper().getCategoryDao().queryBuilder().distinct().selectColumns("name").query())
            {
                categoryResponse.add(category);
                listItems.add(category.getName());
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

        adapter = new CategoriesArrayAdapter(getApplicationContext(), listItems, categoryResponse);
        ListView listView = (ListView) findViewById(R.id.categoriesListView);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                viewCategory(position);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchButtonPressed(searchTextView);
            }
        });

        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchButton.callOnClick();
                    return true;
                }
                return false;
            }
        });

    }


    private void viewCategory(int position)
    {
        Intent i = new Intent(this, HealthServices.class);

        //TODO if name at pos isnt what it should be
        //then iterate over till find name

        i.putExtra("CATEGORY", listItems.get(position)); //Integer.toString(categoryResponse.get(position).id)
        i.putExtra("CATEGORY_ID", categoryResponse.get(position).getId());
        Log.v("rhtds", "HERE. value is: " + categoryResponse.get(position).getId());
        startActivity(i);
    }

    private void searchButtonPressed(View v) {
        String searchTerm = searchTextView.getText().toString();
        Log.d(TAG, searchTerm);


        Intent i = new Intent(this, HealthServices.class);

        i.putExtra("SEARCH_TERM", searchTerm);

        Utils.hideSoftKeyboard(this);

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

      /*  //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}