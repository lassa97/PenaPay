package com.lassa97.penapay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lassa97.penapay.Entities.Event;
import com.lassa97.penapay.R;

import java.util.ArrayList;

public class EventsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Event> events;

    public EventsAdapter (Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    public void remove (int position) {
        events.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.event_item, parent, false);

        TextView eventID = convertView.findViewById(R.id.eventID);
        TextView eventTitle = convertView.findViewById(R.id.eventTitle);
        TextView eventDate = convertView.findViewById(R.id.eventDate);

        Event event = getItem(position);

        eventID.setText(String.valueOf(event.eventID));
        eventTitle.setText(event.title);
        eventDate.setText(event.image);

        return convertView;
    }
}
