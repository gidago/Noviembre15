package lab.acme.noviembre15;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import lab.acme.noviembre15.adapters.MyListCursorAdapter;
import lab.acme.noviembre15.common.Common;
import lab.acme.noviembre15.provider.Provider;

//REFS:
// http://valokafor.com/create-and-publish-your-first-android-app-part-2/


public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private MyListCursorAdapter myListCursorAdapter;


    // Changed by a Cursor private List<FactItem> mFactItemList;

    private Context mContext;
    private Activity activity;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        activity = MainActivity.this;
        mContext = MainActivity.this;

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

		mRecyclerView = (RecyclerView) findViewById(R.id.facts_main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //promptForAdd();  TODO - ¿Pedir confirmación?
                Intent activityAdd;
                activityAdd = new Intent(mContext, AddFactActivity.class);
                startActivity(activityAdd);
            }
        });

        //mAdapter =  new MyListCursorAdapter(mContext, cursorFacts());
        myListCursorAdapter=  new MyListCursorAdapter(mContext, cursorFacts());
        //TODO añadido listener
        //mAdapter.setOnClickListener(new View.OnClickListener() {
        myListCursorAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DemoRecView", "Pulsado el elemento " + mRecyclerView.getChildPosition(v));
                //Intent intentDetail = new Intent(mContext, DetailActivity.class);
                //intentDetail.putExtra("ID", recyclerView.getChildLayoutPosition(child)); // Pasamos la posición del elemento de la lista
                //startActivity(intentDetail);
            }
        });
        
       // mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAdapter(myListCursorAdapter);

		// TODO - comandos para el adaptador
		// adaptador.notifyItemInserted(1);
		// adaptador.notifyItemRemoved(1);
		//
		// mAdapter.notifyItemInserted(1);
		// ....

      final GestureDetector mGestureDetector =
                new GestureDetector ( MainActivity.this, new GestureDetector.SimpleOnGestureListener()
                {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
         });
         
		//TODO revisar por ser añadido listener
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
			//TODO revisar por ser añadido listener
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    // onTouchDrawer(recyclerView.getChildLayoutPosition(child));
                    // Snackbar.make(recyclerView, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Log.e(LOG_TAG, "** child ly. pos.  " + recyclerView.getChildLayoutPosition(child));
                    //startActivity(new Intent(mContext, DetailActivity.class));
                 	Intent intentDetail = new Intent(mContext, DetailActivity.class);
                	intentDetail.putExtra("ID", recyclerView.getChildLayoutPosition(child)); // Pasamos la posición del elemento de la lista
                	startActivity(intentDetail);
                    return true;
                }
                return false;
            }
			//TODO revisar por ser añadido listener
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                //  Snackbar.make(this, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
			//TODO revisar por ser añadido listener
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    /**
     * 	Cursor with complete DB
     */
    private Cursor cursorFacts(){
        Uri allTitles = Uri.parse("content://lab.acme.noviembre15/facts");
        //Cursor cursor = managedQuery(allTitles, null, null, null, "title asc");
        Cursor cursor = managedQuery(allTitles, null, null, null, null);
        Log.d(LOG_TAG, "==================>>>>>>>>>>>Registros del cursor: " + cursor.getCount());
        return cursor;
    }

	//
	// Delete all rows of db
	//
    private void deleteAllRowsTestBD() {
        getContentResolver().delete(
                Uri.parse("content://lab.acme.noviembre15/facts"), null,
                null);

    }

	//
	// Add rows to db
	//
	private void addTestBD() {
    	String mMsg = "ILG PRUEBAS BD: " +"<------------------------->";
        Log.d(LOG_TAG, mMsg);
        // add a row (reg)
		ContentValues values = new ContentValues();      	
		for (int i = 0; i < 5; i++) {
			values.clear();
            values.put(Provider.COLUMN_CATEGORY_ID, i);
            values.put(Provider.COLUMN_VALUE, 320 + i);
            values.put(Provider.COLUMN_COORD_LAT, 20.40);
            values.put(Provider.COLUMN_COORD_LONG, 50.40);
            values.put(Provider.COLUMN_DATE, (i+1) + " noviembre 2015 " );
            values.put(Provider.COLUMN_TITLE,  (i+1) +  " Registro añadido" );
            values.put(Provider.COLUMN_FACT, (i+1) + "Texto largo de Informes parcial ");
            values.put(Provider.COLUMN_CATEGORY, "Test");
            getContentResolver().insert(Provider.CONTENT_URI, values);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Set an icon in the ActionBar
       // menu.findItem(R.id.lab).setIcon(R.drawable.ic_image_arrow);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String mMsg = "Method onOptionsItemSelected";
        Log.i(LOG_TAG, mMsg);

        int id = item.getItemId();

        switch (id) {

            case (R.id.action_app_version):
                Common.showAlertDialog(this, getString(R.string.app_name), "Your application version is: " + Common.getAppVersionCode(mContext) + ".", false);
                break;
            case (R.id.action_app_icon):
                Intent activityAppIcon;
                activityAppIcon = new Intent(this,  AppIconAct.class);
                startActivity(activityAppIcon);
                break;
            case (R.id.action_bd_copy):
                micopia();
                break;
            case (R.id.action_populate_db):
                addTestBD();
                break;
            case (R.id.action_db_delete_all):
                deleteAllRowsTestBD();
                break;
            case (R.id.lab):
                Intent activityPis;
                activityPis = new Intent(this, PisActivity.class);
                startActivity(activityPis);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //
    // Copy db from local storage to public storage
    //
    private void micopia() {

        String[] dbList =  {"facts"};
        // TODO - revisar fallo
        if ( checkDatabase("facts.db"))
            Toast.makeText(getBaseContext(), "O.k. db", Toast.LENGTH_LONG).show();

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            Log.e(LOG_TAG, "1:  " + sd.toString());
            Log.e(LOG_TAG, "2:  " + data.toString());
            if (sd.canWrite()) {
                String currentDBPath = "//data//"+ "lab.acme.noviembre15" +"//databases//"+  dbList[0];  //
                String backupDBPath = dbList[0];  //
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                Log.e(LOG_TAG, "3:   " +  currentDB.toString());
                Log.e(LOG_TAG, "4:   " +  backupDB.toString());
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Checks if the database exists at the application's data directory
     *
     * @param dbName Name of the database to check the existence of
     * @return True if the database exists, false if not
     */
    private boolean checkDatabase(String dbName) {
        File dbFile = new File(this.activity.getApplicationInfo().dataDir + "/" + dbName);
        return dbFile.exists();
    }

}
