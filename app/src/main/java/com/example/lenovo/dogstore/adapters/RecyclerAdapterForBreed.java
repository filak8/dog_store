package com.example.lenovo.dogstore.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.dogstore.BreedClickListener;
import com.example.lenovo.dogstore.R;
import com.example.lenovo.dogstore.viewHolder.BreedViewHolder;

import java.util.ArrayList;

public class RecyclerAdapterForBreed extends RecyclerView.Adapter<BreedViewHolder> {

    private ArrayList<String> mDatasetBreeds;
    private BreedClickListener breedClickListener;

    public RecyclerAdapterForBreed(ArrayList<String> dataset,
                                   BreedClickListener breedClickListener) {
        this.mDatasetBreeds = dataset;
        this.breedClickListener = breedClickListener;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_breed, parent, false);

        return new BreedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, final int position) {
        holder.getmTvBreed().setText(mDatasetBreeds.get(position));
        holder.getmTvBreed().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                breedClickListener.onBreedClick(mDatasetBreeds.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatasetBreeds.size();
    }
}
