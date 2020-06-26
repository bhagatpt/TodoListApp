package com.example.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public UserAdapter adapter;
    List<TodoListItem> items;
    TextView emptyView;
     AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .build();
        getSupportActionBar().setTitle(R.string.todo_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //whenever the activity is started, it reads data from database and stores it into
        // local array list 'items'

        emptyView = (TextView) findViewById(R.id.empty_view);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                items = db.databaseInterface().getAllItems();

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                adapter = new UserAdapter(items);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

        };

        Thread newThread = new Thread(r);
        newThread.start();
        //it is very bad practice to pull data from Room on main UI thread,
        // that's why we create another thread which we use for getting the data and displaying it


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                if (items.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }
        });

        Button add = (Button) findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(i);
                finish();
            }
        });

        EditText search = (EditText) findViewById(R.id.etSearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });
    }

    void filter(String text) {
        List<TodoListItem> temp = new ArrayList();
        for (TodoListItem d : items) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getTitle().contains(text) || d.getDesc().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview

        adapter.updateList(temp);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (items != null && !items.isEmpty()) {
                    Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                            .build().databaseInterface().clearToDoItem();
                    Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                            .build().databaseInterface().insertAll(items);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (items != null && !items.isEmpty()) {
                    List<TodoListItem> list = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                            .build().databaseInterface().getAllItems();
                    adapter.refreshList(list);
                }
            }
        });
    }
}

