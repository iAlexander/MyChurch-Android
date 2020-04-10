package com.d2.pcu.login.sign_up.adapters_and_viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.databinding.ViewItemSearchedTempleShortBinding;

import java.util.ArrayList;
import java.util.List;

public class TemplesDialogAdapter extends RecyclerView.Adapter<ProfileTemplesDialogItemViewHolder> {

    private List<BaseTemple> temples;

    private OnDialogItemClickListener onDialogItemClickListener;

    public TemplesDialogAdapter(OnDialogItemClickListener onDialogItemClickListener) {
        temples = new ArrayList<>();
        this.onDialogItemClickListener = onDialogItemClickListener;

        setHasStableIds(true);
    }

    public void setTemples(List<BaseTemple> temples) {
        this.temples.clear();
        this.temples.addAll(temples);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfileTemplesDialogItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewItemSearchedTempleShortBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_item_searched_temple_short,
                parent,
                false
        );

        return new ProfileTemplesDialogItemViewHolder(binding, onDialogItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileTemplesDialogItemViewHolder holder, int position) {
        holder.bind(temples.get(position));
    }

    @Override
    public int getItemCount() {
        return temples.size();
    }

    @Override
    public long getItemId(int position) {
        return temples.get(position).getId();
    }
}
