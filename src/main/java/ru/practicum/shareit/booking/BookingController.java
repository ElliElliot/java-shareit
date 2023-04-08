package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReceivedBookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@PathVariable long bookingId,
                                 @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping()
    public List<BookingDto> getAllUserBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        return bookingService.getAllUserBookings(userId, state, "USER");
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllOwnerBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                                    @RequestParam(name = "state", required = false, defaultValue = "ALL") String state) {
        return bookingService.getAllUserBookings(userId, state, "OWNER");
    }

    @PostMapping()
    public BookingDto createBooking(@RequestBody ReceivedBookingDto bookingDto,
                                        @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateBookingStatus(@PathVariable long bookingId,
                                              @RequestParam(name = "approved") String approved,
                                              @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.updateBookingStatus(bookingId, approved.toLowerCase(), userId);
    }
}