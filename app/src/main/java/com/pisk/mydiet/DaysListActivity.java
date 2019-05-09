package com.pisk.mydiet;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DaysListActivity extends Activity {

    private ListView listView;
    //Bundle b;
    int programNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_list);

        Intent intentTmp = getIntent();
        //b = intentTmp.getBundleExtra("bund");
        programNumber = intentTmp.getIntExtra("arg_program_number",0);

        listView = (ListView) findViewById(R.id.daylist2);

        final String[] days = new String[30];

        for (int i = 0; i < days.length; i++) {
            days[i] = "День " + (i+1);
        }

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.day, days);

        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setAdapter(adapter);

        final Intent intent = new Intent(this, PagerActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

//                int newPos = position;
//                if (newPos !=0 ) {
//                    newPos++;
//                }
//                b.putInt("arg_day_number", newPos);
//                intent.putExtra("bund", b);
                intent.putExtra("arg_day_number", (position + 1));
                intent.putExtra("arg_program_number", programNumber);
                startActivity(intent);
            }
        });

    }
}
