package lab.acme.noviembre15.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class FactsContract {

    private FactsContract() {}

    /** The authority for the contacts provider */
    public static final String AUTHORITY = "com.marakana.android.yamba.provider";

    /** A content:// style uri to the authority for this table */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/status");

    /** The MIME type of {@link #CONTENT_URI} providing a directory of status messages. */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.marakana.status";

    /** The MIME type of a {@link #CONTENT_URI} a single status message. */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.marakana.status";

    /**
     * Column definitions for facts information.
     */
    public final static class Columns implements BaseColumns {
	private Columns() {}

 	// Table name
	public static final String TABLE_NAME = "myFacts";

        /**
         * The date of fact
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_DATE = "date";

        /**
         * Short description
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_TITLE = "title";
        
        /**
         * Indeed categorization
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_CATEGORY = "category";
        
        /**
         * Numerical identification of the categories
         * <P>Type: INT</P>
         */
        public static final String COLUMN_CATEGORY_ID = "category_id";
        
        /**
         * Detailed description of the fact
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_FACT = "fact";
        
        /**
         * Quantifying the economic fact
         * <P>Type: INT</P>
         */
        public static final String COLUMN_VALUE = "value";
        
        //TODO: Decide whether a field with LAT LONG + or two fields
        /**
         * Geografic location of FACT
         * <P>Type: INT</P>
         */
        //public static final String COLUMN_LOCATION = "location";
 	// In order to uniquely pinpoint the location on the map when we launch the
	// map intent, we store the latitude and longitude as returned by openweathermap.
	public static final String COLUMN_COORD_LAT = "coord_lat";
	public static final String COLUMN_COORD_LONG = "coord_long";
        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = COLUMN_DATE + " DESC";
	}
}
