package com.glowup.Service;

import com.glowup.domain.BookingStatus;
import com.glowup.dto.BookingRequest;
import com.glowup.dto.SalonDTO;
import com.glowup.dto.ServiceDTO;
import com.glowup.dto.UserDTO;
import com.glowup.model.Booking;
import com.glowup.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO ser,
                          SalonDTO salon,
                          Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);
}
