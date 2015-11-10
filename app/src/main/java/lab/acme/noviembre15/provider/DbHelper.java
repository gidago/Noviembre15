package lab.acme.noviembre15.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	
 // If you change the database schema, you must increment the database version.
public static final int DB_VERSION = 1;
public static final String DB_NAME = "facts.db";	
	
	
// TODO - Decidir si esta definicon debe ir aqui o en CONTRACTS
public static final String TABLE = "myfacts";

// TODO - Renombrar DbHelper a FactsDbHelper
    public DbHelper(Context context) {
    	super(context, DB_NAME, null, DB_VERSION);
    }
          
//TODO: Set real columns from FACTS
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// Create a table to hold facts. 
    	final String SQL_CREATE_FACTS_TABLE = "CREATE TABLE " + Columns.TABLE_NAME + " (" +
				Columns._ID + " INTEGER PRIMARY KEY," +
				Columns.COLUMN_DATE + " TEXT UNIQUE NOT NULL, " +
				Columns.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
				Columns.COLUMN_COORD_LAT + " REAL NOT NULL, " +
				Columns.COLUMN_COORD_LONG + " REAL NOT NULL " +
		" );";
    	
    	
// TODO: Continuar aqui ----------------------------->>

    	
 		//String sql = String.format("create table %s ( %s INT PRIMARY KEY,"
            //            + "%s INT, %s TEXT, %s TEXT);", TABLE,
           //             StatusContract.Columns._ID, StatusContract.Columns.CREATED_AT,
           //             StatusContract.Columns.USER, StatusContract.Columns.MESSAGE);
      //  Log.d("DbHelper", "sql: " + sql);
      //  db.execSQL(sql);
      
      sqLiteDatabase.execSQL(SQL_CREATE_FACTS_TABLE);
    }


 @Override
public void onCreate(SQLiteDatabase sqLiteDatabase) {
// Create a table to hold locations. A location consists of the string supplied in the
// location setting, the city name, and the latitude and longitude
final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
LocationEntry._ID + " INTEGER PRIMARY KEY," +
LocationEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL, " +
LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL " +
" );";





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Temporary for development purposes
        db.execSQL("drop table if exists "+ TABLE);
        onCreate(db);
    }
}
