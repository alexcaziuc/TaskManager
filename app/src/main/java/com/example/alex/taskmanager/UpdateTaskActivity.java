package com.example.alex.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.taskmanager.Utils.TaskDBHelper;
import com.example.alex.taskmanager.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateTaskActivity extends AppCompatActivity {

    public RadioGroup myRadioGroup;
    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;
    private EditText mNoteEditText;
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
        mTagEditText = findViewById(R.id.update_task_tag);
        mUpdateBtn = findViewById(R.id.updateTaskButton);
        mDateTextView = findViewById(R.id.single_row_date);
        myRadioGroup = findViewById(R.id.radioGroupID);

        dbHelper = new TaskDBHelper(this);

        try {
            //get intent to get person id
            receivedTaskId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //populate user data with old values before update
        Task queriedTask = dbHelper.getTask(receivedTaskId);
        //set field to this user data
        mNoteEditText.setText(queriedTask.getNote());
        mTagEditText.setText(queriedTask.getTag());
        //get priority from selected id and set radio button
        int receivedPriority = queriedTask.getPriority();
        switch (receivedPriority) {
            case TaskDBHelper.PRIORITY_LOW:
                myRadioGroup.check(R.id.low_radio_btn);
                break;
            case TaskDBHelper.PRIORITY_MEDIUM:
                myRadioGroup.check(R.id.medium_radio_btn);
                break;
            case TaskDBHelper.PRIORITY_HIGH:
                myRadioGroup.check(R.id.high_radio_btn);
                break;
            case TaskDBHelper.PRIORITY_URGENT:
                myRadioGroup.check(R.id.urgent_radio_btn);
                break;
        }

        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updateTask();
            }
        });

    }

    private void updateTask() {
        String note = mNoteEditText.getText().toString().trim();

        String tag = mTagEditText.getText().toString().trim();
        String date = getCurrentDate();

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

        //create updated person
        Task updatedTask = new Task(note, mPriority, tag, date);

        //call dbhelper update
        dbHelper.updateTaskRecord(receivedTaskId, this, updatedTask);
        toastMessage("item updated");
    }

    private void goBackHome() {
        startActivity(new Intent(this, MainActivity.class));
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
