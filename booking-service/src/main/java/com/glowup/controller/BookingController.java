package com.glowup.controller;

import com.glowup.Service.BookingService;
import com.glowup.domain.BookingStatus;
import com.glowup.dto.*;
import com.glowup.mapper.BookingMapper;
import com.glowup.model.Booking;
import com.glowup.model.SalonReport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingservice;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId, @RequestBody BookingRequest bookingRequest) throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);

        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
        salon.setCloseTime(LocalTime.now().plusHours(12));
        Set<ServiceDTO> serviceDTOSet = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");

        serviceDTOSet.add(serviceDTO);

        Booking booking = bookingservice.createBooking(bookingRequest,
                user,
                salon,
                serviceDTOSet);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer() {
        List<Booking> bookings = bookingservice.getBookingsByCustomer(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon() {
        List<Booking> bookings = bookingservice.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream().map(b -> BookingMapper.toDTO(b)).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsById(@PathVariable Long bookingId) throws Exception {
        Booking booking = bookingservice.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingsByStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) throws Exception {
        Booking booking = bookingservice.updateBooking(bookingId, status);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date) throws Exception {
        List<Booking> bookings = bookingservice.getBookingsByDate(date, salonId);

        List<BookingSlotDTO> slotsDTO = bookings.stream().map(booking -> {
            BookingSlotDTO slotDTO = new BookingSlotDTO();
            slotDTO.setStartTime(booking.getStartTime());
            slotDTO.setEndTime(booking.getEndTime());
            return slotDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(slotsDTO);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport() throws Exception {
        SalonReport report = bookingservice.getSalonReport(1L);
        return ResponseEntity.ok(report);
    }
}
