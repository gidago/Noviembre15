/*
 *  https://www.udacity.com/course/viewer#!/c-ud258/l-3372188753/m-3383668991
 *  https://github.com/udacity/DictionaryProviderExample
 *  http://www.compiletimeerror.com/2013/12/content-provider-in-android.html
 *  http://code.tutsplus.com/tutorials/android-fundamentals-working-with-content-providers--mobile-5549
 *  http://www.mysamplecode.com/2012/11/android-database-content-provider.html
 *  http://www.grokkingandroid.com/android-tutorial-writing-your-own-content-provider/
 *  http://developer.android.com/intl/es/guide/topics/providers/content-provider-creating.html
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
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings.System;
import android.util.Log;

public class Provider extends ContentProvider {

  static final String TAG = "Provider";

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mOpenHelper;


    static final int WEATHER = 100;
    static final int WEATHER_WITH_LOCATION = 101;
    static final int WEATHER_WITH_LOCATION_AND_DATE = 102;



  static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.lab.acme.noviembre15.provider.status";
  static final String MULTIPLE_RECORDS_MIME_TYPE = "vnd.android.cursor.dir/vnd.lab.acme.noviembre15.provider.status";


    /*
        Students: Here is where you need to create the UriMatcher. This UriMatcher will
        match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
        and LOCATION integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FactsContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, FactsContract.PATH_FACTS, WEATHER);
        matcher.addURI(authority, FactsContract.PATH_FACTS + "/*", WEATHER_WITH_LOCATION);
        matcher.addURI(authority, FactsContract.PATH_FACTS + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);

        return matcher;
    }



  @Override
  public boolean onCreate() {
    Log.d(TAG, "onCreate");
  mOpenHelper = new DbHelper(getContext());
  return true;
  }

  @Override
  public String getType(Uri uri) {
    String ret = getContext().getContentResolver().getType(System.CONTENT_URI);
    Log.d(TAG, "getType returning: " + ret);
    return ret;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    Log.d(TAG, "insert uri: " + uri.toString());
    return null;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    Log.d(TAG, "update uri: " + uri.toString());
    return 0;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    Log.d(TAG, "delete uri: " + uri.toString());
    return 0;
  }
/**
  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {
    Log.d(TAG, "query with uri: " + uri.toString());
    return null;
  }*/
@Override
public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                    String sortOrder) {
    // Here's the switch statement that, given a URI, will determine what kind of request it is,
    // and query the database accordingly.
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
        // "weather/*/*"
       /* case WEATHER_WITH_LOCATION_AND_DATE:
        {
            retCursor = getWeatherByLocationSettingAndDate(uri, projection, sortOrder);
            break;
        }
        // "weather/*"
        case WEATHER_WITH_LOCATION: {
            retCursor = getWeatherByLocationSetting(uri, projection, sortOrder);
            break;
        }*/
        // "weather"
        case WEATHER: {
            retCursor = mOpenHelper.getReadableDatabase().query(
                    FactsContract.ColumnsEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
            break;
        }
        // "location"
    /*    case LOCATION: {
            retCursor = mOpenHelper.getReadableDatabase().query(
                    FactsContract.ColumnsEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
            break;
        }
*/
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
}
}