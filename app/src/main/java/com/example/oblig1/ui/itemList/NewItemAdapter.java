package com.example.oblig1.ui.itemList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig1.R;

import java.util.List;

public class NewItemAdapter extends RecyclerView.Adapter<NewItemAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.imageView_photoitem);
        }
    }

    private List<Bitmap> photoList;

    public NewItemAdapter(List<Bitmap> photoList){
        this.photoList = photoList;
    }

    @Override
    public NewItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View photoView = inflater.inflate(R.layout.item_photo, parent, false);

        ViewHolder viewHolder = new ViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewItemAdapter.ViewHolder holder, int position) {
        Bitmap photo = photoList.get(position);

        ImageView imageView = holder.imageView;
        imageView.setImageBitmap(photo);
    }

    @Override
    public int getItemCount(){
        return photoList.size();
    }
}
