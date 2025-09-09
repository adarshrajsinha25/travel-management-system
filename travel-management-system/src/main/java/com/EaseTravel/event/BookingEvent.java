package com.EaseTravel.event;

import org.springframework.context.ApplicationEvent;

public class BookingEvent extends ApplicationEvent {
    private final String bookingId;

    public BookingEvent(Object source, String bookingId) {
        super(source);
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }
}
