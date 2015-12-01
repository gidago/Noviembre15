package lab.acme.noviembre15.provider;

import android.provider.BaseColumns;
//TODO ¿En donde se utiliza?
public final class FactsContract {

    private FactsContract() {}
    // Todo: Elegir AUTHORITY vs. CONTENT_AUTHORITY
    /** The authority for the contacts provider */
    public static final String AUTHORITY = "lab.acme.noviembre15";
    public static final String CONTENT_AUTHORITY = "lab.acme.noviembre15";
    /** A content:// style uri to the authority for this table */
    //public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY );

    /**
     * Column definitions for facts information.
     */
    /* Inner class that defines the table contents of the facts table */
    public final static class FactsEntry implements BaseColumns {
		/*public static final Uri CONTENT_URI =
        	BASE_CONTENT_URI.buildUpon().appendPath(PATH_FACTS).build();*/
        /*public static final String CONTENT_TYPE =
        	ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FACTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FACTS;*/
        private FactsEntry() {}
        public static final String DATABASE_NAME = "facts";
        public static final int DATABASE_VERSION = 1;
 		// Table name
		public static final String TABLE_NAME = "myfacts";
    	public static final String DATABASE_TABLE = "myfacts";

   		public static final String COLUMN_ID = "_ID";

	    /*** date */
	    public static final String COLUMN_DATE = "date";

	    /*** title - Short description */	     	     
	    public static final String COLUMN_TITLE = "title";

	    /*** category - Indeed categorization */	     	     
	    public static final String COLUMN_CATEGORY = "category";

	    /*** category_id - Numerical identification of the categories */
	    public static final String COLUMN_CATEGORY_ID = "category_id";

	    /*** fact - Detailed description of the fact */	     	     
	    public static final String COLUMN_FACT = "fact";

	    /*** value - Quantifying the economic fact */    	     
	    public static final String COLUMN_VALUE = "value";

	    /*** Geografic location of FACT */	     	     
	    public static final String COLUMN_COORD_LAT = "coord_lat";
		public static final String COLUMN_COORD_LONG = "coord_long";

	    /*** The default sort order for this table */	     	     
	    public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
	    
	    
	    
		//TODO - for study  
	    public static final String CREATE_TABLE = "CREATE TABLE " 
	    					+ TABLE_NAME + " ("
							+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
							+ TRACKID + " INTEGER, "
							+ LONGITUDE + " INTEGER, "
							+ LATITUDE + " INTEGER, "
							+ TIME + " INTEGER, "
							+ ALTITUDE + " FLOAT, "
							+ ACCURACY + " FLOAT, "
							+ SPEED + " FLOAT, "
							+ BEARING + " FLOAT, "
							+ SENSOR + " BLOB"
							+ ");";
		public static final String[] COLUMNS = {
			_ID,
			TRACKID,
			LONGITUDE,
			LATITUDE,
			TIME,
			ALTITUDE,
			ACCURACY,
			SPEED,
			BEARING,
			SENSOR
		};
		public static final byte[] COLUMN_TYPES = {
			LONG_TYPE_ID, // id
			LONG_TYPE_ID, // track id
			INT_TYPE_ID, // longitude
			INT_TYPE_ID, // latitude
			LONG_TYPE_ID, // time
			FLOAT_TYPE_ID, // altitude
			FLOAT_TYPE_ID, // accuracy
			FLOAT_TYPE_ID, // speed
			FLOAT_TYPE_ID, // bearing
			BLOB_TYPE_ID // sensor
		};
	    
	    	    
	}
}
