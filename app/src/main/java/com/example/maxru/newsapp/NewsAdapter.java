package com.example.maxru.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxru.newsapp.data.Contract;
import com.example.maxru.newsapp.data.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by maxru on 7/2/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {
    private ItemClickListener listener;
    private Cursor cursor;
    private Context context;

    public NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.listener = listener;
        this.cursor = cursor;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView date;
        ImageView image;


        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            date = (TextView)view.findViewById(R.id.date);
            image = (ImageView)view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            cursor.moveToPosition(pos);
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION)));
            date.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_DATE)));
            Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE))).into(image);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);

        }
    }

}
