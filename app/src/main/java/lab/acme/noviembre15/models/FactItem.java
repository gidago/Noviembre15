package lab.acme.noviembre15.models;

public class FactItem {               
    private String Date; 
    private String Title;
    private String Fact; 
    private String Category;
    private int category_id;
    private String Value; 
    private String Coord_lat;
    private String Coord_long;


    public FactItem(){}

    public void setDate(String name) {
        Date = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setFact(String fact) {
        Fact = fact;
    }


    public void setCategory(String category) {
        Category = category;
    }


    public void setValue(String value) {
        Value = value;
    }


    public void setCoord_lat(String coord_lat) {
        Coord_lat = coord_lat;
    }


    public void setCoord_long(String coord_long) {
        Coord_long = coord_long;
    }

}
