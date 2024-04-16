package com.example.kien101.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.kien101.R;
import com.example.kien101.adapters.ShopAdapter;
import com.example.kien101.databinding.ActivityKquaSearchBinding;
import com.example.kien101.models.Shop;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class kqua_search extends AppCompatActivity {

//    ListView lv;
//    ArrayList<Cafe> mylist;
//    CafeArrayAdapter myadapter;

    private ArrayList<Shop> mCafe ;
    private RecyclerView mRecycleCafe;
    private ShopAdapter mCafeAdapter ;
    private ActivityKquaSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKquaSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRecycleCafe = findViewById(R.id.list1);
        mCafe = new ArrayList<>();
        createCafeList();
        mCafeAdapter = new ShopAdapter(this,mCafe);
        mRecycleCafe.setAdapter(mCafeAdapter);
        mRecycleCafe.setLayoutManager(new LinearLayoutManager(this));
        setListener();

//        //khai bao list view
//        lv= (ListView) findViewById(R.id.list1);
//        mylist= new ArrayList<>(); ///tao moi mang rong
//

//
//        myadapter= new CafeArrayAdapter(nSearchActivity.this, R.layout.layout_shop, mylist); //nhap vao (..., ten layout da tao, thong tin cacs quan cafe
//        lv.setAdapter(myadapter);
//
////Tương tác với ListView
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(nSearchActivity.this, mylist.get(position).toString() , Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void setListener() {
        binding.btnQuaylai.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), home1.class));
        });
    }

    private void createCafeList() {
        GeoPoint local = new GeoPoint(10, 20);
        mCafe.add(new Shop("link","Thor", "1", "khong gian nho, phucj vu kem", 5, "Co deien", local));
        mCafe.add(new Shop("link","IronMan", "2", "thai do nhan vien tot nhg menu it mon", 4, "hien dai", local));
    }

//    private void createCafeList() {
//        String name[]={"Quan1","Quan2","Quan3","QUan4"};
//        int vote[]={1,3,2,5};
//        String describe[]={"khong gian nho, phucj vu kem","thai do nhan vien tot nhg menu it mon","nhan vien phuc vu kem ,kbh den nx","10 d k co nhung"};
//
//        for (int i=0;i<name.length; i++){
//            mCafe.add(new Cafe(0,name[i],vote[i],describe[i]));
//        }
//    }
}