package com.d2.pcu.fragments.map;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.databinding.ItemMapTempleBinding;
import com.d2.pcu.utils.Locator;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collections;
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

    public void updateDistance(LatLng location) {
        if (CollectionUtils.isEmpty(temples) || location == null) return;
        ArrayList<BaseTemple> temp = new ArrayList<>(temples);
        for (BaseTemple temple : temp) {
            temple.setDistance(SphericalUtil.computeDistanceBetween(location, temple.getLatLng()) / 1000);
        }

        Collections.sort(
                temp,
                (o1, o2) -> Double.compare(o1.getDistance(), o2.getDistance())
        );
        setTemples(temp);
    }

    public LatLngBounds getNearest(LatLng current) {
        if (CollectionUtils.isEmpty(temples)) {
            if (current == null)
                return new LatLngBounds(Locator.DEFAULT_KYIV, Locator.DEFAULT_KYIV);
            else return new LatLngBounds(current, current);
        } else {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(temples.get(0).getLatLng());

            if (current != null) {
               builder.include(current);
            }

            if (temples.size() > 1) {
                for (int i = 1; i < temples.size() && temples.get(i).getDistance() < 3; i++) {
                    builder.include(temples.get(i).getLatLng());
                }
            }

            return builder.build();
        }
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
