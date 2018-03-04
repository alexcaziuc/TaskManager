package com.example.alex.taskmanager.Utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.example.alex.taskmanager.R;
import com.example.alex.taskmanager.UpdateTaskActivity;
import com.example.alex.taskmanager.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mTaskList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    int color = 0;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView taskNoteTxtV;
        public TextView taskPriorityTxtV;
        public TextView taskTagTxtV;
        public TextView taskDateTxtV;
        public TextView taskRadio;

        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            taskNoteTxtV = v.findViewById(R.id.single_row_note);
            taskPriorityTxtV = v.findViewById(R.id.single_row_priority);
            taskTagTxtV = v.findViewById(R.id.single_row_tag);
            taskDateTxtV = v.findViewById(R.id.single_row_date);
            taskRadio = v.findViewById(R.id.single_row_radio);

            //taskPriorityTxtV.setTextColor(getPriorityColor("5"));

        }
    }

    public void add(int position, Task task) {
        mTaskList.add(position, task);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mTaskList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskAdapter(List<Task> myDataset, Context context, RecyclerView recyclerView) {
        mTaskList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Task task = mTaskList.get(position);
        holder.taskNoteTxtV.setText("note: " + task.getNote());
        holder.taskPriorityTxtV.setText("priority: " + task.getPriority());
        holder.taskTagTxtV.setText("tag: #" + task.getTag());
        // get date
        holder.taskDateTxtV.setText(task.getDate());
        holder.taskRadio.setText(task.getRadio());



        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete task?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    //go to update activity
                        goToUpdateActivity(task.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskDBHelper dbHelper = new TaskDBHelper(mContext);
                        dbHelper.deleteTaskRecord(task.getId(), mContext);

                        mTaskList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mTaskList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }

    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(mContext, UpdateTaskActivity.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTaskList.size();
    }







//
//    private int getPriorityColor(String priority) {
//        int magnitudeColorResourceId = 0;
//        int magnitudeFloor = Integer.parseInt(priority);
//        switch (magnitudeFloor) {
//            case 0:
//            case 1:
//                magnitudeColorResourceId = R.color.magnitude1;
//                break;
//            case 2:
//                magnitudeColorResourceId = R.color.magnitude2;
//                break;
//            case 3:
//                magnitudeColorResourceId = R.color.magnitude3;
//                break;
//            case 4:
//                magnitudeColorResourceId = R.color.magnitude4;
//                break;
//            case 5:
//                magnitudeColorResourceId = R.color.magnitude5;
//                break;
//        }
//
//        return ContextCompat.getColor(mContext, magnitudeColorResourceId);
//    }
//





}