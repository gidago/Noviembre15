package lab.acme.noviembre15;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
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
import android.widget.TextView;
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

    private Context  mContext;
    private Activity activity;

    private RecyclerView.LayoutManager mLayoutManager;
    private MyListCursorAdapter myListCursorAdapter;
    private Cursor detailCursor;

    //LOADERS
    private static final int FACTS_LOADER = 0;
    private static final int SEARCH_LOADER = 1;

    public static final String QUERY_KEY = "query";

    private static final String[] FACTS_COLUMNS = {
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

    // These indices are tied to FACTS_COLUMNS.  If FACTS_COLUMNS changes, these
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
    //private MenuItem searchMenuItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = MainActivity.this;
        getSupportLoaderManager().initLoader(FACTS_LOADER, null, this);

		super.onCreate(savedInstanceState);
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
                Intent activityAdd;
                activityAdd = new Intent(mContext, AddFactActivity.class);
                startActivity(activityAdd);
            }
        });

        // LOADER
        myListCursorAdapter =  new MyListCursorAdapter(mContext, null);
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
                    // Element position
                	intentDetail.putExtra("ID", recyclerView.getChildLayoutPosition(child));
                    detailCursor.moveToPosition(recyclerView.getChildLayoutPosition(child));
                    intentDetail.putExtra("COL_FACTS_DATE",        detailCursor.getString(COL_FACTS_DATE));
                    intentDetail.putExtra("COL_FACTS_TITLE",       detailCursor.getString(COL_FACTS_TITLE));
                    intentDetail.putExtra("COL_FACTS_FACT",        detailCursor.getString(COL_FACTS_FACT));
                    intentDetail.putExtra("COL_FACTS_CATEGORY_ID", detailCursor.getInt(COL_FACTS_CATEGORY_ID));
                    intentDetail.putExtra("COL_FACTS_CATEGORY",    detailCursor.getString(COL_FACTS_CATEGORY));
                    intentDetail.putExtra("COL_FACTS_VALUE",       detailCursor.getString(COL_FACTS_VALUE));
                    intentDetail.putExtra("COL_FACTS_COORD_LAT",   detailCursor.getString(COL_FACTS_COORD_LAT));
                    intentDetail.putExtra("COL_FACTS_COORD_LONG",  detailCursor.getString(COL_FACTS_COORD_LONG));
                	startActivity(intentDetail);
                    Log.e(LOG_TAG,  "------------------------------------> after DETAIL intent");
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
       // setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Assuming this activity was started with a new intent, process the incoming information and
     * react accordingly.
     * @param intent
     */
    private void handleIntent(Intent intent) {
        Log.e("SEARCHVIEWMAIN", "-----------------------------> ** handleIntent **");
        // Special processing of the incoming intent only occurs if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // SearchManager.QUERY is the key that a SearchManager will use to send a query string
            // to an Activity.
            String query = intent.getStringExtra(SearchManager.QUERY);
            // We need to create a bundle containing the query string to send along to the
            // LoaderManager, which will be handling querying the database and returning results.
            Bundle bundle = new Bundle();
            bundle.putString(QUERY_KEY, query);
            // Start the loader with the new query, and an object that will handle all callbacks.
            getSupportLoaderManager().restartLoader(SEARCH_LOADER, bundle, this);
        }
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
            values.put(FactsContract.FactsEntry.COLUMN_DATE,   "10/12/2015" );
            values.put(FactsContract.FactsEntry.COLUMN_TITLE,  (i+1) +  " Registro añadido" );
            values.put(FactsContract.FactsEntry.COLUMN_FACT,   (i+1) + " Texto largo de Informes parcial ");
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

    //
    // CursorLoader
    //

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.
        switch (id) {
            case FACTS_LOADER:
                Uri allTitles = Uri.parse("content://" + FactsContract.AUTHORITY + "/facts");
                // public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/facts");
                return new CursorLoader(
                        activity,
                        allTitles,
                        FACTS_COLUMNS,
                        null,
                        null,
                        FactsContract.FactsEntry.DEFAULT_SORT_ORDER);
            case SEARCH_LOADER:
                // BEGIN_INCLUDE(uri_with_query)
                String query = args.getString(QUERY_KEY);
                Uri uriQuery = Uri.withAppendedPath(Uri.parse("content://" + FactsContract.AUTHORITY + "/facts"), query);
                // END_INCLUDE(uri_with_query)

                String queryLikeSelection = "'%" + query + "%'";

                Log.e(LOG_TAG, "SEARCH_LOADER ----->" + uriQuery.toString()
                        + " \n queryLikeSelection: " + queryLikeSelection );

                return new CursorLoader(
                    activity,
                    uriQuery,                    // URI representing the table/resource to be queried
                    FACTS_COLUMNS,               // projection - the list of columns to return.  Null means "all"
                    FactsContract.FactsEntry.COLUMN_DATE    + " LIKE " + queryLikeSelection + " OR " +
                            FactsContract.FactsEntry.COLUMN_TITLE   + " LIKE " + queryLikeSelection + " OR " +
                            FactsContract.FactsEntry.COLUMN_FACT    + " LIKE " + queryLikeSelection,   // selection - Which rows to return (condition rows must match)
                    null,                       // selection args - can be provided separately and subbed into selection.
                    FactsContract.FactsEntry.DEFAULT_SORT_ORDER);   // string specifying sort order
        }
        return null;
    }

    /**
     * Called when a previously created loader has finished its load.
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        detailCursor = data;

        Log.e(LOG_TAG, " 1 =======>   onLoadFinished:  "
                + "\n filas:  " + detailCursor.getCount());

        myListCursorAdapter.swapCursor(detailCursor);
        TextView mTotalCards = (TextView) findViewById(R.id.totalCards);
        mTotalCards.setText(myListCursorAdapter.getItemCount() + " tarjetas");
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
        myListCursorAdapter.swapCursor(null);
    }
}
