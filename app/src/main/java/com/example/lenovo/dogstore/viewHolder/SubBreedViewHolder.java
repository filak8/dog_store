package com.example.lenovo.dogstore.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.dogstore.R;

public class SubBreedViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvSubBtreed;

    public SubBreedViewHolder(View itemView) {
        super(itemView);
        mTvSubBtreed = (TextView) itemView.findViewById(R.id.text_view_sub_breed);
    }

    public TextView getmTvSubBtreed() {
        return mTvSubBtreed;
    }
}
