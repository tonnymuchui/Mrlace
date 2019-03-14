package com.mrlace.mrlace.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrlace.mrlace.Interface.ItemClickListner;
import com.mrlace.mrlace.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProduct_name, txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public ItemClickListner itemClickListner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProduct_name = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListner(ItemClickListner listner){

        this.itemClickListner = listner;
    }

    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v, getAdapterPosition(),false);
    }
}
