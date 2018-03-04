package com.example.alex.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.alex.taskmanager.Utils.TaskDBHelper;
import com.example.alex.taskmanager.model.Task;

public class AddTaskActivity extends AppCompatActivity {
    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;

    private EditText mNoteEditText;
    private EditText mPriorityEditText;
    private EditText mTagEditText;
    private TextView mDateEditTextView;
    RadioButton radioButton;


    private Button mAddBtn;

    private TaskDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize to highest mPriority by default (mPriority = 1)
        radioButton = findViewById(R.id.radioButton);
        mPriority = 1;

        //init
        mNoteEditText = findViewById(R.id.add_task_note);
        mPriorityEditText = findViewById(R.id.add_task_priority);
        mTagEditText = findViewById(R.id.add_task_tag);
        mDateEditTextView = findViewById(R.id.single_row_date);

        mAddBtn = findViewById(R.id.addNewTaskButton);

        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save task method
                saveTask();
            }
        });

    }

    private void saveTask() {
        String note = mNoteEditText.getText().toString().trim();
        String priority = mPriorityEditText.getText().toString().trim();
        String tag = mTagEditText.getText().toString().trim();
        String date = getCurrentDate();
        dbHelper = new TaskDBHelper(this);

        String radio = radioButton.getText().toString().trim();

        if (radio.isEmpty()) {
            //error name is empty
            Toast.makeText(this, "You must enter a radio", Toast.LENGTH_SHORT).show();
        } else {
            goBackHome();
        }

        if (note.isEmpty()) {
            //error name is empty
            Toast.makeText(this, "You must enter a note", Toast.LENGTH_SHORT).show();
        } else {
            goBackHome();
        }

        if (priority.isEmpty()) {
            //error priority is empty
            Toast.makeText(this, "You must enter a priority", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(priority) < 0 && Integer.parseInt(priority) > 5) {
            //error priority is out of range ( 1 lowest - 5 highest)
            Toast.makeText(this, "You must enter a priority", Toast.LENGTH_SHORT).show();
        } else {
            goBackHome();
        }

        if (tag.isEmpty()) {
            //error tag is empty
            Toast.makeText(this, "You must enter a tag", Toast.LENGTH_SHORT).show();
        } else {
            goBackHome();
        }


        //create new task
        //Task task = new Task(note, priority, tag);

        Task task = new Task(note, priority, tag, date , radio);
        dbHelper.saveNewTaskWithRADIO(task);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete


    }

    private void goBackHome() {
        startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
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


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
//    public void onPrioritySelected(View view) {
//        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
//            mPriority = 1;
//        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
//            mPriority = 2;
//        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
//            mPriority = 3;
//        }
//    }

}
