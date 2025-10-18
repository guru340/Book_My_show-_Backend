package com.cfs.BookMyShow.Service;


import com.cfs.BookMyShow.Repostitry.BookingRepository;
import com.cfs.BookMyShow.Repostitry.ShowRepository;
import com.cfs.BookMyShow.Repostitry.ShowseatRepository;
import com.cfs.BookMyShow.Repostitry.UserRepository;
import com.cfs.BookMyShow.dto.*;
import com.cfs.BookMyShow.exception.ResourceNotFound;
import com.cfs.BookMyShow.exception.SeatUnavailableException;
import com.cfs.BookMyShow.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private UserRepository UserRepo;

    @Autowired
    private ShowRepository showRepo;

    @Autowired
    private ShowseatRepository showseatRepo;

    @Autowired
    private BookingRepository bookingRepository;

//    ACID Property ko consit kr deti hai
    @Transactional
    public BookingDto createBooking(BookingRequestDto bookingRequest)
    {
        User user=  UserRepo.findById(bookingRequest.getUserId())
                .orElseThrow(()->new ResourceNotFound("User Not Found"));

        Show show =  showRepo .findById(bookingRequest.getShowId())
                .orElseThrow(()->new ResourceNotFound ("Show Not Found"));

        List<Showseat> selectedSeats=showseatRepo.findAllById(bookingRequest.getSeatIds());

        for (Showseat seat:selectedSeats){
            if (!"AVAILABLE".equals(seat.getStatus())){
                    throw new SeatUnavailableException("Seat"+seat.getSeat().getSeatnumber()+" is not available");
            }
            seat.setStatus("LOCKED");


        }
        showseatRepo.saveAll(selectedSeats);

        Double totalAmount= selectedSeats.stream().mapToDouble(Showseat::getPrice).sum();

//        payement
        Payement payement=new Payement();
        payement.setAmount(totalAmount);
        payement.setPaymentTime(LocalDateTime.now());
        payement.setPaymentMethod(bookingRequest.getPayementmethod());
        payement.setStatus("SUCCESS");
        payement.setTransctionId(UUID.randomUUID().toString());

//        Booking
        Booking booking=new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingtime(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        booking.setTotalamount(totalAmount);
        booking.setBookingNumber(UUID.randomUUID().toString());
        booking.setPayement(payement);

        Booking saveBooking= bookingRepository.save(booking);

        selectedSeats.forEach(seat->
        {
            seat.setStatus("BOOKED");
            seat.setBooking(saveBooking);
        });
        showseatRepo.saveAll(selectedSeats);
        return mapToBookingDto(saveBooking,selectedSeats);
    }
    public BookingDto getBookingById(Long id)
    {
        Booking booking=bookingRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Booking Not Found"));
        List<Showseat> seats=showseatRepo.findAll()
                .stream().
                filter(seat->seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());
        return mapToBookingDto(booking,seats);
    }
    public BookingDto getBookingByNumber(String BookingNumber){

        Booking booking=bookingRepository. findByBookingNumber(BookingNumber)
                .orElseThrow(()->new ResourceNotFound("Booking Not Found"));
        List<Showseat> seats=showseatRepo.findAll()
                .stream().
                filter(seat->seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());
        return mapToBookingDto(booking,seats);

    }

    private List<BookingDto> getBookingByUserId(Long userId)
    {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(booking -> {
                    List<Showseat> seats=showseatRepo.findAll()
                            .stream().
                            filter(seat->seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId()))
                            .collect(Collectors.toList());
                    return mapToBookingDto(booking,seats);
                })
                .collect(Collectors.toList());

    }

    public BookingDto cancelBooking(Long Id){
        Booking booking=bookingRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFound("Booking Not found"));

        booking.setStatus("CANCELLED");

        List<Showseat> seats=showseatRepo.findAll()
                .stream()
                .filter(seat->seat.getBooking()!=null && seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());

        seats.forEach(seat->{
            seat.setStatus("AVAILABLE");
            seat.setBooking(null);
        });
        if (booking.getPayement()!=null){
            booking.getPayement().setStatus("REFUNDED");


        }
        Booking UpdateBooking =bookingRepository.save(booking);
        showseatRepo.saveAll(seats);

        return mapToBookingDto(UpdateBooking,seats);
    }

    private BookingDto mapToBookingDto(Booking booking,List<Showseat> seats){
        BookingDto bookingDto=new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStatus(bookingDto.getStatus());
        bookingDto.setBookingTime(booking.getBookingtime());
        bookingDto.setBookingNumber(booking.getBookingNumber());
        bookingDto.setTotalAmount(booking.getTotalamount());


        UserDto userDto=new UserDto();
        userDto.setId(booking.getUser().getId());
        userDto.setName(booking.getUser().getName());
        userDto.setEmail(booking.getUser().getEmail());
        userDto.setPhoneNumber(booking.getUser().getPhoneNumber());
        bookingDto.setUser(userDto);

        ShowDto showDto=new ShowDto();
        showDto.setId(booking.getShow().getId());
        showDto.setStartTime(booking.getShow().getStartTime());
        showDto.setEndTime(booking.getShow().getEndTime());

        MovieDto movieDto = new MovieDto();
        movieDto.setId(booking.getShow().getMovie().getId());
        movieDto.setTitle(booking.getShow().getMovie().getTitle());
        movieDto.setDescription(booking.getShow().getMovie().getDescription());
        movieDto.setLanguage(booking.getShow().getMovie().getLanguage());
        movieDto.setGenre(booking.getShow().getMovie().getGenre());
        movieDto.setDurationMins((booking.getShow().getMovie().getDurationMin()));
        movieDto.setReleaseDate(booking.getShow().getMovie().getReleaseDAte());
        movieDto.setPosterUrl(booking.getShow().getMovie().getPosterUrl());
        showDto.setMovie(movieDto);


        ScreenDto screenDto=new ScreenDto();
        screenDto.setId(booking.getShow().getScreen().getId());
        screenDto.setName(booking.getShow().getScreen().getName());
        screenDto.setTotalseats(booking.getShow().getScreen().getTotalseats());


        TheaterDto theaterDto=new TheaterDto();
        theaterDto.setId(bookingDto.getShow().getScreen().getTheater().getId());
        theaterDto.setName(bookingDto.getShow().getScreen().getTheater().getName());
        theaterDto.setAddress(bookingDto.getShow().getScreen().getTheater().getAddress());
        theaterDto.setCity(bookingDto.getShow().getScreen().getTheater().getCity());
        theaterDto.setTotalScreen(bookingDto.getShow().getScreen().getTheater().getTotalScreen());
        screenDto.setTheater(theaterDto);
        showDto.setScreen(screenDto);
        bookingDto.setShow(showDto);
        List<ShowseatDto> seatDtos=seats.stream()
                .map(seat->{
                    ShowseatDto seatDto=new ShowseatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setStatus(seat.getStatus());
                    seatDto.setPrice(seat.getPrice());

                    Seatdto baseSeatDto=new Seatdto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatnumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBaseprice());
                    seatDto.setSeat(baseSeatDto);
                    return seatDto;
                })
                .collect(Collectors.toList());
        bookingDto.setSeats(seatDtos);


        if(booking.getPayement()!=null)
        {
            Payementdto paymentDto=new Payementdto();
            paymentDto.setId(booking.getPayement().getId());
            paymentDto.setAmount(booking.getPayement().getAmount());
            paymentDto.setPaymentMethod(booking.getPayement().getPaymentMethod());
            paymentDto.setPaymentTime(booking.getPayement().getPaymentTime());
            paymentDto.setStatus(booking.getPayement().getStatus());
            paymentDto.setTransctionId(booking.getPayement().getTransctionId());
            bookingDto.setPayement(paymentDto);
        }



        return bookingDto;
    }
    }

