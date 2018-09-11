package com.example.lenovo.dogstore.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.dogstore.BreedClickListener;
import com.example.lenovo.dogstore.R;
import com.example.lenovo.dogstore.adapters.RecyclerAdapterForBreed;
import com.example.lenovo.dogstore.constants.Constants;
import com.example.lenovo.dogstore.dataBase.DBHelper;
import com.example.lenovo.dogstore.models.Dog;

import java.util.ArrayList;
import java.util.HashSet;

public class StoreScreenActivity extends AppCompatActivity implements BreedClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        RecyclerView mRvBreed = (RecyclerView) findViewById(R.id.recycler_view_breed);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvBreed.setLayoutManager(linearLayoutManager);
        DBHelper dbHelper = new DBHelper(this);

        ArrayList<Dog> arrayList = new ArrayList<>(dbHelper.getDogsFromDataBase());
        ArrayList<String> breeds = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            Dog dog = arrayList.get(i);
            String breed = dog.getBreed();
            breeds.add(breed);
        }

        ArrayList<String> originBreads = new ArrayList<>(new HashSet<>(breeds));
        RecyclerView.Adapter mAdapter = new RecyclerAdapterForBreed(originBreads,
                StoreScreenActivity.this);
        mRvBreed.setAdapter(mAdapter);
    }

    @Override
    public void onBreedClick(String breed) {
        ArrayList<Dog> arrayList = new ArrayList<>(new DBHelper(this)
                .getDogsFromDataBase());
        ArrayList<String> arrayListSubBreed = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++){
           if (arrayList.get(i).getBreed().equals(breed))
                arrayListSubBreed.add(arrayList.get(i).getSub_breed());
        }

        Intent intent = new Intent(this, SubBreedActivity.class);
        intent.putExtra(Constants.ARGS_KEY_ARRAY_LIST_SUB_BREED, arrayListSubBreed);
        startActivity(intent);
    }
}
