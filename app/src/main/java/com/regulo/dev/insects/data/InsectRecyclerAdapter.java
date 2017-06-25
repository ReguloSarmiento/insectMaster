package com.regulo.dev.insects.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.regulo.dev.insects.R;
import com.regulo.dev.insects.views.DangerLevelView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.regulo.dev.insects.data.InsectContract.Bugs.INDEX_COLUMN_DANGER_LEVEL;
import static com.regulo.dev.insects.data.InsectContract.Bugs.INDEX_COLUMN_FRIENDLY_NAME;
import static com.regulo.dev.insects.data.InsectContract.Bugs.INDEX_COLUMN_SCIENTIFIC_NAME;

/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class InsectRecyclerAdapter extends
        RecyclerView.Adapter<InsectRecyclerAdapter.InsectHolder> {

    /* ViewHolder for each insect item */
    public class InsectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final DangerLevelView mDangerLevel;
        public final TextView mCommonName;
        public final TextView mScientificName;

        public InsectHolder(View itemView) {
            super(itemView);
            mDangerLevel = (DangerLevelView) itemView.findViewById(R.id.dangerLevel);
            mCommonName = (TextView) itemView.findViewById(R.id.commonName);
            mScientificName = (TextView) itemView.findViewById(R.id.scientificName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(getItem(getAdapterPosition()));
        }
    }

    private Cursor mCursor = null;
    private  LayoutInflater mInflater;
    private  RecyclerOnItemClickListener mOnItemClickListener;
    public List<Insect> mInsectsList = new ArrayList<>();

    public InsectRecyclerAdapter(Context context, RecyclerOnItemClickListener mOnItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public InsectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.insect_list_item, parent, false);
        return new InsectHolder(view);
    }


    @Override
    public void onBindViewHolder(InsectHolder holder, int position) {
        if (mCursor != null) {
           if(mCursor.moveToPosition(position)) {
               int level = mCursor.getInt(INDEX_COLUMN_DANGER_LEVEL);
               holder.mCommonName.setText(mCursor.getString(INDEX_COLUMN_FRIENDLY_NAME));
               holder.mScientificName.setText(mCursor.getString(INDEX_COLUMN_SCIENTIFIC_NAME));
               holder.mDangerLevel.setDangerLevel(level);
               holder.mDangerLevel.setDangerLevelColor(holder);
           }
            createInsectList(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return -1;
        }
    }

    /**
     * Return the {@link Insect} represented by this item in the adapter.
     *
     * @param position Adapter item position.
     *
     * @return A new {@link Insect} filled with this position's attributes
     *
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public Insect getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (mCursor.moveToPosition(position)) {
            return new Insect(mCursor);
        }
        return null;
    }

    public void createInsectList(int position) {
        for (int i = 0; i <= getItemCount(); i++) {
            if(mCursor.moveToPosition(i)) {
                mInsectsList.add(new Insect(mCursor));
            }
        }
    }

    public List<Insect> getInsectsList(){
        Collections.shuffle(mInsectsList);
        return mInsectsList;
    }

    public interface RecyclerOnItemClickListener {
        /**
         * Called when an item is clicked.
         * @param insect  Position of the insect that was clicked.
         */
        void onItemClick(Insect insect);
    }
}
