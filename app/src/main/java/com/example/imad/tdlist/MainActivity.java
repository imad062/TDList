package com.example.imad.tdlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper database;

    TextView txtDate_act_main;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseHelper(this);
        txtDate_act_main = (TextView) findViewById(R.id.date);
        listView = (ListView) findViewById(R.id.list);

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

    public void addClicked(View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        TextView title = new TextView(this);
        title.setText("Add Task");
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 10, 10, 10);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);

        final EditText editTextTask = (EditText) findViewById(R.id.edittext_task);

        alert.setCustomTitle(title)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.insertData(editTextTask.getText().toString());
                        Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "CANCELLED", Toast.LENGTH_LONG).show();
                    }
                })
                .setView(R.layout.alert_edittext);

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void updateView(Cursor data)
    {
        ArrayList<String> arrayList = new ArrayList<>();

    }
}
