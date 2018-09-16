package com.assigementlogin.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.assigementlogin.R;
import com.assigementlogin.storage.SessionManager;

public class DashBoardActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    Button logoutbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logoutbtn = (Button)findViewById(R.id.logout_btn);
        sessionManager = new SessionManager(DashBoardActivity.this);
        // Check if user is already logged in or not
        if (!sessionManager.isLoggedIn()) {

            logoutUser();
        }
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }
    private void logoutUser() {
        sessionManager.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
