package com.example.alex.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.alex.taskmanager.Utils.TaskDBHelper;
import com.example.alex.taskmanager.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    public RadioGroup myRadioGroup;
    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;
    private EditText mNoteEditText;
    private EditText mTagEditText;
    private Button mAddBtn;
    private TaskDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize to lowest mPriority by default (mPriority = 1)
        mPriority = 1;

        //init
        myRadioGroup = findViewById(R.id.radioGroupID);
        mNoteEditText = findViewById(R.id.add_task_note);
        mTagEditText = findViewById(R.id.add_task_tag);

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
        String tag = mTagEditText.getText().toString().trim();
        String date = getCurrentDate();
        dbHelper = new TaskDBHelper(this);

        int checkedRadioId = myRadioGroup.getCheckedRadioButtonId();

        if (checkedRadioId == R.id.low_radio_btn) {
            mPriority = 1;
        } else if (checkedRadioId == R.id.medium_radio_btn) {
            mPriority = 2;
        } else if (checkedRadioId == R.id.high_radio_btn) {
            mPriority = 3;
        } else if (checkedRadioId == R.id.urgent_radio_btn) {
            mPriority = 4;
        }

        if (TextUtils.isEmpty(note)) {
            Toast.makeText(this, "You must enter a task", Toast.LENGTH_SHORT).show();
            return;
        } else {
            goBackHome();
        }

        //create new task
        //Task task = new Task(note, priority, tag);
        Task task = new Task(note, mPriority, tag, date);
        dbHelper.saveNewTask(task);
        toastMessage("1 item added");

    }

    private void goBackHome() {
        startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
    }

    private String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("LLL dd, yyyy HH:mm", Locale.getDefault());

        return mdformat.format(calendar.getTime());

    }

    // customizable toast
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
