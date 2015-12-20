package lab.acme.noviembre15;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import lab.acme.noviembre15.adapters.MyListCursorAdapter;
import lab.acme.noviembre15.common.Common;
import lab.acme.noviembre15.provider.FactsContract;
import lab.acme.noviembre15.provider.Provider;




public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView.LayoutManager mLayoutManager;
    private MyListCursorAdapter myListCursorAdapter;
    private Context mContext;
    private Activity activity;

    public static final String QUERY_KEY = "query";

    //LOADER
    private static final int FACTS_LOADER = 0;

    private static final String[] FACTS_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            FactsContract.FactsEntry.COLUMN_ID,
            FactsContract.FactsEntry.COLUMN_DATE,
            FactsContract.FactsEntry.COLUMN_TITLE,
            FactsContract.FactsEntry.COLUMN_CATEGORY,
            FactsContract.FactsEntry.COLUMN_CATEGORY_ID,
            FactsContract.FactsEntry.COLUMN_FACT,
            FactsContract.FactsEntry.COLUMN_VALUE,
            FactsContract.FactsEntry.COLUMN_COORD_LAT,
            FactsContract.FactsEntry.COLUMN_COORD_LONG
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_FACTS_ID = 0;
    static final int COL_FACTS_DATE = 1;
    static final int COL_FACTS_TITLE = 2;
    static final int COL_FACTS_CATEGORY = 3;
    static final int COL_FACTS_CATEGORY_ID = 4;
    static final int COL_FACTS_FACT = 5;
    static final int COL_FACTS_VALUE = 6;
    static final int COL_FACTS_COORD_LAT = 7;
    static final int COL_FACTS_COORD_LONG = 8;



    //TODO-search
    // Menu items
    private MenuItem searchMenuItem;

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p/>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p/>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(Uri dateUri);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        activity = MainActivity.this;
        mContext = MainActivity.this;
        setTitle("Memory");
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.facts_main_recycler_view);
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

        myListCursorAdapter=  new MyListCursorAdapter(mContext, cursorFacts());
        mRecyclerView.setAdapter(myListCursorAdapter);

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
                 	Intent intentDetail = new Intent(mContext, DetailActivity.class);
                	intentDetail.putExtra("ID", recyclerView.getChildLayoutPosition(child)); // Pasamos la posición del elemento de la lista
                	startActivity(intentDetail);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        if (getIntent() != null) {
            handleIntent(getIntent());
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    /**
     * Assuming this activity was started with a new intent, process the incoming information and
     * react accordingly.
     * @param intent
     */
    private void handleIntent(Intent intent) {
        Log.e("SEARCHVIEWMAIN", "handleIntent");
        // Special processing of the incoming intent only occurs if the if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // SearchManager.QUERY is the key that a SearchManager will use to send a query string
            // to an Activity.
            String query = intent.getStringExtra(SearchManager.QUERY);

            // We need to create a bundle containing the query string to send along to the
            // LoaderManager, which will be handling querying the database and returning results.
            Bundle bundle = new Bundle();
            bundle.putString(QUERY_KEY, query);

          /** ContactablesLoaderCallbacks loaderCallbacks = new ContactablesLoaderCallbacks(this);

            // Start the loader with the new query, and an object that will handle all callbacks.
            getLoaderManager().restartLoader(CONTACT_QUERY_LOADER, bundle, loaderCallbacks);*/
        }
    }

    /**
     * 	Cursor with complete DB
     */
	private Cursor cursorFacts(){
        Uri allTitles = Uri.parse("content://lab.acme.noviembre15/facts");              
		//Cursor cursor = managedQuery(allTitles, null, null, null, "_id asc");
        // TODO - Validar
        Cursor cursor = managedQuery(allTitles, null, null, null, FactsContract.FactsEntry.DEFAULT_SORT_ORDER);
        //Cursor cursor = managedQuery(allTitles, null, null, null, null);
        //Log.e(LOG_TAG, "==================>>>>>>>>>>> Registros del cursor: " + cursor.getCount());
        return cursor;
    }

	//
	// Delete all rows of db
	//
    private void deleteAllRowsTestBD() {
    	getContentResolver().delete(
        	Uri.parse("content://lab.acme.noviembre15/facts"), null, null);
        myListCursorAdapter.notifyDataSetChanged();
        mLayoutManager.removeAllViews();
    }

	//
	// Add rows to db
	//
	private void addTestBD() {
		String mMsg = "ILG PRUEBAS BD: " +"<------------------------->";
        Log.e(LOG_TAG, mMsg);
        // add a row (reg)
		ContentValues values = new ContentValues();      	
		for (int i = 0; i < 5; i++) {
			values.clear();
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, i);
            values.put(FactsContract.FactsEntry.COLUMN_VALUE, 320 + i);
            values.put(FactsContract.FactsEntry.COLUMN_COORD_LAT,  20.40);
            values.put(FactsContract.FactsEntry.COLUMN_COORD_LONG, 50.40);
            values.put(FactsContract.FactsEntry.COLUMN_DATE,   (i+1) + "/12/2015 " );
            values.put(FactsContract.FactsEntry.COLUMN_TITLE,  (i+1) +  " Registro añadido" );
            values.put(FactsContract.FactsEntry.COLUMN_FACT,   (i+1) + "Texto largo de Informes parcial ");
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY, "Test");
            getContentResolver().insert(Provider.CONTENT_URI, values);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Log.e(LOG_TAG, "----------- onCreateOptionsMenu --------------------------xxxxxxxxx-------------------");
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();


        Log.e(LOG_TAG,  searchView.toString());
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        return true;



        /**

         https://github.com/danbrough/Android-SearchView-Demo/blob/master/src/danbroid/searchview/MainActivity.java
         searchMenuItem = menu.add(android.R.string.search_go);




         searchItem = menu.add(android.R.string.search_go);

         searchItem.setIcon(R.drawable.ic_search_white_36dp);
         MenuItemCompat.setActionView(searchItem, searchView);
         MenuItemCompat.setShowAsAction(searchItem,
         MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
         menu.add(0, R.id.menu_about, 0, R.string.lbl_about);
         return super.onCreateOptionsMenu(menu);

        */

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
