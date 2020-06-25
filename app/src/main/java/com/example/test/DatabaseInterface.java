package com.example.test;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DatabaseInterface {

    @Query("SELECT * FROM todolistitem")
    List<TodoListItem> getAllItems();

    @Insert
    void insertAll(TodoListItem... todoListItems);
}
