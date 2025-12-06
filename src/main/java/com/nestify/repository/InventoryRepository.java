package com.nestify.repository;

import com.nestify.entity.Hotel;
import com.nestify.entity.Inventory;
import com.nestify.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByRoom(Room room);

    @Query("""
         SELECT DISTINCT i.hotel FROM Inventory i
         WHERE i.city = :city AND
               (i.date BETWEEN :startDate AND :endDate) AND
               i.closed = false AND
               (i.totalCount - i.bookedCount - i.reservedCount) >= :roomCount
         GROUP BY i.hotel, i.room
         HAVING COUNT(i.date) >= :dayCount
         """)
    Page<Hotel> searchHotels(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCount") Integer roomCount,
            @Param("dayCount") Long dayCount,
            Pageable pageable);


    @Query("""
          SELECT i FROM Inventory i
          WHERE i.room.id = :roomId AND
            (i.date BETWEEN :checkInDate AND :checkOutDate) AND
            i.closed = false AND
            (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
          """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAndLockInventory(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Integer roomsCount);

    @Query("""
          SELECT i FROM Inventory i
          WHERE i.room.id = :roomId AND
            (i.date BETWEEN :checkInDate AND :checkOutDate) AND
            AND (i.totalCount - i.bookedCount) >= :roomsCount AND
            i.closed = false
          """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void findAndLockReservedInventory(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Integer roomsCount);

    @Modifying
    @Query("""
                UPDATE Inventory i
                SET i.reservedCount = i.reservedCount - :numberOfRooms,
                    i.bookedCount = i.bookedCount + :numberOfRooms
                WHERE i.room.id = :roomId
                  AND i.date BETWEEN :startDate AND :endDate
                  AND (i.totalCount - i.bookedCount) >= :numberOfRooms
                  AND i.reservedCount >= :numberOfRooms
                  AND i.closed = false
            """)
    void confirmBooking(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("numberOfRooms") int numberOfRooms);

    @Modifying
    @Query("""
                UPDATE Inventory i
                SET i.bookedCount = i.bookedCount - :numberOfRooms
                WHERE i.room.id = :roomId
                  AND i.date BETWEEN :checkInDate AND :checkOutDate
                  AND i.bookedCount >= :numberOfRooms
                  AND i.closed = false
            """)
    void cancelBooking(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Integer roomsCount);
}
