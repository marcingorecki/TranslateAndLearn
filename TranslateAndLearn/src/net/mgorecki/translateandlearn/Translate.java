package net.mgorecki.translateandlearn;


import net.mgorecki.translateandlearn.service.TranslationException;
import net.mgorecki.translateandlearn.service.TranslationProvider;
import net.mgorecki.translateandlearn.service.TranslationProviderFactory;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class Translate extends Activity {

	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_translate, menu);
        return true;
    }

    public void onTranslateClick(View view){
    	hideKeyboard();
    	TranslationProvider provider = TranslationProviderFactory.getTranslationProvider();
    	EditText editFrom = (EditText) findViewById(R.id.editFrom);
    	TextView editTo = (TextView) findViewById(R.id.viewTextTo);
    	
    	try {
			String result = provider.translate(editFrom.getText().toString(), "en", "pl");
			editTo.setText(result);
		} catch (TranslationException e) {
			e.printStackTrace();
			editTo.setText(e.getLocalizedMessage());
		}
    	
    }
    
}
