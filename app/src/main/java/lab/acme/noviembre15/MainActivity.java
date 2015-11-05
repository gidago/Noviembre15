package lab.acme.noviembre15;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import lab.acme.noviembre15.common.Common;


public class MainActivity extends AppCompatActivity {


	//private TextView versionCodeLabel;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = MainActivity.this;

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        
        //int intVersionCode = Common.getDeviceId(mContext);

        //--- text view---
      	TextView versionCodeLabel = (TextView) findViewById(R.id.text_id);

      	String msg = "Device ID: " + Common.getDeviceId(mContext);
      
      	// Display the Version Code in the UI	
    	versionCodeLabel.setText(msg);
      	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Common.showAlertDialog(this, getString(R.string.app_name), "Your application version is: " + Common.getAppVersionCode(mContext) + ".", false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
