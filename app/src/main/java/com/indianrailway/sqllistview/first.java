package com.indianrailway.sqllistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class first extends AppCompatActivity {

    // Declaring layout button, edit texts
    Button addBtn,finishBtn;
    EditText name,pic;
    Connection con;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        addBtn = (Button) findViewById(R.id.nextDB);
        finishBtn = (Button) findViewById(R.id.nextPage);
        name=findViewById(R.id.nameET);
        pic=findViewById(R.id.ImageET);

        con=databaseConnect.ConnectDB();
        // Setting up the function when button login is clicked
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                insertData  insertData=new insertData();// this is the Asynctask, which is used to process in background to reduce load on app process
                insertData.execute("");
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(first.this,MainActivity.class));
            }
        });
    }

    public class insertData extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(String r)
        {
            Toast.makeText(first.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(first.this , "Data Added" , Toast.LENGTH_LONG).show();

            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String addName = name.getText().toString();
            String image = pic.getText().toString();

            if(addName.trim().equals("")|| image.trim().equals(""))
                z = "Please enter details";
            else
            {
                try
                {
                    con = databaseConnect.ConnectDB(); // Connect to database
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        //String query = "select * from TEST_TABLE where ID= " + usernam + " and USERPASSWORD = '"+ passwordd +"';";
                        //String query = "insert into AlertInfo (what,location,additional) values ('" + sendWhat + "','" + sendWhere + "','" + sendMore + "');";
                        String query ="INSERT INTO MainTable(name,url) values('"+addName+"','"+image+"');";
                        Statement stmt = con.createStatement();
                        stmt.executeQuery(query);
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }
}
