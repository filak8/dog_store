package com.example.lenovo.dogstore.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lenovo.dogstore.models.Dog;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "uuid text,"
                + "breed text,"
                + "sub_breed text,"
                + "uri text" + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void fillDataBase(ArrayList<String> breedList, String breed) {

        SQLiteDatabase database = getWritableDatabase();

        for (int i = 0; i < breedList.size(); i++) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("uuid", i);
            contentValues.put("breed", breed);
            contentValues.put("sub_breed", breedList.get(i));

            long rowID = database.insert("mytable", null, contentValues);
        }

        close();
    }

    public void fillOrUpdateDataBase(ArrayList<String> breedList, String breed) {

        SQLiteDatabase database = getWritableDatabase();

        if (database.delete("mytable", null, null) != 0) {

            database.delete("mytable", null, null);
            fillDataBase(breedList, breed);
        } else {
            fillDataBase(breedList, breed);
        }

    }

    public ArrayList<Dog> getDogsFromDataBase() {

        SQLiteDatabase database = getWritableDatabase();

        Cursor cursor = database.query("mytable", null, null,
                null, null, null, null);

        ArrayList<Dog> result = new ArrayList<>();

        if (cursor.moveToFirst()) {

            int breedColIndex = cursor.getColumnIndex("breed");
            int subBreedColIndex = cursor.getColumnIndex("sub_breed");

            do {
                Dog dog = new Dog();
                dog.setBreed(cursor.getString(breedColIndex));
                dog.setSub_breed(cursor.getString(subBreedColIndex));
                result.add(dog);
            } while (cursor.moveToNext());
        } else cursor.close();

        close();
        return result;
    }

}
