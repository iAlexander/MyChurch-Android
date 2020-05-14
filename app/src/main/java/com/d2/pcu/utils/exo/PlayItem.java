package com.d2.pcu.utils.exo;

public final class PlayItem {
    private boolean isMorning;
    private int position;

    public PlayItem(boolean isMorning, int position) {
        this.isMorning = isMorning;
        this.position = position;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public int getPosition() {
        return position;
    }
}
