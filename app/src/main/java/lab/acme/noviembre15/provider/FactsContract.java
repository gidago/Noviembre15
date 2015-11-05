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
         * Column definitions for status information.
         */
        public final static class Columns implements BaseColumns {
                private Columns() {}

                /**
                 * The name of the user who posted the status message
                 * <P>Type: TEXT</P>
                 */
                public static final String COLUMN_DATE = "date";

                /**
                 * The status message content
                 * <P>Type: TEXT</P>
                 */
                public static final String COLUMN_TITLE = "title";

                public static final String COLUMN_CATEGORY = "category";

                public static final String COLUMN_CATEGORY_ID = "category_id";

                public static final String COLUMN_FACT = "fact";

                public static final String COLUMN_VALUE = "value";

                /**
                 * The date the message was posted, in milliseconds since the epoch
                 * <P>Type: INTEGER (long)</P>
                 */
                public static final String CREATED_AT = "createdAt";

                /**
                 * The default sort order for this table
                 */
                public static final String DEFAULT_SORT_ORDER = CREATED_AT + " DESC";
        }
}
