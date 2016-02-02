package com.example.scott.scotchtaster;

import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
    private List<Drink> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView vDrinkName;
        protected TextView vDrinkPrice;
        protected TextView vDrinkRating;
        protected ImageView vDrinkPicture;

        public DrinkViewHolder(View v) {
            super(v);
            vDrinkName = (TextView)v.findViewById(R.id.drinkName);
            vDrinkPrice = (TextView)v.findViewById(R.id.drinkPrice);
            vDrinkRating = (TextView)v.findViewById(R.id.drinkRating);
            vDrinkPicture = (ImageView)v.findViewById(R.id.drinkPicture);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DrinkAdapter(List<Drink> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DrinkAdapter.DrinkViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drink_cards, parent, false);

        return new DrinkViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DrinkViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.vDrinkName.setText(mDataset.get(position).getNom());
        holder.vDrinkPrice.setText(String.valueOf(mDataset.get(position).getPrice()));
        holder.vDrinkRating.setText(String.valueOf(mDataset.get(position).getRating()));
        if (!mDataset.get(position).getPicture().isEmpty())
            Picasso.with(holder.itemView.getContext()).load(mDataset.get(position).getPicture()).into(holder.vDrinkPicture);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        if (mDataset != null) {
            return mDataset.size();
        }
        else return 0;
    }

    public Drink getItemAtPosition(int position)
    {
        return mDataset.get(position);
    }
}