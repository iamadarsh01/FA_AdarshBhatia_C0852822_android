package com.firstapp.fa_adarshbhatia_c0852822_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddNew extends AppCompatActivity  implements OnMapReadyCallback,
        LocationListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    EditText et_name,et_address;
    CheckBox cb;
    Button btn;
    Button bt1, bt2, bt3, bt4;

    String plname,address,pdate,visited;
    double latitude,longitude, latitude_add,longitude_add;

    private GoogleMap mMap;
    Marker marker_add;
    MarkerOptions markerOptions_add;


    LocationManager lm;

    String s_address, s_city, s_state, s_country, s_postalCode;
    String s_title;

    int d,m,y;
    String date;

    int i=0;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        fetchLocation();

        et_name=findViewById(R.id.et_plname);
        et_address=findViewById(R.id.et_address);
        cb=findViewById(R.id.checkBox);
        btn=findViewById(R.id.button);
        bt1=findViewById(R.id.bt1);
        bt2=findViewById(R.id.bt2);
        bt3=findViewById(R.id.bt3);
        bt4=findViewById(R.id.bt4);

        databaseHelper=new DatabaseHelper(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                plname=et_name.getText().toString().trim();
                address=et_address.getText().toString().trim();

                if(plname.equals(""))
                {
                    et_name.setError("enter place name");
                }
                else if(address.equals(""))
                {
                    et_address.setError("enter address");
                }
                else {
                    if (cb.isChecked()) {
                        visited = "Visited";
                    } else {
                        visited = "Not visited yet";
                    }

                    Bean b = new Bean();
                    b.setPlacename(plname);
                    b.setAddress(address);
                    b.setLatitude(String.valueOf(latitude_add));
                    b.setLongitude(String.valueOf(longitude_add));
                    b.setVisited(visited);
                    b.setCreatedon(pdate);

                    boolean t = databaseHelper.insert(b);

                    if (t) {
                        Toast.makeText(AddNew.this, "location is saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddNew.this, "error while saving location", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void fetchLocation()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(AddNew.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(AddNew.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddNew.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }
        }

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        mMap=googleMap;

        mMap.clear();

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(AddNew.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(AddNew.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude=location.getLatitude();
        longitude=location.getLongitude();

        if(i==0)
        {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
        }

        i=1;

    }
    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

        latitude_add=marker.getPosition().latitude;
        longitude_add=marker.getPosition().longitude;

        Log.e("latitude drag",""+latitude_add);
        Log.e("longitude drag",""+longitude_add);

        Geocoder geocoder = new Geocoder(AddNew.this, Locale.getDefault());

        List<Address> addresses = null;
        try {
            if(addresses!=null && addresses.size()>0) {

                addresses = geocoder.getFromLocation(latitude_add, longitude_add, 1);
                s_address = addresses.get(0).getAddressLine(0);
                s_city = addresses.get(0).getLocality();
                s_state = addresses.get(0).getAdminArea();
                s_country = addresses.get(0).getCountryName();
                s_postalCode = addresses.get(0).getPostalCode();

                s_title = s_address + ",\n" + s_city + ",\n" + s_state + "," + s_country;
                marker_add.setTitle(s_address);
                marker_add.showInfoWindow();

                et_address.setText(s_address);
            }
            else
            {
                Calendar ca = Calendar.getInstance();

                d=ca.get(Calendar.DATE);
                m=ca.get(Calendar.MONTH)+1;
                y=ca.get(Calendar.YEAR);

                String d2,m2;

                if(d<10)
                {
                    d2="0"+d;
                }
                else
                {
                    d2=String.valueOf(d);
                }
                if(m<10)
                {
                    m2="0"+m;
                }
                else
                {
                    m2=String.valueOf(m);
                }

                date=d2+"/"+m2+"/"+y;

                marker_add.setTitle(date);
                marker_add.showInfoWindow();

                et_address.setText("couldn't fetch address");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses==null)
        {
            Calendar ca = Calendar.getInstance();

            d=ca.get(Calendar.DATE);
            m=ca.get(Calendar.MONTH)+1;
            y=ca.get(Calendar.YEAR);

            String d2,m2;

            if(d<10)
            {
                d2="0"+d;
            }
            else
            {
                d2=String.valueOf(d);
            }
            if(m<10)
            {
                m2="0"+m;
            }
            else
            {
                m2=String.valueOf(m);
            }

            date=d2+"/"+m2+"/"+y;

            marker_add.setTitle(date);
            marker_add.showInfoWindow();

            et_address.setText("couldn't fetch address");

        }
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng)
    {
        if(marker_add!=null)
        {
            marker_add.remove();
        }
        latitude_add=latLng.latitude;
        longitude_add=latLng.longitude;

        Log.e("latitude click",""+latitude_add);
        Log.e("longitude click",""+longitude_add);

        markerOptions_add = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).draggable(true).title("New Marker");

        markerOptions_add.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        marker_add=mMap.addMarker(markerOptions_add);

        Geocoder geocoder = new Geocoder(AddNew.this, Locale.getDefault());

        List<Address> addresses = null;
        try {

            addresses = geocoder.getFromLocation(latitude_add, longitude_add, 1);

            if(addresses!=null && addresses.size()>0)
            {
                s_address = addresses.get(0).getAddressLine(0);
                s_city = addresses.get(0).getLocality();
                s_state = addresses.get(0).getAdminArea();
                s_country = addresses.get(0).getCountryName();
                s_postalCode = addresses.get(0).getPostalCode();

                s_title = s_address + "," + s_city + "," + s_state + "," + s_country;
                marker_add.setTitle(s_address);
                marker_add.showInfoWindow();

                et_address.setText(s_address);
            }
            else
            {
                Calendar ca = Calendar.getInstance();

                d=ca.get(Calendar.DATE);
                m=ca.get(Calendar.MONTH)+1;
                y=ca.get(Calendar.YEAR);

                String d2,m2;

                if(d<10)
                {
                    d2="0"+d;
                }
                else
                {
                    d2=String.valueOf(d);
                }
                if(m<10)
                {
                    m2="0"+m;
                }
                else
                {
                    m2=String.valueOf(m);
                }

                date=d2+"/"+m2+"/"+y;

                marker_add.setTitle(date);
                marker_add.showInfoWindow();

                et_address.setText("couldn't fetch address");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses==null)
        {
            Calendar ca = Calendar.getInstance();

            d=ca.get(Calendar.DATE);
            m=ca.get(Calendar.MONTH)+1;
            y=ca.get(Calendar.YEAR);

            String d2,m2;

            if(d<10)
            {
                d2="0"+d;
            }
            else
            {
                d2=String.valueOf(d);
            }
            if(m<10)
            {
                m2="0"+m;
            }
            else
            {
                m2=String.valueOf(m);
            }

            date=d2+"/"+m2+"/"+y;

            marker_add.setTitle(date);
            marker_add.showInfoWindow();

            et_address.setText("couldn't fetch address");

        }

        mMap.setOnMarkerDragListener(this);

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

}