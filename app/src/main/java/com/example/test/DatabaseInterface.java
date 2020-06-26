package com.example.test;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DatabaseInterface {

    @Query("SELECT * FROM todolistitem")
    List<TodoListItem> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TodoListItem... todoListItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TodoListItem> todoListItems);

    @Query("DELETE FROM todolistitem")

    void clearToDoItem();
}
