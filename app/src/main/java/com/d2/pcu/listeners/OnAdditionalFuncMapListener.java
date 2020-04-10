package com.d2.pcu.listeners;

import com.google.android.gms.maps.model.LatLng;

public interface OnAdditionalFuncMapListener {

    void getDirectionsOnMap(LatLng end);

    void onTempleInfoClick(String serializedTemple, int type);
}
