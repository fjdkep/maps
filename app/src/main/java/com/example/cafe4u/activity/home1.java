package com.example.cafe4u.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.cafe4u.R;
import com.example.cafe4u.ultility.Constants;
import com.example.cafe4u.ultility.PreferenceManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.List;

public class home1 extends FragmentActivity implements OnMapReadyCallback {

    private PreferenceManager preferenceManager;

    private final int FINE_PERMISSION_CODE = 100; //yeu cau truy nhap
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentUserLocationMarker;

    GoogleMap mMap;
    SupportMapFragment mapFragment;
//    BottomNavigationView bottom_menu;

//    SearchView search;
    AutoCompleteTextView search;
    ImageButton btnFilter, btnGroup, btnHome, btnProfile, btnLike, btnTarget, search_btn;

//    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this); //location
        getLastLocation();//location

        preferenceManager = new PreferenceManager(getApplicationContext());
        getToken();

        addControls();

        String[] suggestions = {"Coffee Shop 1", "Coffee Shop 2", "Cafeteria", "Coffee House", "Tea House", "Bakery"};

// Tạo một ArrayAdapter để liên kết với AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);

// Đặt adapter cho AutoCompleteTextView
        search.setAdapter(adapter);

// Đặt độ dài tối thiểu của văn bản cần nhập trước khi gợi ý xuất hiện
        search.setThreshold(1);
        addEvent();
    }


    private void addEvent() {
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String location = search.getQuery().toString();
//                List<Address> addressList = null;
//                if( location!=null){
//                    Geocoder geocoder = new Geocoder(home1.this);
//                    try {
//                        addressList = geocoder.getFromLocationName(location,1);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                    MarkerOptions options = new MarkerOptions().position(latLng);
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                    mMap.addMarker(options);
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home1.this, home2.class);
                startActivity(i);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home1.this, tai_khoan.class);
                startActivity(i);
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home1.this, ds_yeuthich.class);
                startActivity(i);
            }
        });

        btnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home1.this, HomeChatActivity.class);
                startActivity(i);
            }
        });

        btnTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = search.getText().toString();
                performSearch(query);
            }
        });

    }


    private void performSearch(String query) {
        if (!query.isEmpty()) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addresses = geocoder.getFromLocationName(query, 10);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng location = new LatLng(address.getLatitude(), address.getLongitude());

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                    mMap.addMarker(new MarkerOptions().position(location).title(query));
                } else {
                    Toast.makeText(this, "Không tìm thấy địa chỉ phù hợp", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi khi thực hiện tìm kiếm", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập địa chỉ cần tìm kiếm", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        search = findViewById(R.id.search);
        btnFilter = findViewById(R.id.btnFilter);
//        bottom_menu = findViewById(R.id.bottom_menu);
//        bottom_menu =  findViewById(R.id.bottom_menu);
        btnGroup = findViewById(R.id.btnGroup);
        btnHome = findViewById(R.id.btnHome);
        btnLike = findViewById(R.id.btnLike);
        btnProfile = findViewById(R.id.btnProfile);
        btnTarget = findViewById(R.id.btnTarget);
        search_btn = findViewById(R.id.search_btn);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ptit = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()); //location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ptit, 15));
        MarkerOptions options = new MarkerOptions().position(ptit).title("You are here");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        currentUserLocationMarker = mMap.addMarker(options);
    }

    private void showToast(String notice) {
        Toast.makeText(getApplicationContext(), notice, Toast.LENGTH_SHORT).show();
    }
    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }
    private void updateToken(String token) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID));
        documentReference.update(Constants.KEY_FCM_TOKEN, token)

                .addOnFailureListener(e -> showToast("Unable update"));
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(home1.this);
                    if (currentUserLocationMarker != null)
                    {
                        currentUserLocationMarker.remove();
                    }
                }
                else{
                    Toast.makeText(home1.this,"Hãy bật định vị", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //yeu cau truy cap
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else{
                Toast.makeText(this,"Cho phép truy cập vị trí", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

