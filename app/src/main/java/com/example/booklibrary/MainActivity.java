package com.example.booklibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    FloatingActionButton add_button ;
    MyDatabaseHelper myDB ;
    ArrayList<String> book_id ,book_title, book_auther ,book_pages ;

    ImageView empty_image ;
    TextView no_data ;

    CustomAdapter customAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 ){
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.addBotton);

        empty_image = findViewById(R.id.empty_img);
        no_data = findViewById(R.id.noData);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , AddActivity.class);
                startActivity(intent);
            }
        });


        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_auther = new ArrayList<>();
        book_pages =new ArrayList<>();

        storeDataInArrays();


        customAdapter = new CustomAdapter(MainActivity.this ,this , book_id,book_title,book_auther,book_pages);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    public void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_image.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);

        }else{
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_auther.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
            empty_image.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delelete_all){
            confirmeDialoge();

        }
        return super.onOptionsItemSelected(item);

    }


    public void confirmeDialoge(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("are you sure you want to deleat All ?  ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(MainActivity.this);
                mydb.deleleteAllData();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.create().show();
    }



}