/*
 *  https://www.udacity.com/course/viewer#!/c-ud258/l-3372188753/m-3383668991
 *  https://github.com/udacity/DictionaryProviderExample
 *  http://www.compiletimeerror.com/2013/12/content-provider-in-android.html
 *  http://code.tutsplus.com/tutorials/android-fundamentals-working-with-content-providers--mobile-5549
 *  http://www.mysamplecode.com/2012/11/android-database-content-provider.html
 *  http://www.grokkingandroid.com/android-tutorial-writing-your-own-content-provider/
 *  http://developer.android.com/intl/es/guide/topics/providers/content-provider-creating.html
 *  https://threeheadedmonkeydev.wordpress.com/2011/10/20/introduccion-al-manejo-de-content-providers-en-android/
 *
 *  Implementing a content provider involves always the following steps:
 *
 * Create a class that extends ContentProvider
 * Create a contract class
 * Create the UriMatcher definition
 * Implement the onCreate() method
 * Implement the getType() method
 * Implement the CRUD methods
 * Add the content provider to your AndroidManifest.xml
 */

package lab.acme.noviembre15.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class Provider extends ContentProvider {

	static final String TAG = "Provider";

    public static final String PROVIDER_NAME = "lab.acme.noviembre15";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/facts");
    private static final int FACTS = 1;
    private static final int FACTS_ID = 2;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FactsContract.AUTHORITY, "facts", FACTS);
        uriMatcher.addURI(FactsContract.AUTHORITY, "facts/#", FACTS_ID);
    }
    // database stuff
    private SQLiteDatabase factsDB;
    // CONTRACT
    //private static final String DATABASE_NAME = "facts";
    //private static final String DATABASE_TABLE = "myfacts";
    //private static final int DATABASE_VERSION = 1;
    //public static final String COLUMN_ID = "_ID";
    //public static final String COLUMN_DATE = "date";
    //public static final String COLUMN_TITLE = "title";
    //public static final String COLUMN_CATEGORY = "category";
    //public static final String COLUMN_CATEGORY_ID = "category_id";
    //public static final String COLUMN_FACT = "fact";
    //public static final String COLUMN_VALUE = "value";
    //public static final String COLUMN_COORD_LAT = "coord_lat";
    //public static final String COLUMN_COORD_LONG = "coord_long";

    // Create a table to hold facts.
    private static final String DATABASE_CREATE = "CREATE TABLE " +
            FactsContract.FactsEntry.DATABASE_TABLE  + " (" +
            FactsContract.FactsEntry.COLUMN_ID + " INTEGER UNIQUE PRIMARY KEY," +
            FactsContract.FactsEntry.COLUMN_DATE + " TEXT , " +
            FactsContract.FactsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            FactsContract.FactsEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
            FactsContract.FactsEntry.COLUMN_CATEGORY_ID + " INTEGER , " +
            FactsContract.FactsEntry.COLUMN_FACT + " TEXT NOT NULL, " +
            FactsContract.FactsEntry.COLUMN_VALUE + " REAL , " +
            FactsContract.FactsEntry.COLUMN_COORD_LAT + " REAL , " +
            FactsContract.FactsEntry.COLUMN_COORD_LONG + " REAL  " +
            " );";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, FactsContract.FactsEntry.DATABASE_NAME, null, FactsContract.FactsEntry.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("CP db ", "Upgrading db from version: " );
            Log.d("  ....    " , oldVersion + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FactsContract.FactsEntry.DATABASE_TABLE);
            onCreate(db);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.content.ContentProvider#onCreate()
     */
    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        factsDB = dbHelper.getWritableDatabase();
        return (factsDB  == null) ? false : true;
      }

    /*
     * (non-Javadoc)
     *
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
      @Override
      public String getType(Uri uri) {
          String ret = getContext().getContentResolver().getType(CONTENT_URI);
          Log.e(TAG, "==============> Get URI type:   " + ret);
          switch (uriMatcher.match(uri)) {
              // get all facts
              case FACTS:
                  return "vnd.android.cursor.dir/lab.acme.noviembre15.facts";
              // get a particular fact
              case FACTS_ID:
                  return "vnd.android.cursor.item/lab.acme.noviembre15.facts";
              default:
                  throw new IllegalArgumentException("Unsupported URI: " + uri);
          }
      }

    /*
	 * (non-Javadoc)
	 *
	 * @see android.content.ContentProvider#insert(android.net.Uri,
	 * android.content.ContentValues)
	 */
      @Override
      public Uri insert(Uri uri, ContentValues values) {
        Log.e(TAG, "==================> Insert URI:    " + uri.toString());

          // add a new row
          long rowID = factsDB.insert(FactsContract.FactsEntry.DATABASE_TABLE, "", values);

          // if added successfully
          if (rowID > 0) {
              Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
              getContext().getContentResolver().notifyChange(_uri, null);
              return _uri;
          }
          throw new SQLException("Failed to insert row into " + uri);
      }

    /*
     * (non-Javadoc)
     *
     * @see android.content.ContentProvider#update(android.net.Uri,
     * android.content.ContentValues, java.lang.String, java.lang.String[])
     */
      @Override
      public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
          Log.d(TAG, "update uri: " + uri.toString());
          int count = 0;
          switch (uriMatcher.match(uri)) {
              case FACTS:
                  count = factsDB.update(FactsContract.FactsEntry.DATABASE_TABLE, values, selection,
                          selectionArgs);
                  break;
              case FACTS_ID:
                  count = factsDB.update(FactsContract.FactsEntry.DATABASE_TABLE, values, FactsContract.FactsEntry.COLUMN_ID
                          + " = "
                          + uri.getPathSegments().get(1)
                          + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                          + ')' : ""), selectionArgs);
                  break;
              default:
                  throw new IllegalArgumentException("Unknown URI " + uri);
          }
          getContext().getContentResolver().notifyChange(uri, null);
          //Log.d(TAG, "**** count: " + count);
          return count;
      }

    /**
      * (non-Javadoc)
      *
      * @see android.content.ContentProvider#delete(android.net.Uri,
      * java.lang.String, java.lang.String[])
      */
      @Override
      public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "**** Prueba: delete uri: " + uri.toString());
          int count = 0;
          switch (uriMatcher.match(uri)) {
              case FACTS:
                  count = factsDB.delete(FactsContract.FactsEntry.DATABASE_TABLE, selection, selectionArgs);
                  break;
              case FACTS_ID:
                  String id = uri.getPathSegments().get(1);
                  count = factsDB.delete(FactsContract.FactsEntry.DATABASE_TABLE, FactsContract.FactsEntry.COLUMN_ID
                          + " = "
                          + id
                          + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                          + ')' : ""), selectionArgs);
                  break;
              default:
                  throw new IllegalArgumentException("Unknown URI " + uri);
          }
          getContext().getContentResolver().notifyChange(uri, null);
          Log.e(TAG, "**** delete count: " + count);
          return count;
      }

    /**
     * (non-Javadoc)
     *
     * @see android.content.ContentProvider#query(android.net.Uri,
     * java.lang.String[], java.lang.String, java.lang.String[],
     * java.lang.String)
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {

        Log.e(TAG, "==============>   Query with URI:  " + uri.toString());

        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();

        sqlBuilder.setTables(FactsContract.FactsEntry.DATABASE_TABLE);

        if (uriMatcher.match(uri) == FACTS_ID)
            // if getting a particular row
            sqlBuilder.appendWhere(FactsContract.FactsEntry.COLUMN_ID + " = " + uri.getPathSegments().get(1));

        if (sortOrder == null || sortOrder == "")
            sortOrder = FactsContract.FactsEntry.COLUMN_DATE;

        Cursor cursor = sqlBuilder.query(factsDB, projection, selection,
                selectionArgs, null, null, sortOrder);

        // register to watch a content URI for changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
}