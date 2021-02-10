package com.example.a8;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText et_username, et_password;
    Button btn_register, btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        et_username = (EditText)findViewById(R.id.lusername);
        et_password = (EditText)findViewById(R.id.lpassword);
        btn_register = (Button)findViewById(R.id.lregister);
        btn_login = (Button)findViewById(R.id.llogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String Username = et_username.getText().toString();
                    String Password = et_password.getText().toString();
                    boolean user = databaseHelper.CheckUsernamePassword(Username,Password);
                    if (user)
                    {

                        makeText(getApplicationContext(), "Login Successful", LENGTH_SHORT).show();
                        /*Intent intent = new Intent(DataBaseActivity.this, MainActivity.class);
                        startActivity(intent);*/

                    }
                    else
                    {
                        makeText(getApplicationContext(), "Login Unsuccessful! Please verify your Username and Password", LENGTH_SHORT).show();
                    }
                }
                catch (SQLiteException ex)
                {
                    makeText(getApplicationContext(), ""+ex, LENGTH_SHORT).show();
                }

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if(username.equals("") || password.equals("")){
                    makeText(getApplicationContext(), "Fields Required", LENGTH_SHORT).show();
                }else{

                    if(password.equals(password)){
                        Boolean checkusername = databaseHelper.CheckUsername(username);
                        if (!checkusername)
                            makeText(getApplicationContext(), "Username already taken", LENGTH_SHORT).show();
                        else {
                            boolean insert;
                            if (databaseHelper.Insert(username, password)) insert = true;
                            else insert = false;
                            if(insert){
                                makeText(getApplicationContext(), "Registered", LENGTH_SHORT).show();
                                et_username.setText("");
                                et_password.setText("");
                            }
                        }
                    }else{
                        makeText(getApplicationContext(), "Password does not match", LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
