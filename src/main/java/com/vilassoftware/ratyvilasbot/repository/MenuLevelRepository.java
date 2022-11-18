package com.vilassoftware.ratyvilasbot.repository;

import com.vilassoftware.ratyvilasbot.model.Button;
import com.vilassoftware.ratyvilasbot.model.MenuLevel;
import com.vilassoftware.ratyvilasbot.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuLevelRepository extends CrudRepository<MenuLevel, Long> {
    @Query(value = "SELECT text from menu_level where level = ?1 and  position = ?2", nativeQuery = true)
    String getTextByLevelAndPosition(int level, int position);

    //@Query(value = "SELECT position, button_size as buttonSize, button_text as buttonText from menu_level where level = ?1 and  parent_position = ?2  ORDER BY position", nativeQuery = true)
    @Query(value = "SELECT * from menu_level where level = ?1 and  parent_position = ?2  ORDER BY position", nativeQuery = true)
    List<MenuLevel> getButtonsByLevelAndPosition(int level, int parentPosition);
}
