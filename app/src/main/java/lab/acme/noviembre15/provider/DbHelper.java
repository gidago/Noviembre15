package lab.acme.noviembre15.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lab.acme.noviembre15.provider.FactsContract.ColumnsEntry;
// TODO - ¿en donde se utiliza?
/**
 * Manages a local database.
 */
public class DbHelper extends SQLiteOpenHelper {

     // If you change the database schema, you must increment the database version.
    private static final int DB_VERSION = 1;

    public static final String DB_NAME = "facts.db";
	
	
// TODO - Decidir si esta definicon debe ir aqui o en CONTRACTS
//public static final String TABLE = "myfacts";

// TODO - Renombrar DbHelper a FactsDbHelper
    public DbHelper(Context context) {
    	super(context, DB_NAME, null, DB_VERSION);
    }
          
//TODO: Set real columns from FACTS
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	// Create a table to hold facts. 
    	final String SQL_CREATE_FACTS_TABLE = "CREATE TABLE " + ColumnsEntry.TABLE_NAME + " (" +
				ColumnsEntry._ID + " INTEGER UNIQUE PRIMARY KEY," +
				ColumnsEntry.COLUMN_DATE + " TEXT NOT NULL, " +
				ColumnsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
				ColumnsEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                ColumnsEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
				ColumnsEntry.COLUMN_FACT + " TEXT NOT NULL, " +
				ColumnsEntry.COLUMN_VALUE + " REAL NOT NULL, " +
				ColumnsEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
				ColumnsEntry.COLUMN_COORD_LONG + " REAL NOT NULL " +
		" );";
        db.execSQL(SQL_CREATE_FACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Temporary for development purposes
        db.execSQL("drop table if exists "+ FactsContract.ColumnsEntry.TABLE_NAME);
        onCreate(db);
    }
}
