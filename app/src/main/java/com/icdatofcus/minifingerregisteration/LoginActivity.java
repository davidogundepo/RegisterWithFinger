package com.icdatofcus.minifingerregisteration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.userName);
        Password = findViewById(R.id.passWord);
    }

    public void adminAccess (View v) {
        if (Username.getText().toString().equalsIgnoreCase("admin") && Password.getText().toString().equalsIgnoreCase("admin")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(this, "You are extremely blessed, Kilode :)", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "You better hurry up", Toast.LENGTH_LONG).show();
    }

    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to Exit?");
        alertDialog.setPositiveButton("YES ",
                (dialog, which) -> finish());
        alertDialog.setNegativeButton("NO",
                (dialog, which) -> {
                    dialog.cancel();
                });
        alertDialog.show();
    }
}
