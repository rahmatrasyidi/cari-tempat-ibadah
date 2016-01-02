package com.sig.caritempatibadah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.sig.caritempatibadah.adapter.EventAdapter;
import com.sig.caritempatibadah.custom.ViewPagerAnnotation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Rahmat Rasyidi Hakim on 12/10/2015.
 */

@EActivity(R.layout.event)
public class EventActivity extends AppCompatActivity {

    @ViewById(R.id.tabsEvent)
    PagerSlidingTabStrip tabs;

    @ViewById(R.id.pagerEvent)
    ViewPagerAnnotation pager;

    public static final int TAB_SHOW_EVENT = 0;
    public static String name, address;

    @InstanceState
    int topTabs = TAB_SHOW_EVENT;

    @ViewById(R.id.tvTitleEvent)
    TextView title;

    @AfterViews
    void init(){
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(new EventAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        title.setText(name);
    }

    public static String getAddress(){
        return address;
    }

    public static String getName(){
        return name;
    }


    @Click(R.id.linearLayoutBackEvent)
    void back(){
        onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
