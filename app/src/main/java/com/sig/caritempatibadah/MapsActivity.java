package com.sig.caritempatibadah;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sig.caritempatibadah.util.Utilities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

@EActivity
public class MapsActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private final String MAPS_LOCATION_KEY = "YOUR_KEY_HERE";
    private final String RADIUS = "1112";
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private AsyncHttpClient mClient;
    private double curLatitude, curLongitude;
    private LocationManager locationManager;

    @ViewById(R.id.etCari)
    EditText etCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMap().setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);
        mapFragment.getMapAsync(this);
    }

    private void initTempatIbadah(String type){
        mClient = new AsyncHttpClient();
        if(Utilities.isConnectedNetwork(this, true)){
            mClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("radius", RADIUS);
            params.add("types", type);
            params.add("key", MAPS_LOCATION_KEY);
            params.add("sensor", "true");
            String location = curLatitude + "," + curLongitude;
            //String location = "-6.364513,106.828659";
            Log.d("location", location + "");
            mClient.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+location, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        Log.d("type", (String)response.getJSONArray("results").getJSONObject(0).getJSONArray("types").get(0));
                        if (response.getJSONArray("results").length() > 0) {
                            for(int i = 0 ; i < response.getJSONArray("results").length(); i++) {
                                double latitude = response.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                double longitude = response.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                                String name = response.getJSONArray("results").getJSONObject(i).getString("name");
                                String vicinity = response.getJSONArray("results").getJSONObject(i).getString("vicinity");
                                String type = (String) response.getJSONArray("results").getJSONObject(i).getJSONArray("types").get(0);

                                addMarker(latitude, longitude, name, vicinity, type);
                            }
                        }
                    } catch (JSONException e) {

                    }

                }
            });
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation(mMap);
        initTempatIbadah("mosque|church|hindu_temple");
    }

    public void getCurrentLocation(GoogleMap map){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.364513, 106.828659), 15));

        //null mulu locationnya
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 15));
            curLatitude = location.getLatitude();
            curLongitude = location.getLongitude();
        }
    }

    private void addMarker(double latitude, double longitude, String name, String vicinity, String type){
        LatLng agent = new LatLng(latitude, longitude);
        if(type.equalsIgnoreCase("church")){
            mMap.addMarker(new MarkerOptions().position(agent).title(name).snippet(vicinity).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    EventActivity_.intent(getApplicationContext()).extra("name", marker.getTitle()).extra("address", marker.getSnippet()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                }
            });
        } else if(type.equalsIgnoreCase("hindu_temple")){
            mMap.addMarker(new MarkerOptions().position(agent).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).title(name).snippet(vicinity));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    EventActivity_.intent(getApplicationContext()).extra("name", marker.getTitle()).extra("address", marker.getSnippet()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                }
            });
        } else if(type.equalsIgnoreCase("mosque")){
            mMap.addMarker(new MarkerOptions().position(agent).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(name).snippet(vicinity));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    EventActivity_.intent(getApplicationContext()).extra("name", marker.getTitle()).extra("address", marker.getSnippet()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                }
            });
        } else {
            mMap.addMarker(new MarkerOptions().position(agent).title(name).snippet(vicinity));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    EventActivity_.intent(getApplicationContext()).extra("name", marker.getTitle()).extra("address", marker.getSnippet()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                }
            });
        }

    }
    
    @Click(R.id.ivMasjid)
    void mesjidClick(){
        mMap.clear();
        initTempatIbadah("mosque");
    }

    @Click(R.id.ivGereja)
    void gerejaClick(){
        mMap.clear();
        initTempatIbadah("church");
    }

    @Click(R.id.ivCandi)
    void candiClick(){
        mMap.clear();
        initTempatIbadah("hindu_temple");
    }

    @Click(R.id.ivEvent)
    void eventClick(){
        EventListActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
    }

    @Click(R.id.ivHistory)
    void historyClick(){
        HistoryActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
    }

    @Click(R.id.btnCari)
    void cariClick(){
        mMap.clear();
        Utilities.hideSoftKeyboard(this);
        cari();
    }

    private void cari(){
        mClient = new AsyncHttpClient();
        if(Utilities.isConnectedNetwork(this, true) && etCari.getText().toString().length() != 0){
            mClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("key", MAPS_LOCATION_KEY);
            params.add("query", etCari.getText().toString());
            Log.d("etCari", etCari.getText().toString());
            mClient.get("https://maps.googleapis.com/maps/api/place/textsearch/json?", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if (response.getJSONArray("results").length() > 0) {
                            double latitude = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                            double longitude = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 12));

                            for(int i = 0 ; i < response.getJSONArray("results").length(); i++) {
                                latitude = response.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                longitude = response.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                                String name = response.getJSONArray("results").getJSONObject(i).getString("name");
                                String vicinity = response.getJSONArray("results").getJSONObject(i).getString("formatted_address");
                                String type = (String) response.getJSONArray("results").getJSONObject(i).getJSONArray("types").get(0);
                                Log.d("panjang array", response.getJSONArray("results").length() + "");
                                addMarker(latitude, longitude, name, vicinity, type);
                            }
                        }
                    } catch (JSONException e) {

                    }

                }
            });
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        curLatitude = location.getLatitude();
        curLongitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS is turned on!! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "GPS is turned off!! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}
