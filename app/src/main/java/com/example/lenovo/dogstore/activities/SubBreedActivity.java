package com.example.lenovo.dogstore.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.dogstore.BreedClickListener;
import com.example.lenovo.dogstore.R;
import com.example.lenovo.dogstore.adapters.RecyclerAdapterForSubBreed;
import com.example.lenovo.dogstore.constants.Constants;
import com.example.lenovo.dogstore.dataBase.DBHelper;
import com.example.lenovo.dogstore.models.BreedImageUrlResponse;
import com.example.lenovo.dogstore.models.Dog;
import com.example.lenovo.dogstore.rest.ImageUrlApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubBreedActivity extends AppCompatActivity implements BreedClickListener {

    private String mTargetSubBreed;
    private String mTargetBreed;
    private String mUriImageSubBreed;
    private int DIALOG = 1;

    ImageUrlApi imageUrlApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_breed);

        Intent intent = getIntent();
        TextView mTvBreadCrumbs = (TextView) findViewById(R.id.bread_crumbs);

        mTvBreadCrumbs.setText(getResources().getString(R.string.text_view_bread_crumbs));
        mTvBreadCrumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RecyclerView rvSubBreed = (RecyclerView) findViewById(R.id.recycler_view_sub_breed);
        rvSubBreed.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter rvSubBreedAdapter = new RecyclerAdapterForSubBreed
                (intent.getStringArrayListExtra(Constants.ARGS_KEY_ARRAY_LIST_SUB_BREED),
                        SubBreedActivity.this);
        rvSubBreed.setAdapter(rvSubBreedAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.ARGS_KEY_TARGET_BREED, mTargetBreed);
        outState.putString(Constants.ARGS_KEY_TARGET_SUB_BREED, mTargetSubBreed);
        outState.putString(Constants.ARGS_KEY_URI_IMAGE_SUB_BREED, mUriImageSubBreed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTargetBreed = savedInstanceState.getString(Constants.ARGS_KEY_TARGET_BREED);
        mTargetSubBreed = savedInstanceState.getString(Constants.ARGS_KEY_TARGET_SUB_BREED);
        mUriImageSubBreed = savedInstanceState.getString(Constants.ARGS_KEY_URI_IMAGE_SUB_BREED);
    }

    @Override
    public void onBreedClick(String breed) {
        mTargetSubBreed = breed;
        ArrayList<Dog> arrayListDogs = new ArrayList<>(new DBHelper
                (this).getDogsFromDataBase());

        for (int i = 0; i < arrayListDogs.size(); i++)
            if (arrayListDogs.get(i).getSub_breed().equals(breed))
                mTargetBreed = arrayListDogs.get(i).getBreed();
        getImageUri(mTargetBreed, mTargetSubBreed);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater()
                .inflate(R.layout.dialog_image_sub_breed, null);
        builder.setPositiveButton(getResources()
                .getString(R.string.dialog_positive_button), myClickListener);
        builder.setNegativeButton(getResources()
                .getString(R.string.dialog_negative_button), myClickListener);
        builder.setView(constraintLayout);
        return builder.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG) {
            ImageView mIvSubBreedImage = dialog.getWindow()
                    .findViewById(R.id.image_view_sub_breed_dog);
            Picasso.get()
                    .load(mUriImageSubBreed)
                    .placeholder(R.drawable.corgi_new1)
                    .resize(Constants.PICASSO_RESIZE_TARGET_WIDTH,
                            Constants.PICASSO_RESIZE_TARGET_HEIGTH)
                    .centerCrop()
                    .into(mIvSubBreedImage);
        }
    }

    public void getImageUri(String mTargetBreed, String mTargetSubBreed) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.ARGS_KEY_BASE_URL_PART_TWO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        imageUrlApi = retrofit.create(ImageUrlApi.class);

        imageUrlApi.getImageUrl(mTargetBreed, mTargetSubBreed)
                .enqueue(new Callback<BreedImageUrlResponse>() {
            @Override
            public void onResponse(Call<BreedImageUrlResponse> call,
                                   Response<BreedImageUrlResponse> response) {
                mUriImageSubBreed = response.body().getMessage();
                showDialog(DIALOG);
            }

            @Override
            public void onFailure(Call<BreedImageUrlResponse> call, Throwable t) {
                Toast.makeText(SubBreedActivity.this, getResources()
                        .getString(R.string.retrofit_on_failure), Toast.LENGTH_SHORT).show();
            }
        });
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    getImageUri(mTargetBreed, mTargetSubBreed);
                    break;
            }
        }
    };
}

