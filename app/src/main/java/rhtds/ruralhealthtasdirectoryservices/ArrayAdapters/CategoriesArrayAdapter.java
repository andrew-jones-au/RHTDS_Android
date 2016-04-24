package rhtds.ruralhealthtasdirectoryservices.ArrayAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import RuralHealthAPIClient.Models.Category;
import rhtds.ruralhealthtasdirectoryservices.R;

/**
 * Created by Andrew on 1/11/2016.
 * Some code from http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class CategoriesArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private  ArrayList<Category> categories = new ArrayList<Category>();

    public CategoriesArrayAdapter(Context context, ArrayList<String> values, ArrayList<Category> categories) {
        super(context, -1, values);
        this.context = context;
        this.categories = categories;
    }

    private String[] colours = { "#FDB813", "#F68B1F", "#F17022", "#62C2CC", "#E4F6F8", "#EEF66C"};

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View category = inflater.inflate(R.layout.category_subview, parent, false);

        TextView name = (TextView) category.findViewById(R.id.categoryName);
        name.setText(categories.get(position).getName());

        category.setBackgroundColor(Color.parseColor(colours[position % colours.length ]));//set the colour of the row

        return category;
    }
}
