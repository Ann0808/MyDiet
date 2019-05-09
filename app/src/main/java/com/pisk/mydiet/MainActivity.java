package com.pisk.mydiet;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private ArrayAdapter<String> mAdapter;
    private ListView listView;
    private int countProgramms = 4;
    static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



 //      TextView textView = (TextView) findViewById(R.id.textview);
//        textView.measure(0, 0);
//       Log.d(TAG, "width is: " + textView.getMeasuredWidth());
//        textView.getLayoutParams().height = (int) (textView.getMeasuredWidth()*0.3);

        listView = (ListView) findViewById(R.id.listView);

        Resources res = getResources();
        final String[] programms = res.getStringArray(R.array.programms);

        final ListViewItem[] items = new ListViewItem[countProgramms];

        for (int i = 0; i < items.length; i++) {
            if (i == 0) {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_SuperFit);
            } else if (i == 1) {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_Fit);
            } else if (i == 2) {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_Balance);
            } else {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_Strong);
            }
        }

        CustomAdapter customAdapter = new CustomAdapter(this, R.id.textview, items);

//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics ();
 //       display.getMetrics(outMetrics);

//        float density  = getResources().getDisplayMetrics().density;
        //float dpHeight = outMetrics.heightPixels / density;
//        float dpWidth  = outMetrics.widthPixels / density;


//        View convertView = customAdapter.getView(3,null, this.listView);
//        TextView textView = convertView.findViewById(R.id.textview);
//        textView.measure(0, 0);
//        Log.d(TAG, "height is: " + textView.getMeasuredHeight());
//        Log.d(TAG, "width is: " + dpWidth);
//        textView.getLayoutParams().height = (int) (dpWidth*2);


        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setAdapter(customAdapter);


        final Intent intent = new Intent(this, DaysListActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                Log.d(TAG, "position is: " + position);

//                int newPos = position;
//                Bundle b = new Bundle();
//                if (newPos !=0 ) {
//                    newPos++;
//                }
                //b.putInt("arg_program_number", newPos);
                //intent.putExtra("bund", b);
                intent.putExtra("arg_program_number", (position + 1));
                startActivity(intent);
            }
        });

    }
}
