package com.sig.caritempatibadah;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.sig.caritempatibadah.adapter.HistoriesListAdapter;
import com.sig.caritempatibadah.database.DBHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Rahmat Rasyidi Hakim on 12/11/2015.
 */

@EActivity(R.layout.history)
public class HistoryActivity extends AppCompatActivity {

    @ViewById(R.id.lvHistory)
    ListView histories;

    private HistoriesListAdapter adapter;
    private DBHelper dbHelper;
    private Dialog.Builder builder;

    @Click(R.id.linearLayoutBackHistory)
    void back(){
        onBackPressed();
    }

    @AfterViews
    void init(){
        dbHelper = new DBHelper(this);
        if(dbHelper.getAllHistories().size() == 0){
            Toast.makeText(this, "History masih kosong", Toast.LENGTH_LONG).show();
        } else {
            adapter = new HistoriesListAdapter(this, dbHelper.getAllHistories());
            histories.setAdapter(adapter);
            histories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    addTestimoni(view.findViewById(R.id.tvTempatHistory).toString(), view.findViewById(R.id.tvAlamatHistory).toString());
                }
            });
        }
    }

    private void addTestimoni(final String place, final String address){
        builder = new SimpleDialog.Builder(R.style.AlertDialog_AppCompat_Light){

            @Override
            protected void onBuildDone(final Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Button update = (Button) dialog.findViewById(R.id.tvUpdate);
                final EditText testi = (EditText) dialog.findViewById(R.id.etTestimoni);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelper.updateHistory(place, address, testi.getText().toString());
                        dialog.dismiss();
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

        builder.contentView(R.layout.update_layout);
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }
}
