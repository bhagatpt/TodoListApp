package com.example.test;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TodoListItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="isChecked")
    private boolean isChecked;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    public TodoListItem(){

    }

    public TodoListItem( String title,String desc,boolean isChecked) {
        this.desc = desc;
        this.title = title;
        this.isChecked=isChecked;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
