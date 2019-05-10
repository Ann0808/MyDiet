package com.pisk.mydiet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DaysListActivity extends Activity {

    private ListView listView;
    private TextView titleView;
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

        titleView = (TextView) findViewById(R.id.title);

        ColorDrawable sage = null;

        if (programNumber == 1) {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorSuperFit));
            titleView.setBackgroundResource(R.drawable.custom_shape1);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.superfit, 0);
        } else if (programNumber == 2) {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorFit));
            titleView.setBackgroundResource(R.drawable.custom_shape2);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fit, 0);
        } else if (programNumber == 3) {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorBalance));
            titleView.setBackgroundResource(R.drawable.custom_shape3);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.balance, 0);
        } else {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorStrong));
            titleView.setBackgroundResource(R.drawable.custom_shape4);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.strong, 0);
        }

        titleView.setText(getResources().getStringArray(R.array.programms)[programNumber - 1]);

        final String[] days = new String[28];

        for (int i = 0; i < days.length; i++) {
            days[i] = "День " + (i+1);
        }

        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, R.layout.day, days) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView mytextview=(TextView)view;

                if (programNumber == 1) {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape1);

                } else if (programNumber == 2) {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape2);

                } else if (programNumber == 3) {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape3);

                } else {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape4);

                }

                return view;
            }
        };

//        listView.setDivider(sage);
        listView.setDivider(null);
//        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);

//        for (int i = 0; i < days.length; i++) {
//            TextView view = (TextView) adapter.getView(i,null,null);
//            Log.d("design", "text is: " + view.getText());
//            view.setText("hh");
//        }



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
