package com.nestify.service;

import com.nestify.entity.Room;

public interface InventoryService {

    void initializeInventoryForAYear(Room room);

    void deleteAllInventories(Room room);

}
