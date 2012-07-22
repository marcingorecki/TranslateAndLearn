package net.mgorecki.translateandlearn.service;

public interface TranslationProvider {
	String translate(String text, String langFrom, String langTo) throws TranslationException;
}
