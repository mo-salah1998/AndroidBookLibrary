package com.example.booklibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
     EditText title_input,author_input,number_input;
     Button update_button , delete_button;
    String id, title , author , pages ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        number_input = findViewById(R.id.number_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delelte);
        getAndSetIntentData();

        // le reglage du nom de l'activite
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                pages = number_input.getText().toString().trim();

                mydb.UpdateData(id,title,author,pages);
            }

        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmeDialoge();
            }
        });
    }

    public void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
            //getting text from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title").trim();
            author = getIntent().getStringExtra("author").trim();
            pages = getIntent().getStringExtra("pages").trim();
            //setting Intent data
            title_input .setText(title);
            author_input.setText(author);
            number_input.setText(pages);
        }else{
            Toast.makeText(this,"No Date changed", Toast.LENGTH_SHORT).show();

        }

    }



    public void confirmeDialoge(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title + " ?");
        builder.setMessage("are you sure you want to deleat " + title + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(UpdateActivity.this);
                mydb.deleteOneRow(id);
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