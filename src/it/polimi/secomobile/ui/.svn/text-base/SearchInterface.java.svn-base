package it.polimi.secomobile.ui;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.Services;

import java.util.Arrays;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

public class SearchInterface extends Search {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        TextView tv1 = (TextView)findViewById(R.id.SearchMenuTitle);
        tv1.setText(getIntent().getBundleExtra(Keys.QUERY_INFO.toString())
        						.getString(Keys.SELECTED_AP_NAME.toString()));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
        TextView tv2 = (TextView)findViewById(R.id.SearchMenuSubtitle);
        tv2.setText("Choose a service");
        
		String accPatternId = getIntent().getStringExtra(Keys.ACCESS_PATTERN_ID.toString());
		serviceIdlist = Arrays.asList(accPatternId);
        new SeCoLoader().execute(Services.INTERFACE);
    }
}
