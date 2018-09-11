package com.example.lenovo.dogstore.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.dogstore.BreedClickListener;
import com.example.lenovo.dogstore.R;
import com.example.lenovo.dogstore.viewHolder.SubBreedViewHolder;

import java.util.ArrayList;

public class RecyclerAdapterForSubBreed extends RecyclerView.Adapter<SubBreedViewHolder> {
    private ArrayList<String> mDatasetSubBreed;
    private BreedClickListener breedClickListener;

    public RecyclerAdapterForSubBreed(ArrayList<String> mDatasetSubBreed,
                                      BreedClickListener breedClickListener) {
        this.mDatasetSubBreed = mDatasetSubBreed;
        this.breedClickListener = breedClickListener;
    }

    @NonNull
    @Override
    public SubBreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_sub_breed, parent, false);
        return new SubBreedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubBreedViewHolder holder, final int position) {
        holder.getmTvSubBtreed().setText(mDatasetSubBreed.get(position));
        holder.getmTvSubBtreed().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                breedClickListener.onBreedClick(mDatasetSubBreed.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatasetSubBreed.size();
    }
}
