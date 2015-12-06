package lab.acme.noviembre15.provider;

import android.provider.BaseColumns;
//TODO ¿En donde se utiliza?
public final class FactsContract {

    private FactsContract() {}
    // Todo: Elegir AUTHORITY vs. CONTENT_AUTHORITY
    /** The authority for the contacts provider */
    public static final String AUTHORITY = "lab.acme.noviembre15";
    public static final String CONTENT_AUTHORITY = "lab.acme.noviembre15";

    /**
     * Column definitions for facts information.
     */
    /* Inner class that defines the table contents of the facts table */
    public final static class FactsEntry implements BaseColumns {

        private FactsEntry() {}
        public static final String DATABASE_NAME = "facts";
        public static final int DATABASE_VERSION = 1;

 		// Table name
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
	}
}
