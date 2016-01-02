package com.sig.caritempatibadah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sig.caritempatibadah.R;
import com.sig.caritempatibadah.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahmat Rasyidi Hakim on 12/14/2015.
 */
public class EventListAdapter extends ArrayAdapter<Event>{

    private static class ViewHolder{
        TextView eventName;
        TextView eventPlace;
        TextView eventDesc;
        TextView eventTime;
        TextView eventAddress;
    }

    public EventListAdapter(Context context, List<Event> events) {
        super(context, R.layout.event_layout, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.event_layout, parent, false);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.tvJudulEvent);
            viewHolder.eventPlace = (TextView) convertView.findViewById(R.id.tvTempatEvent);
            viewHolder.eventTime = (TextView) convertView.findViewById(R.id.tvWaktuEvent);
            viewHolder.eventDesc = (TextView) convertView.findViewById(R.id.tvDeskripsiEvent);
            viewHolder.eventAddress = (TextView) convertView.findViewById(R.id.tvAlamatEvent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.eventName.setText(event.getName());
        viewHolder.eventPlace.setText(event.getPlace());
        viewHolder.eventAddress.setText(event.getAddress());
        viewHolder.eventTime.setText(event.getTime());
        viewHolder.eventDesc.setText(event.getDescription());
        return convertView;
    }
}
