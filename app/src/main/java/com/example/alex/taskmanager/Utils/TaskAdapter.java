package com.example.alex.taskmanager.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.taskmanager.R;
import com.example.alex.taskmanager.UpdateTaskActivity;
import com.example.alex.taskmanager.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mTaskList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskAdapter(List<Task> myDataset, Context context, RecyclerView recyclerView) {
        mTaskList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    public void add(int position, Task task) {
        mTaskList.add(position, task);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mTaskList.remove(position);
        notifyItemRemoved(position);
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

        holder.taskNoteTxtV.setText(task.getNote());
        holder.taskTagTxtV.setText(task.getTag());
        // get date
        holder.taskDateTxtV.setText(task.getDate());
        // get selected priority and color as int -> display as String
        int selectedPriority = task.getPriority();
        getPriorityAndColor(holder, selectedPriority);

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
//                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        Toast.makeText(mContext, "1 item removed", Toast.LENGTH_SHORT).show();
//                        notifyItemRangeChanged(position, mTaskList.size());
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

    private void goToUpdateActivity(long personId) {
        Intent goToUpdate = new Intent(mContext, UpdateTaskActivity.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    // Return the priority with the corresponding color ex low = green, medium = yellow...
    private void getPriorityAndColor(ViewHolder holder, int priority) {

        switch (priority) {
            case TaskDBHelper.PRIORITY_LOW:
                holder.taskPriorityTxtV.setText(R.string.radio_low_priority);
                holder.taskPriorityTxtV.setTextColor(ContextCompat.getColor(mContext, R.color.lowPri));
                break;
            case TaskDBHelper.PRIORITY_MEDIUM:
                holder.taskPriorityTxtV.setText(R.string.radio_medium_priority);
                holder.taskPriorityTxtV.setTextColor(ContextCompat.getColor(mContext, R.color.mediumPri));
                break;
            case TaskDBHelper.PRIORITY_HIGH:
                holder.taskPriorityTxtV.setText(R.string.radio_high_priority);
                holder.taskPriorityTxtV.setTextColor(ContextCompat.getColor(mContext, R.color.highPri));
                break;
            case TaskDBHelper.PRIORITY_URGENT:
                holder.taskPriorityTxtV.setText(R.string.radio_urgent_priority);
                holder.taskPriorityTxtV.setTextColor(ContextCompat.getColor(mContext, R.color.urgentPri));
                break;
            default:
                break;
        }
    }

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskNoteTxtV;
        public TextView taskPriorityTxtV;
        public TextView taskTagTxtV;
        public TextView taskDateTxtV;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            taskNoteTxtV = v.findViewById(R.id.single_row_note);
            taskTagTxtV = v.findViewById(R.id.single_row_tag);
            taskDateTxtV = v.findViewById(R.id.single_row_date);
            taskPriorityTxtV = v.findViewById(R.id.single_row_priority);
        }
    }


}