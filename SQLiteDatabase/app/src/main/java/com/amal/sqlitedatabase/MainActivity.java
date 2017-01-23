package com.amal.sqlitedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amal.sqlitedatabase.model.Work;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText work, description;
    TextView dataText;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);

        work = (EditText) findViewById(R.id.editText_work);
        description = (EditText) findViewById(R.id.editText_description);
        dataText = (TextView) findViewById(R.id.textView_data);

    }

    public void addToDB(View v){
        Date currDateTime = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDateTime);
        calendar.add(Calendar.DATE, 14);

        db.addWork(new Work(work.getText().toString(), description.getText().toString(), currDateTime));
    }

    public void getFromDB(View v){
        List<Work> todos = db.getAllWorkByTitle();

        String data = "";
        for (Work w : todos){
            data = data + w.getTitle() + "----" + w.getDescription() + "\n";
        }
        dataText.setText(data);
    }

    public void updateDB(View v){
        Date currDateTime = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDateTime);
        db.updateContact(new Work(work.getText().toString(), description.getText().toString(), currDateTime));
    }

    public void deleteFromDB(View v){
        Date currDateTime = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDateTime);
        db.deleteContact(new Work(work.getText().toString(), description.getText().toString(), currDateTime));

    }


}