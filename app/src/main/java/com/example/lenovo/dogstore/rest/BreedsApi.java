package com.example.lenovo.dogstore.rest;

import com.example.lenovo.dogstore.models.BreedResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BreedsApi  {
    @GET("breeds/list/all")
    Call<BreedResponse> getBreeds();
}
