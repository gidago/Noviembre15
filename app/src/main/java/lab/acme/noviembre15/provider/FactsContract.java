package lab.acme.noviembre15.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class FactsContract {

    private FactsContract() {}
    // Todo: Elegir AUTHORITY vs. CONTENT_AUTHORITY
    /** The authority for the contacts provider */
    public static final String AUTHORITY = "lab.acme.noviembre15";
    /**atanarro*/
    public static final String PROVIDER_NAME = "net.atanarro.provider.Songs";
//    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/songs");

    // static final String AUTHORITY = "content://lab.acme.noviembre15";

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "lab.acme.noviembre15";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_FACTS = "facts";

   // public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);

    /** A content:// style uri to the authority for this table */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY );



	// TODO - Cambiar ColumnsEntry por factsEntry
    /**
     * Column definitions for facts information.
     */
    /* Inner class that defines the table contents of the facts table */
    public final static class ColumnsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FACTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FACTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FACTS;

        private ColumnsEntry() {}

 	// Table name
	public static final String TABLE_NAME = "myfacts";

    public static final String COLUMN_ID = "_ID";
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
