package lab.acme.noviembre15.models;

/**
 * Created by Valentine on 6/16/2015.
 */
public class Attendant {
        private String Date; //**
        private String Title; //**
        private String Fact; //**
        private String StreetAddress;
        private String City;
        private String State;
        private String PostalCode;
        private boolean IsCheckedIn;
        private String ProfileImagePath;
        private int ProfileImageId;
        private String SerializedAttendances;


        //private List<Attendance> Attendances;

        public Attendant(){}


        public boolean isCheckedIn() {
                return IsCheckedIn;
        }

        public void setIsCheckedIn(boolean isCheckedIn) {
                IsCheckedIn = isCheckedIn;
        }

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

        public String getStreetAddress() {
                return StreetAddress;
        }

        public void setStreetAddress(String streetAddress) {
                StreetAddress = streetAddress;
        }

        public String getCity() {
                return City;
        }

        public void setCity(String city) {
                City = city;
        }

        public String getState() {
                return State;
        }

        public void setState(String state) {
                State = state;
        }

        public String getPostalCode() {
                return PostalCode;
        }

        public void setPostalCode(String postalCode) {
                PostalCode = postalCode;
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


