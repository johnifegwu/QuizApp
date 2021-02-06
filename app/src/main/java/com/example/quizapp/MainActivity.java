package com.example.quizapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    MenuItem SetSoundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.SetSoundView = menu.getItem(0);
        try {
            this.SetSoundView.setChecked(this.getIsPlaySound());
        } finally {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(item.isChecked() && item.isCheckable()){
            item.setChecked(false);
        }else if(item.isChecked() == false && item.isCheckable()){
            item.setChecked(true);
        }
        //Set Sound Options
        if(item.isCheckable() && id == R.id.app_bar_switch){
            this.setSound(item);
        }

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.app_bar_switch) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void setSound(MenuItem item) {
        try {
               this.setIsPlaySound(item.isChecked());
        } catch (Exception e) {
             Log.e("Error", e.toString());
        }
    }

    public boolean getIsPlaySound() {
        SharedPreferences sp = getSharedPreferences(module.MyPREFERENCES, Context.MODE_PRIVATE);
        module.isPlaySound = sp.getBoolean(module.Name,true);
        return module.isPlaySound;
    }

    public void setIsPlaySound(boolean IsPlaySound) {
        SharedPreferences sp = getSharedPreferences(module.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(module.Name, IsPlaySound);
        editor.apply();
        module.isPlaySound = IsPlaySound;
    }

    public void showAbout(MenuItem menuItem) {
        try {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("About Us")
                    .setContentText("Math Table Quiz App \n Version 1.0 \n \n (c) 2021 Mickle Entity Ltd. \n\n Write us a feedback to \n mickle.entity.ltd@gmail.com \n\n")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }
}