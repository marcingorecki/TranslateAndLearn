package net.mgorecki.translateandlearn.service.translate;

import net.mgorecki.translateandlearn.service.db.DBHelper;

public class SqliteTranslationProvider implements TranslationProvider {

	DBHelper dbHelper = new DBHelper();
	
	@Override
	public String translate(String text, String langFrom, String langTo) throws TranslationException {
		
		return dbHelper.getTranslation(text);
	}

}
