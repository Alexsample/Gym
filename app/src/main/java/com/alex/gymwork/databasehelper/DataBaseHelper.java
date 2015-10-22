package com.alex.gymwork.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.alex.gymwork.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static DataBaseHelper mDataBaseHelper;

    private final Context mContext;

    public static final String TABLE_EXERCISES = "table_exercises";
    public static final String COLUMN_EXERCISE_ID = "_id";
    public static final String COLUMN_GROUPS = "groups";
    public static final String COLUMN_EXERCISES = "exercises";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String TABLE_WORKOUT = "table_workout";
    public static final String COLUMN_WORKOUT_ID = "_id";
    public static final String COLUMN_TRAININGS = "trainings";

    private static String DB_NAME = "appgymdatabase";

    public DataBaseHelper(final Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public static DataBaseHelper getDataBase(final Context context) {
        if (mDataBaseHelper == null) {
            mDataBaseHelper = new DataBaseHelper(context);
        }
        return mDataBaseHelper;
    }

    public void createDataBase() throws IOException {
        final boolean dataBaseExists = checkDataBase();

        if (!dataBaseExists){
            try {
                createTables();
                completeDataBase();
            } catch (final IOException ioexception) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        final String DB_PATH = "/data/data/com.alex.gymwork/databases/";
        final File dataBaseFile = new File(DB_PATH + DB_NAME);
        return dataBaseFile.exists();
    }

    private void createTables() {
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("create table if not exists " + TABLE_EXERCISES + " ("
                        + COLUMN_EXERCISE_ID + " integer primary key autoincrement,"
                        + COLUMN_GROUPS + " text,"
                        + COLUMN_EXERCISES + " text,"
                        + COLUMN_DESCRIPTION + " text"
                        + ");"
        );
        sqLiteDatabase.execSQL("create table if not exists " + TABLE_WORKOUT + " ("
                        + COLUMN_WORKOUT_ID + " integer primary key autoincrement,"
                        + COLUMN_TRAININGS + " text"
                        + ");"
        );
        sqLiteDatabase.close();
    }

    private void completeDataBase() throws IOException{

        final String[] arrayGroups =
                mContext.getResources().getStringArray(R.array.groups);
        final String[] arrayArmsExercises =
                mContext.getResources().getStringArray(R.array.arms_exercises);
        final String[] arrayArmsDescription =
                mContext.getResources().getStringArray(R.array.arms_description);
        final String[] arrayShouldersExercises =
                mContext.getResources().getStringArray(R.array.shoulders_exercises);
        final String[] arrayShouldersDescription =
                mContext.getResources().getStringArray(R.array.shoulders_description);
        final String[] arrayStomachExercises =
                mContext.getResources().getStringArray(R.array.stomach_exercises);
        final String[] arrayStomachDescription =
                mContext.getResources().getStringArray(R.array.stomach_description);
        final String[] arrayBackExercises =
                mContext.getResources().getStringArray(R.array.back_exercises);
        final String[] arrayBackDescription =
                mContext.getResources().getStringArray(R.array.back_description);
        final String[] arrayChestExercises =
                mContext.getResources().getStringArray(R.array.chest_exercises);
        final String[] arrayChestDescription =
                mContext.getResources().getStringArray(R.array.chest_description);
        final String[] arrayLegsExercises =
                mContext.getResources().getStringArray(R.array.legs_exercises);
        final String[] arrayLegsDescription =
                mContext.getResources().getStringArray(R.array.legs_description);

        try {
            final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            final Cursor cursor =
                    sqLiteDatabase.query(TABLE_EXERCISES, null, null, null, null, null, null);

            if (cursor.getCount() == 0) {
                for (String group : arrayGroups) {
                    if (group.equals("arms")) {
                        for (int i = 0, n = arrayArmsExercises.length; i < n; i++) {
                            addExerciseData(group, arrayArmsExercises[i], arrayArmsDescription[i]);
                        }

                    } else if (group.equals("shoulders")) {
                        for (int i = 0, n = arrayShouldersExercises.length; i < n; i++) {
                            addExerciseData(group, arrayShouldersExercises[i], arrayShouldersDescription[i]);
                        }

                    } else if (group.equals("stomach")) {
                        for (int i = 0, n = arrayStomachExercises.length; i < n; i++) {
                            addExerciseData(group, arrayStomachExercises[i], arrayStomachDescription[i]);
                        }

                    } else if (group.equals("back")) {
                        for (int i = 0, n = arrayBackExercises.length; i < n; i++) {
                            addExerciseData(group, arrayBackExercises[i], arrayBackDescription[i]);
                        }

                    } else if (group.equals("chest")) {
                        for (int i = 0, n = arrayChestExercises.length; i < n; i++) {
                            addExerciseData(group, arrayChestExercises[i], arrayChestDescription[i]);
                        }

                    } else if (group.equals("legs")) {
                        for (int i = 0, n = arrayLegsExercises.length; i < n; i++) {
                            addExerciseData(group, arrayLegsExercises[i], arrayLegsDescription[i]);
                        }

                    }

                }
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (final Exception exception) {
            // To do nothing
        }
    }

    public void addExerciseData(final String group,
                                 final String exercise,
                                 final String description) {
        final SQLiteDatabase database = this.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GROUPS, group);
        contentValues.put(COLUMN_EXERCISES, exercise);
        contentValues.put(COLUMN_DESCRIPTION, description);
        database.insert(TABLE_EXERCISES, null, contentValues);
        database.close();
    }

    public String getExerciseDescription(final String groupName,
                                         final String exerciseName) {
        String exerciseDescription = "";
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final String sqlRequest = "SELECT " + COLUMN_DESCRIPTION
                + " FROM " + TABLE_EXERCISES
                + " WHERE " + COLUMN_GROUPS + " = ?" + " AND "
                + COLUMN_EXERCISES + " = ?";
        final Cursor cursor =
                sqLiteDatabase.rawQuery(sqlRequest, new String[] {groupName, exerciseName});

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                exerciseDescription =
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            }
        }
        cursor.close();
        sqLiteDatabase.close();

        return exerciseDescription;
    }

    public void deleteExercise(final String exerciseName) {
        if (!isTableEmpty(TABLE_EXERCISES)) {
            final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(
                    TABLE_EXERCISES,
                    COLUMN_EXERCISES + " = ?",
                    new String[]{exerciseName});
            sqLiteDatabase.close();
        }
    }

    public List<String> getOneGroupExercises(final String groupName) {
        final List<String> exercisesList = new ArrayList<String>();
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final String sqlRequest = "SELECT *"
                + " FROM " + TABLE_EXERCISES
                + " WHERE " + COLUMN_GROUPS + " = ?";
        final Cursor cursor = sqLiteDatabase.rawQuery(sqlRequest, new String[] {groupName});

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    exercisesList.add(cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISES)));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return exercisesList;
    }

    public List<String> getOneGroupDescriptions(final String groupName) {
        final List<String> descriptionsList = new ArrayList<String>();
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final String sqlRequest = "SELECT *"
                + " FROM " + TABLE_EXERCISES
                + " WHERE " + COLUMN_GROUPS + " = ?";
        final Cursor cursor = sqLiteDatabase.rawQuery(sqlRequest, new String[] {groupName});

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    descriptionsList.add(
                            cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                    );
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        sqLiteDatabase.close();

        return descriptionsList;
    }

    /**
    * Methods for table "table_workout"
    **/
    //Get all items from table "table_workout"
    public List<String> getAllWorkouts() {
        final List<String> workoutsList = new ArrayList<String>();
        if (isTableExist(TABLE_WORKOUT)) {
            final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            final Cursor cursor =
                    sqLiteDatabase.query(TABLE_WORKOUT, null, null, null, null, null, null);
            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        workoutsList.add(
                                cursor.getString(cursor.getColumnIndex(COLUMN_TRAININGS))
                        );
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            sqLiteDatabase.close();
        }

        return workoutsList;
    }

    public void addWorkout(final String workoutName) {
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRAININGS, workoutName);
        sqLiteDatabase.insert(TABLE_WORKOUT, null, contentValues);
        sqLiteDatabase.close();
    }

    public void deleteWorkout(final String programName) {
        if (!isTableEmpty(TABLE_WORKOUT)) {
            final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(
                    TABLE_WORKOUT,
                    COLUMN_TRAININGS + " = ?",
                    new String[]{programName});
            sqLiteDatabase.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean isTableExist(final String tableName) {
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?",
                new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            return false;
        }
        cursor.close();
        sqLiteDatabase.close();

        return true;
    }

    private boolean isTableEmpty(final String tableName) {
        boolean isEmpty;

        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        final Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);

        isEmpty = !cursor.moveToFirst();

        cursor.close();
        sqLiteDatabase.close();

        return isEmpty;
    }

}
