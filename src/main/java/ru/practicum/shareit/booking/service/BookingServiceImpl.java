package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReceivedBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final ModelMapper mapper;
    private static final String USER = "USER";

    @Override
    @Transactional(readOnly = true)
    public BookingDto getBooking(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ObjectNotFoundException("Не найдено: " + bookingId));
        if (booking.getBooker().getId() == userId || booking.getItem().getOwner() == userId) {
            return convertBookingToDto(booking);
        } else {
            throw new InappropriateUser("Не может совершить действие пользователь с id: " + userId);
        }
    }

    @Transactional(readOnly = true)
    public List<BookingDto> getAllUserBookings(long userId, String state, String userType) {
        if (Arrays.stream(BookingState.values()).noneMatch(enumState -> enumState.name().equals(state))) {
            throw new UnsupportedStatus("Unknown state");
        }
        userService.isExistUser(userId);
        List<Booking> userBookings = userType.equals(USER)
                ? bookingRepository.findAllUserBookingsByState(userId, state)
                : bookingRepository.findAllOwnerBookingsByState(userId, state);
        return convertListBookingToDto(userBookings);
    }

    @Transactional
    public BookingDto createBooking(ReceivedBookingDto bookingDto, long userId) {
        isValidBookingTimeRequest(bookingDto);
        Item item = itemService.getItem(bookingDto.getItemId());
        isValidBookingItemRequest(item, userId);
        Booking booking = mapper.map(bookingDto, Booking.class);
        booking.setItem(item);
        booking.setBooker(userService.getUserById(userId));
        return convertBookingToDto(bookingRepository.save(booking));
    }

    @Transactional
    public BookingDto updateBookingStatus(long bookingId, String approved, long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ObjectNotFoundException("Бронирование не найдено"));
        isValidUpdateBookingStatusRequest(booking, userId, bookingId);
        if (approved.equals("true")) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus((BookingStatus.REJECTED));
        }
        return convertBookingToDto(bookingRepository.save(booking));
    }

    private void isValidBookingTimeRequest(ReceivedBookingDto bookingDto) {
        if (bookingDto.getStart() == null ||
                bookingDto.getEnd() == null ||
                (LocalDateTime.now().isAfter(bookingDto.getStart())) ||
                bookingDto.getEnd().isBefore(LocalDateTime.now()) ||
                bookingDto.getEnd().isBefore(bookingDto.getStart()) ||
                bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new BadRequestException("Not valid");
        }
    }

    private void isValidBookingItemRequest(Item item, long userId) {
        if (item.getAvailable().equals(false)) {
            throw new ItemIsUnavailable("Предмет с id" + item.getId() + "недоступен");
        }
        if (item.getOwner() == userId) {
            throw new InappropriateUser("Владелец не может забронировать свой предмет");
        }
    }

    private void isValidUpdateBookingStatusRequest(Booking booking, long userId, long bookingId) {
        if (booking.getItem().getOwner() != userId) {
            throw new InappropriateUser("Пользователь не может совершить действие. Id пользователя:" + userId);
        }
        if (!booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new BookingStatusAlreadySet("Booking status already set: " + bookingId);
        }
    }

    private BookingDto convertBookingToDto(Booking booking) {
        return mapper.map(booking, BookingDto.class);
    }

    private List<BookingDto> convertListBookingToDto(List<Booking> bookings) {
        return bookings.stream()
                .map(this::convertBookingToDto)
                .collect(Collectors.toList());
    }
}