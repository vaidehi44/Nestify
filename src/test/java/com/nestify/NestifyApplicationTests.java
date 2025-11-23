package com.nestify;

import com.nestify.entity.Hotel;
import com.nestify.entity.Room;
import com.nestify.entity.User;
import com.nestify.entity.enums.Role;
import com.nestify.repository.HotelRepository;
import com.nestify.repository.RoomRepository;
import com.nestify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class NestifyApplicationTests {

//	@Test
//	void contextLoads() {
//	}

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void createHotel() {
        String[] photos = {"url1", "url2", "url3"};

        Hotel hotel = new Hotel();
        hotel.setName("Hotel 3");
        hotel.setCity("Pune");
        hotel.setPhotos(photos);
        hotel.setIsActive(true);

        hotelRepository.save(hotel);
    }

    @Test
    void createRoom() {

        Hotel hotel = hotelRepository.findById(4L).get();

        Room room = Room.builder()
                .hotel(hotel)
                .type("Economy Double")
                .basePrice(BigDecimal.valueOf(799))
                .amenities(new String[]{"Double Bed", "Hot Water", "TV", "WiFi"})
                .totalCount(10)
                .capacity(2)
                .build();

        roomRepository.save(room);
    }

    @Test
    void createUser() {
        User user = User.builder()
                .email("TEST5@gmail.com")
                .password("125@EFG")
                .firstName("Josh17")
                .roles(Set.of(Role.GUEST))
                .build();
        userRepository.save(user);
    }

    @Test
    void getAdmin() {

    }
}
