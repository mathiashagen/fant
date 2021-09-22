package com.example.oblig1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig1.data.model.ItemForSale;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

public class ItemsForSaleAdapter extends RecyclerView.Adapter<ItemsForSaleAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView priceTextView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.itemForSale_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.itemForSale_description);
            priceTextView = (TextView) itemView.findViewById(R.id.itemForSale_price);
            imageView = (ImageView) itemView.findViewById(R.id.itemForSale_imageView);
        }
    }
    private List<ItemForSale> itemForSaleList;

    public ItemsForSaleAdapter(List<ItemForSale> itemForSaleList) {
        this.itemForSaleList = itemForSaleList;
    }

    @Override
    public ItemsForSaleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemForSaleView = inflater.inflate(R.layout.item_itemforsale, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemForSaleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemsForSaleAdapter.ViewHolder holder, int position) {
        ItemForSale itemForSale = itemForSaleList.get(position);

        TextView titleTextView = holder.titleTextView;
        titleTextView.setText(itemForSale.getTitle());
        TextView descriptionTextView = holder.descriptionTextView;
        descriptionTextView.setText(itemForSale.getDescription());
        TextView priceTextView = holder.priceTextView;
        priceTextView.setText(itemForSale.getPrice());
        ImageView imageView = holder.imageView;
        try {
            Picasso.get().load(itemForSale.getFirstPhotoURL()).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return itemForSaleList.size();
    }
}
