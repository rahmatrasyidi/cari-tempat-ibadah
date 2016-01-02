package com.sig.caritempatibadah.fragments;

import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import com.sig.caritempatibadah.EventActivity_;
import com.sig.caritempatibadah.R;
import com.sig.caritempatibadah.database.DBHelper;
import com.sig.caritempatibadah.model.Event;
import com.sig.caritempatibadah.model.History;
import com.sig.caritempatibadah.util.Utilities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Rahmat Rasyidi Hakim on 12/11/2015.
 */

@EFragment(R.layout.tambah_event)
public class FragmentAddEvent extends Fragment {

    @ViewById(R.id.etNamaEvent)
    EditText nama;

    @ViewById(R.id.etDescEvent)
    EditText desc;

    @ViewById(R.id.etTanggalEvent)
    EditText tanggal;

    @ViewById(R.id.etWaktuEvent)
    EditText waktu;

    private DBHelper dbHelper;

    @AfterViews
    void init(){
        setRetainInstance(true);
        dbHelper = new DBHelper(getActivity().getApplicationContext());
    }

    @Click(R.id.btnTambahEvent)
    void tambahEvent(){
        Utilities.hideSoftKeyboard(getActivity());
        if(validasiInput()){
            String time = tanggal.getText().toString() + ", " + waktu.getText().toString();

            if(dbHelper.insertEvent(new Event(nama.getText().toString(), EventActivity_.getAddress(), EventActivity_.getName(), desc.getText().toString(), time))) {
                Toast.makeText(getActivity().getApplicationContext(), "Sukses menambahkan event", Toast.LENGTH_SHORT).show();
                dbHelper.insertHistory(new History(EventActivity_.getName(), EventActivity_.getAddress(), null));
            }
        }
    }

    private boolean validasiInput(){
        if(nama.getText().length() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Nama event masih kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(desc.getText().length() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Deskripsi event masih kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tanggal.getText().length() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Tanggal event masih kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(waktu.getText().length() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Waktu event masih kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
