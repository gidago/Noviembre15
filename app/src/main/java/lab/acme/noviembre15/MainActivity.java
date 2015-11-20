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
import java.util.ArrayList;
import java.util.List;
import lab.acme.noviembre15.adapters.FactsAdapter;
import lab.acme.noviembre15.common.Common;
import lab.acme.noviembre15.models.FactItem;
import lab.acme.noviembre15.provider.Provider;


public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * /*
     Declarar instancias globales

   -  private RecyclerView recycler;
   -  private RecyclerView.Adapter adapter;
   -  private RecyclerView.LayoutManager lManager;
     *
     * */
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<FactItem> mFactItemList;
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
        
        //
        //TODO - replace with cursor
        //
        mFactItemList  = new ArrayList<>();


        populateList();   // Carga datos en la lista a mostrar, desde la bd
addTestGuessList();  // Carga datos en la lista (no quedan en la bd)
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

        mAdapter = new FactsAdapter( mFactItemList, mContext);
        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector =
                new GestureDetector ( MainActivity.this, new GestureDetector.SimpleOnGestureListener()
                {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
         });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    // onTouchDrawer(recyclerView.getChildLayoutPosition(child));
                    // Snackbar.make(recyclerView, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Log.d(LOG_TAG, "** child ly. pos.  " + recyclerView.getChildLayoutPosition(child));
                    //startActivity(new Intent(mContext, DetailActivity.class));
                 	Intent intentDetail = new Intent(mContext, DetailActivity.class);
                	intentDetail.putExtra("ID", recyclerView.getChildLayoutPosition(child)); // Pasamos la posición del elemento de la lista
                	startActivity(intentDetail);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                //  Snackbar.make(this, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void populateList() {
        // test
        Uri allTitles = Uri.parse("content://lab.acme.noviembre15/facts");
        Cursor cursor = managedQuery(allTitles, null, null, null, "date desc");
        if (cursor.moveToFirst()) {
            do {
                FactItem guest = new FactItem();
                guest.setDate(cursor.getString(cursor.getColumnIndex(Provider.COLUMN_DATE)));
                guest.setTitle(cursor.getString(cursor.getColumnIndex(Provider.COLUMN_FACT)));

                switch (cursor.getInt(cursor.getColumnIndex(Provider.COLUMN_CATEGORY_ID))) {

                    case (1):
                        guest.setProfileImageId(R.drawable.category_1);
                        break;
                    case (2):
                        guest.setProfileImageId(R.drawable.category_2);
                        break;
                    case (3):
                        guest.setProfileImageId(R.drawable.category_3);
                        break;
                    default:
                        guest.setProfileImageId(R.drawable.headshot_9);
                        break;
                }
                mFactItemList.add(guest);
                Log.d(LOG_TAG, "** Items.  " + cursor.getString(cursor.getColumnIndex(Provider.COLUMN_ID)));
            } while (cursor.moveToNext());
        }
    }

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
        values.put(Provider.COLUMN_DATE, "25 enero 2015");
        values.put(Provider.COLUMN_CATEGORY_ID, 1);
        values.put(Provider.COLUMN_VALUE, 20);
        values.put(Provider.COLUMN_COORD_LAT, 20.40);
        values.put(Provider.COLUMN_COORD_LONG, 50.40);
        values.put(Provider.COLUMN_TITLE, "Walking Out the Door");
        values.put(Provider.COLUMN_FACT, "John Doe write Walking Out the Door");
        values.put(Provider.COLUMN_CATEGORY, "Pruebas");
        Uri uri = getContentResolver().insert(Provider.CONTENT_URI, values);

        // add another row (reg)
        values.clear();
        values.put(Provider.COLUMN_CATEGORY_ID, 2);
        values.put(Provider.COLUMN_VALUE, 320);
        values.put(Provider.COLUMN_COORD_LAT, 20.40);
        values.put(Provider.COLUMN_COORD_LONG, 50.40);
        values.put(Provider.COLUMN_DATE, "25 enero 2015");
        values.put(Provider.COLUMN_TITLE, "Should we fight back");
        values.put(Provider.COLUMN_FACT, "The Parlotones sing Should we fight back");
        values.put(Provider.COLUMN_CATEGORY, "Pruebas");
        uri = getContentResolver().insert(Provider.CONTENT_URI, values);

        values.clear();
        values.put(Provider.COLUMN_CATEGORY_ID, 3);
        values.put(Provider.COLUMN_VALUE, 320);
        values.put(Provider.COLUMN_COORD_LAT, 20.40);
        values.put(Provider.COLUMN_COORD_LONG, 50.40);
        values.put(Provider.COLUMN_DATE, "12 noviembre 2015");
        values.put(Provider.COLUMN_TITLE, "Informes parcial");
        values.put(Provider.COLUMN_FACT, "Texto largo de Informes parcial");
        values.put(Provider.COLUMN_CATEGORY, "Test");
        //uri = getContentResolver().insert(Provider.CONTENT_URI, values);
        getContentResolver().insert(Provider.CONTENT_URI, values);

        for (int i = 0; i < 5; i++) {
       // int i = 0;
            values.clear();
            values.put(Provider.COLUMN_CATEGORY_ID, i);
            values.put(Provider.COLUMN_VALUE, 320 + i);
            values.put(Provider.COLUMN_COORD_LAT, 20.40);
            values.put(Provider.COLUMN_COORD_LONG, 50.40);
            values.put(Provider.COLUMN_DATE, "12 noviembre 2015");
            values.put(Provider.COLUMN_TITLE, "Titulo " + i);
            values.put(Provider.COLUMN_FACT, "Texto largo de Informes parcial " + i);
            values.put(Provider.COLUMN_CATEGORY, "Test");
            getContentResolver().insert(Provider.CONTENT_URI, values);
        }

        // test
        Uri allTitles = Uri.parse("content://lab.acme.noviembre15/facts");
        Cursor c = managedQuery(allTitles, null, null, null, "date desc");
        if (c.moveToFirst()) {
            do {
               /* Toast.makeText(
                        this,
                        c.getString(c.getColumnIndex(Provider.COLUMN_DATE))
                                + ", \""
                                + c.getString(c.getColumnIndex(Provider.COLUMN_FACT))
                                + "\"",
                        Toast.LENGTH_LONG).show();*/
                String mReg = c.getString(c.getColumnIndex(Provider.COLUMN_DATE))
                        + ", \""
                        + c.getString(c.getColumnIndex(Provider.COLUMN_FACT))
                        + "\"";
                Log.i(LOG_TAG, mReg);
            } while (c.moveToNext());
        }

        // update
        ContentValues editedValues = new ContentValues();
        editedValues.put(Provider.COLUMN_TITLE, "Should We Fight Back?");
        getContentResolver().update(
                Uri.parse("content://lab.acme.noviembre15/facts/2"), editedValues, null, null);

  /**      // delete
        getContentResolver().delete(
                Uri.parse("content://lab.acme.noviembre15/facts/2"),
                null, null);

        // delete all
        getContentResolver().delete(
                Uri.parse("content://lab.acme.noviembre15/facts"), null,
                null);*/

    }

    private void addTestGuessList() {

        FactItem  guest1 = new FactItem();
        guest1.setDate("12 noviembre 2015");
        guest1.setTitle("Informes parcial");
	    guest1.setFact("Texto largo de Informes parcial");
	    guest1.setCategory("Test");
        guest1.setProfileImageId(R.drawable.category_3);
        mFactItemList.add(guest1);

        /**
        values.put(Provider.COLUMN_DATE, "25 enero 2015");
        values.put(Provider.COLUMN_CATEGORY_ID, 1);
        values.put(Provider.COLUMN_VALUE, 20);
        values.put(Provider.COLUMN_COORD_LAT, 20.40);
        values.put(Provider.COLUMN_COORD_LONG, 50.40);
        values.put(Provider.COLUMN_TITLE, "Walking Out the Door");
        values.put(Provider.COLUMN_FACT, "John Doe write Walking Out the Door");
        values.put(Provider.COLUMN_CATEGORY, "Pruebas");
        * */


        FactItem  guest2 = new FactItem();
        guest2.setDate("24 diciembre 2015");
        guest2.setTitle("Navidad");
	    guest2.setCategory("Test");
	    
        guest2.setProfileImageId(R.drawable.headshot_2);
        mFactItemList.add(guest2);

        FactItem  guest3 = new FactItem();
        guest3.setDate("28 octubre 2015");
        guest3.setTitle("Viaje a Venecia");
        guest3.setCategory("Test");
        guest3.setProfileImageId(R.drawable.category_1);
        mFactItemList.add(guest3);

        FactItem  guest4 = new FactItem();
        guest4.setDate("01 enero 2016");
        guest4.setTitle("La gran decisión");
        guest4.setCategory("Test");
        guest4.setProfileImageId(R.drawable.no_category);
        mFactItemList.add(guest4);

        FactItem  guest5 = new FactItem();
        guest5.setDate("01 mayo 2016");
        guest5.setTitle("Una actividad");
        guest5.setProfileImageId(R.drawable.headshot_5);
        mFactItemList.add(guest5);

        FactItem  guest6 = new FactItem();
        guest6.setDate("22 mayo 2016");
        guest6.setTitle("Cualquier hecho");
        guest6.setProfileImageId(R.drawable.category_2);
        mFactItemList.add(guest6);

        FactItem  guest7 = new FactItem();
        guest7.setDate("20 marzo 2014");
        guest7.setTitle("Autoescuela");
        guest7.setProfileImageId(R.drawable.headshot_7);
        mFactItemList.add(guest7);

        FactItem  guest8 = new FactItem();
        guest8.setDate("21 agosto 2016");
        guest8.setTitle("Dentista");
        guest8.setProfileImageId(R.drawable.headshot_8);
        mFactItemList.add(guest8);

        FactItem  guest9 = new FactItem();
        guest9.setDate("11 noviembre 2016");
        guest9.setTitle("Regalos");
        guest9.setProfileImageId(R.drawable.headshot_9);
        mFactItemList.add(guest9);

        FactItem  guest10 = new FactItem();
        guest10.setDate("01 junio 2016");
        guest10.setTitle("Patines");
        guest10.setProfileImageId(R.drawable.headshot_10);
        mFactItemList.add(guest10);
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
//REFS:
// http://valokafor.com/create-and-publish-your-first-android-app-part-2/
