package lab.acme.noviembre15.models;

/**
COLUMN_DATE = "date";
COLUMN_TITLE = "title";
COLUMN_CATEGORY = "category";
 public static final String COLUMN_CATEGORY_ID = "category_id";
COLUMN_FACT = "fact";
COLUMN_VALUE = "value";
COLUMN_COORD_LAT = "coord_lat";
 public static final String COLUMN_COORD_LONG = "coord_long";
 */
public class FactItem {               
    private String Date; 
    private String Title;
    private String Fact; 
    private String Category;
    private String Value; 
    private String Coord_lat;
    private String Coord_long;
    private String ProfileImagePath;
    private int ProfileImageId;

    public FactItem(){}

    public String getDate() {
        return Date;
    }

    public void setDate(String name) {
        Date = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getFact() {
        return Fact;
    }

    public void setFact(String fact) {
        Fact = fact;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getCoord_lat() {
        return Coord_lat;
    }

    public void setCoord_lat(String coord_lat) {
        Coord_lat = coord_lat;
    }

    public String getCoord_long() {
        return Coord_long;
    }

    public void setCoord_long(String coord_long) {
        Coord_long = coord_long;
    }

    public String getProfileImagePath() {
        return ProfileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        ProfileImagePath = profileImagePath;
    }

    public int getProfileImageId() {
        return ProfileImageId;
    }

    public void setProfileImageId(int profileImageId) {
        ProfileImageId = profileImageId;
    }

       /* public String getSerializedAttendances() {
                return SerializedAttendances;
        }*/

       /* public void setSerializedAttendances(String serializedAttendances) {
                SerializedAttendances = serializedAttendances;
        } */


}
