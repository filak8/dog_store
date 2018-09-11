package com.example.lenovo.dogstore.models;

/**
 * Created by lenovo on 12.06.2018.
 */

public class BreedResponse {

    private String status;
    private Breed message;

    public Breed getMessage() {
        return message;
    }

    public void setMessage(Breed message) {
        this.message = message;
    }
}
