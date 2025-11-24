package com.nestify.repository;

import com.nestify.entity.Hotel;
import com.nestify.entity.Inventory;
import com.nestify.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

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
}
