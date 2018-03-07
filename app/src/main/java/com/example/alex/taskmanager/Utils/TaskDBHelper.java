package com.example.alex.taskmanager.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.alex.taskmanager.model.Task;

import java.util.LinkedList;
import java.util.List;

public class TaskDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "task.db";
    public static final String TABLE_NAME = "Task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK_NOTE = "note";
    public static final String COLUMN_TASK_PRIORITY = "priority";
    public static final String COLUMN_TASK_TAG = "tag";
    public static final String COLUMN_TASK_DATE = "date";
    //possible priority constants
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_HIGH = 3;
    public static final int PRIORITY_URGENT = 4;

    private static final int DATABASE_VERSION = 1;


    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_NOTE + " TEXT NOT NULL, " +
                COLUMN_TASK_PRIORITY + " INTEGER NOT NULL, " +
                COLUMN_TASK_TAG + " TEXT NOT NULL, " +
                COLUMN_TASK_DATE + " DATETIME NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    /**
     * create record
     **/
    public void saveNewTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NOTE, task.getNote());
        values.put(COLUMN_TASK_PRIORITY, task.getPriority());
        values.put(COLUMN_TASK_TAG, task.getTag());
        values.put(COLUMN_TASK_DATE, task.getDate());

        // insert
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Query records, give options to filter results
     **/
    public List<Task> taskList(String filter) {
        String query;
        switch (filter) {
            case "":
                //regular query
                query = "SELECT  * FROM " + TABLE_NAME;
                break;
            case "Date":
            case "Priority":
                query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + filter + " DESC ";
                break;
            default:
                //filter results by filter option provided
                query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + filter;
                break;
        }

        List<Task> taskLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Task task;

        if (cursor.moveToFirst()) {
            do {
                task = new Task();

                task.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                task.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NOTE)));
                task.setPriority(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PRIORITY)));
                task.setTag(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TAG)));
                task.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DATE)));
                taskLinkedList.add(task);
            } while (cursor.moveToNext());
        }

        return taskLinkedList;
    }

    /**
     * Query only 1 record
     **/
    public Task getTask(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Task receivedTask = new Task();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedTask.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NOTE)));
            receivedTask.setPriority(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PRIORITY)));
            receivedTask.setTag(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TAG)));
            receivedTask.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_DATE)));
        }

        return receivedTask;
    }

    /**
     * delete record
     **/
    public void deleteTaskRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + id + "'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    /**
     * update record
     **/
    public void updateTaskRecord(long personId, Context context, Task updatedTask) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  " + TABLE_NAME + " SET note ='" + updatedTask.getNote() + "', priority ='" + updatedTask.getPriority() +
                "', tag ='" + updatedTask.getTag() + "', date ='" + updatedTask.getDate() +

                "'  WHERE _id='" + personId + "'");

        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }
}
