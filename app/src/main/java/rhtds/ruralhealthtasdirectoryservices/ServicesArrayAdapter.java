package rhtds.ruralhealthtasdirectoryservices;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 1/11/2016.
 * Some code from http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class ServicesArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private  ArrayList<Service> services = new ArrayList<Service>();

    private boolean receivedData = false;

    public ServicesArrayAdapter(Context context, ArrayList<String> values, ArrayList<Service> services) {
        super(context, -1, values);
        this.context = context;
        this.services = services;

        receivedData = true;
    }

    private String[] colours = { "#FDB813", "#F68B1F", "#F17022", "#62C2CC", "#E4F6F8", "#EEF66C"};

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View service = inflater.inflate(R.layout.service_subview, parent, false);

        if(receivedData) {
            try {
                TextView name = (TextView) service.findViewById(R.id.serviceSubviewName);
                name.setText(services.get(position).getName());

                TextView website = (TextView) service.findViewById(R.id.serviceSubviewWebsite);
                //ImageView websiteImageView = (ImageView) service.findViewById(R.id.websiteIcon);
                website.setText(services.get(position).getWebsite());

                TextView email = (TextView) service.findViewById(R.id.serviceSubviewEmail);
                //ImageView emailImageView = (ImageView) service.findViewById(R.id.emailIcon);
                email.setText(services.get(position).getEmail());

                TextView phone = (TextView) service.findViewById(R.id.serviceSubviewPhone);
                //ImageView phoneImageView = (ImageView) service.findViewById(R.id.phoneIcon);
                phone.setText(services.get(position).getPhone());

                TextView address = (TextView) service.findViewById(R.id.serviceSubviewAddress);
                //ImageView addressImageView = (ImageView) service.findViewById(R.id.addressIcon);
                address.setText(services.get(position).getAddress());

                //ImageView imageView = (ImageView) service.findViewById(R.id.logoIcon);
                //imageView.setImageBitmap(services.get(position).getBitmap());
            }
            catch(Exception e) {
                //e.printStackTrace(); //TODO: this is dodgy as, find out where it gets called that size is 0
            }

        }

        service.setBackgroundColor(Color.parseColor(colours[position % colours.length ]));//set the colour of the row

        return service;
    }
}
