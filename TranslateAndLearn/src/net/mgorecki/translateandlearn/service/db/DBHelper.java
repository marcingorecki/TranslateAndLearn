package net.mgorecki.translateandlearn.service.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBHelper {

	private static final String DATABASE_FILE = Environment.getExternalStorageDirectory().getPath()+"/dictionary.db";
	private SQLiteDatabase database;

	public DBHelper() {
		database = SQLiteDatabase.openDatabase(DATABASE_FILE, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
	}

	public String getTranslation(String text) {
		String result = "Not Found";
		Cursor c = database.rawQuery("select title, body from dictionary where title=?", new String[] { text });
		if (c.moveToNext()) {
			result = c.getString(1);
		}
		return result;
	}

}
