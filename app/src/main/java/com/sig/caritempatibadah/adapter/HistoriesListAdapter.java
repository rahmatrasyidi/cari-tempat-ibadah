package com.sig.caritempatibadah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sig.caritempatibadah.R;
import com.sig.caritempatibadah.model.Event;
import com.sig.caritempatibadah.model.History;

import java.util.List;

/**
 * Created by Rahmat Rasyidi Hakim on 12/14/2015.
 */
public class HistoriesListAdapter extends ArrayAdapter<History>{

    private static class ViewHolder{
        TextView historyPlace;
        TextView historyTesti;
        TextView historyAddress;
    }

    public HistoriesListAdapter(Context context, List<History> histories) {
        super(context, R.layout.history_layout, histories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        History history = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.history_layout, parent, false);
            viewHolder.historyPlace = (TextView) convertView.findViewById(R.id.tvTempatHistory);
            viewHolder.historyAddress = (TextView) convertView.findViewById(R.id.tvAlamatHistory);
            viewHolder.historyTesti = (TextView) convertView.findViewById(R.id.tvTestimoniHistory);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.historyPlace.setText(history.getPlace());
        viewHolder.historyAddress.setText(history.getAddress());
        if(history.getTestimoni() == null){
            viewHolder.historyTesti.setText("Belum ada testimoni");
        } else {
            viewHolder.historyTesti.setText(history.getTestimoni());
        }
        return convertView;
    }
}
