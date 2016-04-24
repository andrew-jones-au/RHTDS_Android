package rhtds.ruralhealthtasdirectoryservices.ArrayAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import RuralHealthAPIClient.Models.Category;
import RuralHealthAPIClient.Models.HealthService;
import rhtds.ruralhealthtasdirectoryservices.R;

/**
 * Created by Andrew on 1/11/2016.
 * Some code from http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class HealthServicesArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private  List<HealthService> healthServices = new ArrayList<HealthService>();

    public HealthServicesArrayAdapter(Context context, List<String> values, List<HealthService> healthServices) {
        super(context, -1, values);
        this.context = context;
        this.healthServices = healthServices;
    }

    private String[] colours = { "#FDB813", "#F68B1F", "#F17022", "#62C2CC", "#E4F6F8", "#EEF66C"};

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View healthServiceItemView = inflater.inflate(R.layout.service_subview, parent, false);

        TextView name = (TextView) healthServiceItemView.findViewById(R.id.serviceSubviewName);
        name.setText(healthServices.get(position).getTitle());

        healthServiceItemView.setBackgroundColor(Color.parseColor(colours[position % colours.length ]));//set the colour of the row

        return healthServiceItemView;
    }
}
