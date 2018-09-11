package com.example.lenovo.dogstore.rest;

import com.example.lenovo.dogstore.models.BreedImageUrlResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageUrlApi {

    @GET("{BREED}/{SUB_BREED}/images/random")
    Call<BreedImageUrlResponse> getImageUrl(@Path("BREED") String breed, @Path("SUB_BREED") String subBreed);
}
