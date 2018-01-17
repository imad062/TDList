package com.example.imad.tdlist;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Handler;

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

        updateView(database.getAllData());

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
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final AlertDialog alertDialog;



        TextView title = new TextView(this);
        title.setText("Add Task");
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 10, 10, 10);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);

        alert.setCustomTitle(title)
                .setView(R.layout.alert_edittext)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Dialog dialog1 = (Dialog) dialog;
                        EditText editTextTask = (EditText) dialog1.findViewById(R.id.edittext_task);
                        database.insertData(editTextTask.getText().toString());
                        updateView(database.getAllData());

                        Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "CANCELLED", Toast.LENGTH_SHORT).show();
                    }
                });


        alertDialog = alert.create();
        alertDialog.show();
    }

    public void doneClicked(View view)
    {
        View parent = (View) view.getParent();
        TextView textTask = (TextView) parent.findViewById(R.id.txt_task);
        String task = textTask.getText().toString();
        database.deleteData(task);
        updateView(database.getAllData());
    }

    public void updateView(Cursor data)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        while (data.moveToNext())
        {
            arrayList.add(data.getString(1));
        }

        if(arrayAdapter == null)
        {
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_todo,R.id.txt_task,arrayList);
            listView.setAdapter(arrayAdapter);
        }
        else
        {
            arrayAdapter.clear();
            arrayAdapter.addAll();
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_todo,R.id.txt_task,arrayList);
            listView.setAdapter(arrayAdapter);

        }

        data.close();
        database.close();

    }
}
