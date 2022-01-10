package com.example.proiectfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    public List<Food> food;

    public LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RecyclerAdapter(Context context, List<Food> food) {
        this.food = food;
        this.mInflater = LayoutInflater.from(context);
    }


    //Pentru a stoca informatii despre vizualizarea unui singur element din lista
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameFood;
        ImageView imageFood;

        public ViewHolder(View itemView) {
            super(itemView);
            nameFood = (TextView) itemView.findViewById(R.id.idTitle);
            imageFood = (ImageView) itemView.findViewById(R.id.imageFood);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.nameFood.setText(food.get(position).getTitle());
        Picasso.with(mInflater.getContext()).load(food.get(position).getImg()).into(holder.imageFood);

    }

    @Override
    public int getItemCount() {
        return food == null ? 0 : food.size();
    }

    String getItem(int id) {
        return food.get(id).getTitle();
    }

    //metoda pentru a memora item-ul pe care se face click-ul
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    //interfata pentru a implementa metoda onItemClick-pentru a raspunde la evenimentele de click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
