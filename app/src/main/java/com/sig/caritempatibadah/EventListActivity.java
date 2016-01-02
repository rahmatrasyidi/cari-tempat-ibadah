package com.sig.caritempatibadah;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.sig.caritempatibadah.adapter.EventListAdapter;
import com.sig.caritempatibadah.database.DBHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Rahmat Rasyidi Hakim on 12/11/2015.
 */

@EActivity(R.layout.list_event)
public class EventListActivity extends AppCompatActivity {

    @ViewById(R.id.lvEventList)
    ListView events;

    EventListAdapter adapter;
    DBHelper dbHelper;

    private Dialog.Builder builder;

    @AfterViews
    void init(){
        dbHelper = new DBHelper(this);
        if(dbHelper.getAllEvents().size() == 0){
            Toast.makeText(this, "Tidak ada event", Toast.LENGTH_LONG).show();
        } else {
            adapter = new EventListAdapter(this, dbHelper.getAllEvents());
            events.setAdapter(adapter);
            events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    deleteEvent(position);
                }
            });
        }

    }

    @Click(R.id.linearLayoutBackEvent)
    void back(){
        onBackPressed();
    }


    private void deleteEvent(final int pos){
        builder = new SimpleDialog.Builder(R.style.AlertDialog_AppCompat_Light){

            @Override
            protected void onBuildDone(final Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView delete = (TextView) dialog.findViewById(R.id.tvDelete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dbHelper.deleteEvent(pos);
                    }
                });
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.contentView(R.layout.delete_layout);
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }
}
