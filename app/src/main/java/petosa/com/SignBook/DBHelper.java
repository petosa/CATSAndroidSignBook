package petosa.com.SignBook;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    // Increment database version when updating schema
    public static final int DATABASE_VERSION = 36;
    public static final String DATABASE_NAME = "datab.db";

    /**
     * Create table for users
     */
    public static void initTable() {
        World.DB.execSQL("CREATE TABLE IF NOT EXISTS phrases (_id INTEGER PRIMARY KEY "
                + "AUTOINCREMENT, text TEXT NOT NULL)");
    }

    public static boolean addPhrase(String s) {
        ContentValues content = new ContentValues();
        content.put("text", s);
        long check = World.DB.insert("phrases", null, content);
        return (check != 0) ? true : false;
    }

    public static List<String> getAllPhrases() {
        initTable();
        ArrayList<String> temp = new ArrayList<>();
        String query = "SELECT * FROM phrases";
        Cursor cursor = World.DB.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndex("text"));
            temp.add(text);
        }
        return temp;
    }

}
