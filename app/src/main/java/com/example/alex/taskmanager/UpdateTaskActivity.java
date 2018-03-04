package com.example.alex.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.alex.taskmanager.Utils.TaskDBHelper;
import com.example.alex.taskmanager.model.Task;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText mNoteEditText;
    private EditText mPriorityEditText;
    private EditText mTagEditText;
    private TextView mDateTextView;

    private Button mUpdateBtn;

    private TaskDBHelper dbHelper;
    private long receivedTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        //init
        mNoteEditText = findViewById(R.id.update_task_note);
        mPriorityEditText = findViewById(R.id.update_task_priority);
        mTagEditText = findViewById(R.id.update_task_tag);
        mUpdateBtn = findViewById(R.id.updateTaskButton);
        mDateTextView = findViewById(R.id.single_row_date);

        dbHelper = new TaskDBHelper(this);

        try {
            //get intent to get person id
            receivedTaskId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Task queriedTask = dbHelper.getTask(receivedTaskId);
        //set field to this user data
        mNoteEditText.setText(queriedTask.getNote());
        mPriorityEditText.setText(queriedTask.getPriority());
        mTagEditText.setText(queriedTask.getTag());



        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updateTask();
            }
        });


    }

    private void updateTask(){
        String note = mNoteEditText.getText().toString().trim();
        String priority = mPriorityEditText.getText().toString().trim();
        String tag = mTagEditText.getText().toString().trim();
        String date = getCurrentDate();

        if(note.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a note", Toast.LENGTH_SHORT).show();
        } else  {
            //finally redirect back home
            goBackHome();
        }

        if(priority.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an priority", Toast.LENGTH_SHORT).show();
        } else {
            //finally redirect back home
            goBackHome();
        }

        if(tag.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an tag", Toast.LENGTH_SHORT).show();
        } else {
            //finally redirect back home
            goBackHome();
        }

        //create updated person
        Task updatedTask = new Task(note, priority, tag, date);

        //call dbhelper update
        dbHelper.updateTaskRecord(receivedTaskId, this, updatedTask);


    }

    private void goBackHome(){
        startActivity(new Intent(this, MainActivity.class));
    }



    // customizable toast
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }



    private String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("LLL dd, yyyy HH:mm", Locale.getDefault());

        return mdformat.format(calendar.getTime());

    }

}
