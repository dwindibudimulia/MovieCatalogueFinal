package com.example.dwindibudimulia.moviecatalogue.itemClick;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dwindibudimulia.moviecatalogue.R;

public class ItemClickSupport {
    private final RecyclerView RecyclerView;
    private OnItemClickListener OnItemClickListener;
    private OnItemLongClickListener OnItemLongClickListener;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (OnItemClickListener != null) {
                RecyclerView.ViewHolder holder = RecyclerView.getChildViewHolder(v);
                OnItemClickListener.onItemClicked(RecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private final View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (OnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = RecyclerView.getChildViewHolder(v);
                OnItemLongClickListener.onItemLongClicked(RecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private final RecyclerView.OnChildAttachStateChangeListener attachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (OnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (OnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };

    private ItemClickSupport(RecyclerView recyclerView) {
        RecyclerView = recyclerView;
        RecyclerView.setTag(R.id.item_click_support, this);
        RecyclerView.addOnChildAttachStateChangeListener(attachStateChangeListener);
    }

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(attachStateChangeListener);
        view.setTag(R.id.item_click_support);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        OnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        OnItemLongClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
