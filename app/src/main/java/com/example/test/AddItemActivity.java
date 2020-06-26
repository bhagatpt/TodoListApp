package com.example.test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener {

    EditText title;
    EditText desc;
    Button saveBtn, cancelBtn, imgBtn;
    AppDatabase db;
    ImageView imageView;
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Todo List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        initListeners();

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .build();

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .build();
        //EnableRuntimePermission();
    }

    private void initViews() {
        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
        saveBtn = (Button) findViewById(R.id.saveButton);
        cancelBtn = (Button) findViewById(R.id.cancelButton);
       /* imgBtn = (Button) findViewById(R.id.imgBtn);
        imageView = (ImageView) findViewById(R.id.img);
*/
    }

    private void initListeners() {
        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        //imgBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                if (!title.getText().toString().equals("") && !desc.getText().toString().equals("")) {

                    final TodoListItem todoListItem = new TodoListItem(title.getText().toString(), desc.getText().toString(), false);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            db.databaseInterface().insertAll(todoListItem);
                        }
                    });
                    gotoMainActivity();
                } else {
                    Toast.makeText(AddItemActivity.this, "Please enter data in all fields", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelButton:
                gotoMainActivity();
                break;
  /*          case R.id.imgBtn:
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);

                break;*/
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                gotoMainActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoMainActivity() {
        Intent i = new Intent(AddItemActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddItemActivity.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(AddItemActivity.this, "CAMERA permission allows us to Access app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AddItemActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddItemActivity.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddItemActivity.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }
*/


}

