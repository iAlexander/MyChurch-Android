package com.d2.pcu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.Temple;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class CustomClusterRenderer extends DefaultClusterRenderer<Temple> {

    private final Context context;
    private final IconGenerator iconGenerator;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<Temple> clusterManager) {
        super(context, map, clusterManager);

        this.context = context;
        iconGenerator = new IconGenerator(context.getApplicationContext());
    }

    @Override
    protected void onBeforeClusterItemRendered(Temple item, MarkerOptions markerOptions) {
        final BitmapDescriptor markerDescriptor =
                BitmapDescriptorFactory.fromResource(R.mipmap.map_pin_image);

        markerOptions.icon(markerDescriptor).snippet(item.getName());
    }


    @Override
    protected void onBeforeClusterRendered(Cluster<Temple> cluster, MarkerOptions markerOptions) {
        iconGenerator.setBackground(ContextCompat.getDrawable(context, R.drawable.map_pin_group));

        iconGenerator.setTextAppearance(R.style.TitleAppText);

        final Bitmap icon = iconGenerator.makeIcon(String.valueOf(cluster.getSize()));

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<Temple> cluster) {
        return cluster.getSize() > 1;
    }
}
