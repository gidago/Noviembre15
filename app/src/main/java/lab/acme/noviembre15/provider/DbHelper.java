package lab.acme.noviembre15.provider;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "facts.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "myFacts";

    public DbHelper(Context context) {
    	super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s ( %s INT PRIMARY KEY,"
                        + "%s INT, %s TEXT, %s TEXT);", TABLE,
                        StatusContract.Columns._ID, StatusContract.Columns.CREATED_AT,
                        StatusContract.Columns.USER, StatusContract.Columns.MESSAGE);
        Log.d("DbHelper", "sql: "+sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Temporary for development purposes
        db.execSQL("drop table if exists "+ TABLE);
        onCreate(db);
    }
}
