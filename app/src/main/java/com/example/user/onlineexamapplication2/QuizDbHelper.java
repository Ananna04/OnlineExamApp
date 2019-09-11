package com.example.user.onlineexamapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="OnlineExamApp.db";
    private static final int DATABASE_VERSION = 2;

    private SQLiteDatabase db;
    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable() {
        Questions q1 = new Questions("What is the name of the first atomic bomb dropped on Hiroshima?", "Little Boy", "Batman", "Fat Man", 1);
        addQuestion(q1);
        Questions q2 = new Questions("What is the name of the Upper House of the US congress?", "House of Commons", "Senate", "House of Lords",  2);
        addQuestion(q2);
        Questions q3 = new Questions("Which countries fought the Opium War?", "China & Uk", "China & Russia", "China & USA",  1);
        addQuestion(q3);
        Questions q4 = new Questions("Where were the summer olympic games 2016 field?", "Tokio", "Rio De Jenerio", "Sau Paulo",  2);
        addQuestion(q4);
        Questions q5 = new Questions("Where was the BRICS Summit 2017 held?", "Beijjing", "Xiamen", "Nanjing",  2);
        addQuestion(q5);
        Questions q6 = new Questions("IC chips used in computers are usually made of:", "Lead", "Silicon", " Chromium",  2);
        addQuestion(q6);
        Questions q7 = new Questions("Which of the following is not an example of an Operating System?", "Windows 98", "BSD Unix", " Microsoft Office XP",  3);
        addQuestion(q7);
        Questions q8 = new Questions(" Which of the following is not an input device?", "VDU", "Mouse", " Light pen",  1);
        addQuestion(q8);
        Questions q9 = new Questions("Which of the following is an example of non-volatile memory?", "Cache memory", "RAM", "ROM",  3);
        addQuestion(q9);
        Questions q10 = new Questions(" One Gigabyte is approximately equal is:", "1000,000 bytes", "1000,000,000 bytes", "1000,000,000,000 bytes",  2);
        addQuestion(q10);

    }

    private void addQuestion(Questions q1) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, q1.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, q1.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, q1.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, q1.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, q1.getAnswerNr());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Questions> getAllQuestions() {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
