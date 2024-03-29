package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwner(long ownerId);

    @Query(value = "SELECT * FROM items WHERE name ILIKE CONCAT('%',?1,'%') OR description ILIKE CONCAT('%',?1,'%') AND " +
            "is_available = TRUE", nativeQuery = true)

    List<Item> searchItem(String text);
}