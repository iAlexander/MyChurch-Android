package com.d2.pcu.fragments.map.temple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.ViewTempleContactBinding;
import com.d2.pcu.databinding.ViewTempleHistoryAndDescriptionBinding;
import com.d2.pcu.fragments.map.temple.temple_views.TempleContactsFragment;
import com.d2.pcu.fragments.map.temple.temple_views.TempleHistoryAndDescriptionFragment;
import com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders.TempleContactsViewHolder;
import com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders.TempleHistoryAndDescriptionViewHolder;
import com.d2.pcu.fragments.map.temple.temple_views.temple_viewholders.TempleViewHolder;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;

import java.util.Arrays;
import java.util.List;

public class TempleViewPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CONTACTS = 0;
    private static final int HISTORY_DESCRIPTION = 1;

    private Temple temple;
    private Context context;
    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;

    private List<Fragment> fragments = Arrays.asList(
            TempleContactsFragment.newInstance(), TempleHistoryAndDescriptionFragment.newInstance()
    );

    TempleViewPagerAdapter(Context context,
                           Temple temple, OnAdditionalFuncMapListener onAdditionalFuncMapListener) {
        this.context = context;
        this.temple = temple;
        this.onAdditionalFuncMapListener = onAdditionalFuncMapListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (fragments.get(position) instanceof TempleContactsFragment) {
            return CONTACTS;
        }

        return HISTORY_DESCRIPTION;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case CONTACTS : {

                ViewTempleContactBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_temple_contact, parent, false);

                holder = new TempleContactsViewHolder(context, binding, onAdditionalFuncMapListener);
                break;
            }
            case HISTORY_DESCRIPTION : {
                ViewTempleHistoryAndDescriptionBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_temple_history_and_description, parent, false);

                holder = new TempleHistoryAndDescriptionViewHolder(context, binding);
                break;
            }
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TempleViewHolder)holder).bind(temple);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
