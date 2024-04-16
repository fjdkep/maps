package com.example.kien101.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kien101.R;
import com.example.kien101.adapters.ShopAdapter;
import com.example.kien101.databinding.DsYeuthichBinding;
import com.example.kien101.models.Shop;
import com.google.firebase.firestore.GeoPoint;

public class ds_yeuthich extends AppCompatActivity {
    //khai bao bien giao dien
    ImageButton btn_QuayLai;
    private ArrayList<Shop> favouriteCafe ;
    private RecyclerView mRecycleCafe;
    private ShopAdapter mCafeAdapter ;
    private DsYeuthichBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DsYeuthichBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRecycleCafe = findViewById(R.id.listFavourite);
        favouriteCafe = new ArrayList<>();
        createCafeList();
        mCafeAdapter = new ShopAdapter(this,favouriteCafe);
        mRecycleCafe.setAdapter(mCafeAdapter);
        mRecycleCafe.setLayoutManager(new LinearLayoutManager(this));
        //anh xa Id cho cac bien giao dien

        addControl();
        addEvent();
    }

    private void addEvent() {

        binding.btnQuaylai.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), home1.class));
        });
        binding.btnNewCoffe.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), goi_y.class));
        });
    }

    private void addControl() {

    }


    //laytt tu csdl
    private void createCafeList() {
        GeoPoint local = new GeoPoint(20.9, 105.7);
        favouriteCafe.add(new Shop( "link","Thor","a1065301","khong gian nho, phucj vu kem",5,"asvf",local));
        favouriteCafe.add(new Shop( "link","Thor","1","khong gian nho, phucj vu kem",5,"Co deien",local));

    }
}