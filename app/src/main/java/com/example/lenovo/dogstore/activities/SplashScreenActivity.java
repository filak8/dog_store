package com.example.lenovo.dogstore.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.dogstore.R;
import com.example.lenovo.dogstore.constants.Constants;
import com.example.lenovo.dogstore.dataBase.DBHelper;
import com.example.lenovo.dogstore.models.BreedResponse;
import com.example.lenovo.dogstore.rest.BreedsApi;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreenActivity extends AppCompatActivity implements Serializable {

    private ArrayList<String> mBreedList = new ArrayList<>();
    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView mTvAppName = (TextView) findViewById(R.id.text_view_app_name);
        Typeface mTfFont = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.app_name_font));
        mTvAppName.setTypeface(mTfFont);

        dbHelper = new DBHelper(this);
        getBreeds();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,
                        StoreScreenActivity.class);
                startActivity(intent);
            }
        }, Constants.HANDLER_DELAY_MILLIS);

        ProgressBar mPbLoader = (ProgressBar) findViewById(R.id.progress_bar_loader);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mPbLoader,
                Constants.ARGS_KEY_PROPERTY_NAME,
                Constants.OBJECT_ANIMATOR_MIN_VALUES_MILLIS,
                Constants.OBJECT_ANIMATOR_MAX_VALUES_MILLIS);
        objectAnimator.setDuration(Constants.OBJECT_ANIMATOR_MAX_VALUES_MILLIS);
        objectAnimator.start();
    }

    public void getBreeds() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.ARGS_KEY_BASE_URL_PART_ONE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BreedsApi breedsApi = retrofit.create(BreedsApi.class);
        breedsApi.getBreeds().enqueue(new Callback<BreedResponse>() {
            @Override
            public void onResponse(@NonNull Call<BreedResponse> call,
                                   @NonNull Response<BreedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    mBreedList.addAll(response.body().getMessage().getBulldog());
                    dbHelper.fillOrUpdateDataBase(mBreedList,
                            getResources().getString(R.string.breed_bulldog));
                    mBreedList.clear();


                    mBreedList.addAll(response.body().getMessage().getHound());
                    dbHelper.fillDataBase(mBreedList,
                            getResources().getString(R.string.breed_hound));
                    mBreedList.clear();


                    mBreedList.addAll(response.body().getMessage().getMastiff());
                    dbHelper.fillDataBase(mBreedList,
                            getResources().getString(R.string.breed_mastiff));
                    mBreedList.clear();

                    mBreedList.addAll(response.body().getMessage().getMountain());
                    dbHelper.fillDataBase(mBreedList,
                            getResources().getString(R.string.breed_mountain));
                    mBreedList.clear();

                    mBreedList.addAll(response.body().getMessage().getRetriever());
                    dbHelper.fillDataBase(mBreedList,
                            getResources().getString(R.string.breed_retriever));
                    mBreedList.clear();
                }
            }

            @Override
            public void onFailure(Call<BreedResponse> call, Throwable t) {
                Toast.makeText(SplashScreenActivity.this,
                        getResources().getString(R.string.retrofit_on_failure),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
