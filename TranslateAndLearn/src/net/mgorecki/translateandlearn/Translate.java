package net.mgorecki.translateandlearn;


import net.mgorecki.translateandlearn.service.BingTranslationProvider;
import net.mgorecki.translateandlearn.service.TranslationException;
import net.mgorecki.translateandlearn.service.TranslationProvider;
import net.mgorecki.translateandlearn.service.TranslationProviderFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Translate extends Activity {

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
    	TranslationProvider provider = TranslationProviderFactory.getTranslationProvider();
    	EditText editFrom = (EditText) findViewById(R.id.editFrom);
    	EditText editTo = (EditText) findViewById(R.id.editTextTo);
    	
    	try {
			String result = provider.translate("Testing translation. Big brown fox", "en", "pl");
    		//String result = BingTranslationProvider.getAPIKey();
			editTo.setText(result);
		} catch (TranslationException e) {
			e.printStackTrace();
			editTo.setText(e.getLocalizedMessage());
		}
    	
    }
    
}
