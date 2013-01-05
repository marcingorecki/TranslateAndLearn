package net.mgorecki.translateandlearn.service.translate;

public class TranslationProviderFactory {
	
	static TranslationProvider provider = null;

	public static TranslationProvider getTranslationProvider(){
		if(provider==null){
			provider = new SqliteTranslationProvider();
		}
		return provider;
	}
}
