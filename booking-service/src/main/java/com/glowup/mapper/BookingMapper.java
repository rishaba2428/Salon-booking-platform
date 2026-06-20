package com.glowup.mapper;

import com.glowup.dto.BookingDTO;
import com.glowup.model.Booking;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking)
    {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setServiceIds(booking.getServiceIds());

        return bookingDTO;
    }
}
