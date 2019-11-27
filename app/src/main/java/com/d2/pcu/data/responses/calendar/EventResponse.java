package com.d2.pcu.data.responses.calendar;

import com.d2.pcu.data.model.calendar.Event;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

public class EventResponse extends OnMasterResponse {

    @SerializedName("data") private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
