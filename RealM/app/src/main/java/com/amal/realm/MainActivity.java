package com.amal.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import com.amal.realm.model.Work;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm myRealm;

    EditText work, description;
    TextView dataText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        work = (EditText) findViewById(R.id.editText_work);
        description = (EditText) findViewById(R.id.editText_description);
        dataText = (TextView) findViewById(R.id.textView_data);

        Realm.init(this);
        myRealm = Realm.getDefaultInstance();

    }

    public void addToDB(View v){
        try {
            Date currDateTime = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currDateTime);
            calendar.add(Calendar.DATE, 14);

            final Work w = new Work();
            w.setTitle(work.getText().toString());
            w.setDescription(description.getText().toString());
            w.setDateCreated(currDateTime);

            myRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(w);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getFromDB(View v){
        try {
            RealmResults<Work> results1 = myRealm.where(Work.class).findAll();

            String result = "";
            for(Work w:results1) {
                result = result + w.getTitle() + "---" + w.getDescription() + "\n";
            }
            dataText.setText(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateDB(View v){

        try {
            final Work w = myRealm.where(Work.class).equalTo("title", work.getText().toString()).findFirst();
            if(w != null){
                myRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        w.setDescription(description.getText().toString());
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteFromDB(View v){
        try {
            final RealmResults<Work> results = myRealm.where(Work.class).equalTo("title", work.getText().toString()).findAll();
            myRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteAllFromRealm();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void oderByTitle(View v){
        try {
            RealmResults<Work> results3 = myRealm.where(Work.class).findAllSorted("title");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void likeTitle(View v){
        try {
            RealmQuery<Work> query = myRealm.where(Work.class);
            query.contains("title","a");
            RealmResults<Work> result1 = query.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}