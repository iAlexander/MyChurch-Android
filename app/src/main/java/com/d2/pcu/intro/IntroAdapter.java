package com.d2.pcu.intro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.databinding.ViewIntroItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroItemViewHolder> {

    private List<String> titles;
    private List<Drawable> imageDrawable;
    private List<Integer> fragmentId;
    private List<Boolean> defaultEnabledScreen;

    private OnSelectedStartScreenListener onSelectedStartScreenListener;

    private Context context;

    IntroAdapter(Context context, OnSelectedStartScreenListener onSelectedStartScreenListener) {
        this.context = context;

        titles = new ArrayList<>();
        imageDrawable = new ArrayList<>();
        fragmentId = new ArrayList<>();
        defaultEnabledScreen = new ArrayList<>(Collections.nCopies(5, false));

        titles.addAll(Arrays.asList(context.getResources().getStringArray(R.array.intro_titles)));
        collectDrawable(context.getResources().obtainTypedArray(R.array.intro_icons));
        collectFragments(context.getResources().obtainTypedArray(R.array.fragments_ids));

        this.onSelectedStartScreenListener = onSelectedStartScreenListener;

        defaultSettings();
    }

    @NonNull
    @Override
    public IntroItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewIntroItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_intro_item, parent, false);

        return new IntroItemViewHolder(binding, position -> {
            for (int i = 0; i < defaultEnabledScreen.size(); i++) {
                if (defaultEnabledScreen.get(i)) {
                    defaultEnabledScreen.set(i, false);
                    notifyItemChanged(i);
                }
            }

            defaultEnabledScreen.set(position, true);
            if (onSelectedStartScreenListener != null) {
                onSelectedStartScreenListener.onSelectItem(fragmentId.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull IntroItemViewHolder holder, int position) {
        holder.bind(
                titles.get(position),
                imageDrawable.get(position),
                defaultEnabledScreen.get(position),
                position
        );
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    private void collectDrawable(TypedArray ids) {
        for (int i = 0; i < ids.length(); i++) {
            int id = ids.getResourceId(i, -1);
            if (id != -1) {
                imageDrawable.add(context.getDrawable(id));
            }
        }
    }

    private void collectFragments(TypedArray fragmentsIds) {
        for (int i = 0; i < fragmentsIds.length(); i++) {
            int id = fragmentsIds.getResourceId(i, -1);
            if (id != -1) {
                fragmentId.add(id);
            }
        }
    }

    private void defaultSettings() {
        defaultEnabledScreen.set(0, true);
        if (onSelectedStartScreenListener != null) {
            onSelectedStartScreenListener.onSelectItem(fragmentId.get(0));
        }
    }
}
