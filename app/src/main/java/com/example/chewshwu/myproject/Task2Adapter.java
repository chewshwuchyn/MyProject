package com.example.chewshwu.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHEW SHWU on 10/7/2016.
 */


public class Task2Adapter extends ArrayAdapter {
        List list = new ArrayList();

        public Task2Adapter(Context context, int resource) {
            super(context, resource);
        }


        public void add(Task2 object) {
            super.add(object);
            list.add(object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row;

            row = convertView;
            Task2Holder task2Holder;
            if(row == null) {
                LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.row_layout3,parent,false);
                task2Holder = new Task2Holder();
            //    task2Holder.checkBox1 = (CheckBox)row.findViewById(R.id.checkBox1);
                task2Holder.tvPName = (TextView)row.findViewById(R.id.tvPName);
                task2Holder.tvTaskStartDate = (TextView)row.findViewById(R.id.tvTaskStartDate);
                task2Holder.tvTaskEndDate = (TextView)row.findViewById(R.id.tvTaskEndDate);

                row.setTag(task2Holder);

        /**        task2Holder.checkBox1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        CheckBox cb = (CheckBox) v;
                        Task2 task2 = (Task2)cb.getTag();
                        task2.setChecked(cb.isChecked());
                    }
                });**/
            }
            else{
                task2Holder = (Task2Holder)row.getTag();
            }


            Task2 task2 = (Task2)this.getItem(position);

        //    task2Holder.checkBox1.setTag(task2);

      //      task2Holder.checkBox1.setChecked((task2.isChecked()));
            task2Holder.tvPName.setText((task2.getTaskName()));
            task2Holder.tvTaskStartDate.setText("Start Date: " + task2.getTaskStartDate());
            task2Holder.tvTaskEndDate.setText("End Date: " + task2.getTaskEndDate());

            return row;
        }

        static class Task2Holder {
         //   CheckBox checkBox1;
            TextView tvPName;
            TextView tvTaskStartDate;
            TextView tvTaskEndDate;

        }


}





