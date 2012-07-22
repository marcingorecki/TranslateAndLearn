package net.mgorecki.translateandlearn.service;

public class TranslationProviderFactory {
	
	static TranslationProvider provider = null;

	public static TranslationProvider getTranslationProvider(){
		if(provider==null){
			provider = new BingTranslationProvider();
		}
		return provider;
	}
}
