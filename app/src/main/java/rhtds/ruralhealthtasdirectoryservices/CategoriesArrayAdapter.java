package rhtds.ruralhealthtasdirectoryservices;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 1/11/2016.
 * Some code from http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class CategoriesArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private  ArrayList<Categories.Category> categories = new ArrayList<Categories.Category>();
    private boolean receivedData = false;

    public CategoriesArrayAdapter(Context context, ArrayList<String> values, ArrayList<Categories.Category> categories) {
        super(context, -1, values);
        this.context = context;
        this.categories = categories;

        receivedData = true;
    }

    private String[] colours = { "#FDB813", "#F68B1F", "#F17022", "#62C2CC", "#E4F6F8", "#EEF66C"};

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View category = inflater.inflate(R.layout.category_subview, parent, false);

        if(receivedData) {
            try {
                TextView name = (TextView) category.findViewById(R.id.categoryName);
                name.setText(categories.get(position).getName());
            } catch (Exception e) {
                //e.printStackTrace(); //TODO: this is dodgy as, find out where it gets called that size is 0
            }
        }

        category.setBackgroundColor(Color.parseColor(colours[position % colours.length ]));//set the colour of the row


        return category;
    }
}
