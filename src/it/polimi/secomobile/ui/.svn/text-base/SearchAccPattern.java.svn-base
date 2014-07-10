package it.polimi.secomobile.ui;

import it.polimi.secomobile.data.Keys;
import it.polimi.secomobile.data.Services;

import java.util.Arrays;

import android.os.Bundle;
import android.widget.TextView;

public class SearchAccPattern extends Search {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        TextView tv1 = (TextView)findViewById(R.id.SearchMenuTitle);
        tv1.setText(getIntent().getBundleExtra(Keys.QUERY_INFO.toString())
        						.getString(Keys.SELECTED_MART_NAME.toString()));
        TextView tv2 = (TextView)findViewById(R.id.SearchMenuSubtitle);
        tv2.setText("Choose other properties");
        
		String serviceMartId = getIntent().getStringExtra(Keys.MART_ID.toString());
		serviceIdlist = Arrays.asList(serviceMartId);
        new SeCoLoader().execute(Services.ACC_PATTERN);
    }
}
