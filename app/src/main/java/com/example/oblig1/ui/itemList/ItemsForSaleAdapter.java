package com.example.oblig1.ui.itemList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig1.ui.itemDetail.ItemDetailActivity;
import com.example.oblig1.R;
import com.example.oblig1.data.model.ItemForSale;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsForSaleAdapter extends RecyclerView.Adapter<ItemsForSaleAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView priceTextView;
        public ImageView imageView;
        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            titleTextView = (TextView) itemView.findViewById(R.id.itemForSale_title);
            priceTextView = (TextView) itemView.findViewById(R.id.itemForSale_price);
            imageView = (ImageView) itemView.findViewById(R.id.itemForSale_imageView);
        }
    }
    private List<ItemForSale> itemForSaleList;
    private Context ctx;

    public ItemsForSaleAdapter(List<ItemForSale> itemForSaleList, Context ctx) {
        this.itemForSaleList = itemForSaleList;
        this.ctx = ctx;
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
        TextView priceTextView = holder.priceTextView;
        priceTextView.setText(itemForSale.getPrice());
        ImageView imageView = holder.imageView;

        Picasso.get().load(itemForSale.getFirstPhotoURL()).into(imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ItemDetailActivity.class);
                intent.putExtra("itemForSale", itemForSale);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return itemForSaleList.size();
    }
}
