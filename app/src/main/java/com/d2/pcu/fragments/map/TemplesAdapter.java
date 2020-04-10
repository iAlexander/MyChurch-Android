package com.d2.pcu.fragments.map;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.databinding.ItemMapTempleBinding;

import java.util.ArrayList;
import java.util.List;

public class TemplesAdapter extends RecyclerView.Adapter<TempleItemViewHolder> {

    private ArrayList<BaseTemple> temples = new ArrayList<>();

    private OnTempleClickListener onTempleClickListener;
    private OnGetRouteClickListener onGetRouteClickListener;

    private RecyclerView recyclerView;

    void setOnTempleClickListener(OnTempleClickListener listener) {
        this.onTempleClickListener = listener;
    }

    void setOnGetRouteClickListener(OnGetRouteClickListener listener) {
        this.onGetRouteClickListener = listener;
    }

//    TemplesAdapter(OnTempleClickListener onTempleClickListener,
//                   OnGetRouteClickListener onGetRouteClickListener) {
//        this.onTempleClickListener = onTempleClickListener;
//        this.onGetRouteClickListener = onGetRouteClickListener;
//
//    }

    void setTemples(List<BaseTemple> temples) {
        this.temples.clear();
        this.temples.addAll(temples);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TempleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ItemMapTempleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_map_temple, parent, false);

        return new TempleItemViewHolder(binding, onTempleClickListener, onGetRouteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TempleItemViewHolder holder, int position) {
        holder.bind(temples.get(position));
    }

    @Override
    public int getItemCount() {
        return temples == null ? 0 : temples.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    void scrollToItem(BaseTemple baseTemple) {

        for (int i = 0; i < temples.size(); i++) {
            if (baseTemple.equals(temples.get(i))) {
                recyclerView.scrollToPosition(i);
                break;
            }
        }
    }

    BaseTemple onItemScroll(int position) {
        return temples.get(position);
    }
}
