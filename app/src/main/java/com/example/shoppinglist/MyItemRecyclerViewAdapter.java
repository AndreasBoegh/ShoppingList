package com.example.shoppinglist;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shoppinglist.ItemFragment.OnListFragmentInteractionListener;
import com.example.shoppinglist.dummy.DummyContent;
import com.example.shoppinglist.dummy.DummyContent.DummyItem;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private int highligtedItemPosition = -1;

    public MyItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setHighlight(DummyItem position) {
        if (position instanceof DummyItem && position != null) {
            int currentPos = mValues.indexOf(position);
            if (currentPos == highligtedItemPosition) {
                this.highligtedItemPosition = -1;
            } else {
                this.highligtedItemPosition = currentPos;
            }
        }else{
            this.highligtedItemPosition = -1;
        }
        this.notifyDataSetChanged();

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(Integer.toString(mValues.get(position).id));
        holder.mContentView.setText(mValues.get(position).content);
        holder.mAmountView.setText(Integer.toString(mValues.get(position).amount));

        if(position == highligtedItemPosition ){
            holder.itemView.setBackgroundColor(Color.parseColor("#87CEEB"));
        }
        else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //holder.itemView.setBackgroundColor(Color.parseColor("#80000000"));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mAmountView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mAmountView = (TextView) view.findViewById(R.id.amount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
