package com.example.imad.tdlist;

import android.app.AlertDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtDate_act_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDate_act_main = (TextView) findViewById(R.id.date);

        txtDate_act_main.setText(currentDate().toString());



    }

    public String currentDate()
    {
        String curDate;
        String longDay, longMonth;
        String longDate;
        String longYear;

        Calendar calendar = Calendar.getInstance();
        longDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        longMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        longDate = dateFormat.format(calendar.getTime());

        dateFormat.applyPattern("yyyy");
        longYear = dateFormat.format(calendar.getTime());

        Log.d("currentDate", ""+longDay+longDate+longMonth+longYear);

        curDate = longDay + ", " + longDate + " " + longMonth + " " + longYear;

        return curDate;
    }

    public void helpClicked(View view)
    {
        Intent intent = new Intent(MainActivity.this, Help.class);
        startActivity(intent);
    }

    public void addClicked()
    {
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

    }
}
