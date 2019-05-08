package com.pisk.mydiet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter {

    static final String TAG = "myLogs";
    public static final int TYPE_SuperFit = 0;
    public static final int TYPE_Fit = 1;
    public static final int TYPE_Balance = 2;
    public static final int TYPE_Strong = 3;

    private ListViewItem[] objects;

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return objects[position].getType();
    }

    public CustomAdapter(Context context, int resource, ListViewItem[] objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        ListViewItem listViewItem = objects[position];
        int listViewItemType = getItemViewType(position);

        Log.d(TAG, "listViewItemType is: " + listViewItemType);

        if (convertView == null) {

            if (listViewItemType == TYPE_SuperFit) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.programms_list_item1, null);
            } else if (listViewItemType == TYPE_Fit) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.programms_list_item2, null);
            } else if (listViewItemType == TYPE_Balance) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.programms_list_item3, null);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.programms_list_item4, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.textview);
//            //textView.setText("jj");
//            textView.measure(0, 0);
//            Log.d(TAG, "width is: " + textView.getMeasuredWidth());
//            textView.getLayoutParams().height = (int) (textView.getMeasuredWidth()*0.3);
            viewHolder = new ViewHolder(textView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.getText().setText(listViewItem.getText());
        //viewHolder.getText().setText("h");

        return convertView;
    }

}
