package lab.acme.noviembre15;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//
// Ref.
// https://www.websmithing.com/2011/02/01/how-to-update-the-ui-in-an-android-activity-using-data-from-a-background-service/
//
public class PisActivity extends AppCompatActivity {

    private final String LOG_TAG = PisActivity.class.getSimpleName();
    private static Calendar dateTime = Calendar.getInstance();

    //String salida;
    //private TextView mTitle;

    private TextView mDiasLeft;
    private TextView mHoursLeft;
    private TextView mMinutesLeft;
    private TextView mSecondsLeft;

    private TextView mDateEnd;
    private ImageView mCamera;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = PisActivity.this;

        setContentView(R.layout.activity_pis);

        // Init calendar of datePicker
        int mYear = 2016;
        int mMonth = 3; //APRIL
        int mDay = 1;

        dateTime.set(mYear, mMonth, mDay);

        mDateEnd = (TextView) findViewById(R.id.pis_fin_textview);
        mCamera = (ImageView) findViewById(R.id.detail_icon);
        mDateEnd.setText("01/04/2016");

        //Log.e(LOG_TAG, "===========================>>>> OnCreate  mDateEnd:   " + mDateEnd.getText());
        calculo();

        //TODO quitar
        //mTitle = (TextView) findViewById(R.id.detail_humidity_textview);
        //mTitle.setText(salida);

        mDiasLeft    = (TextView) findViewById(R.id.pis_day);
        mHoursLeft   = (TextView) findViewById(R.id.pis_hour);
        mMinutesLeft = (TextView) findViewById(R.id.pis_minute);
        mSecondsLeft = (TextView) findViewById(R.id.pis_second);


        mCamera .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog( mContext, "dd/MM/yyyy", mDateEnd);
                calculo();
            }
        });

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        calculo();
                    }
                });
            }
        }, 0, 100);
    }



    public void calculo(){

        //String dateStart = "21/11/2015 09:29:58";
        String dateStart = getCurrentDate("dd/MM/yyyy HH:mm:ss");
        //String dateStop = "01/04/2016 00:00:00";
        String dateStop =  mDateEnd.getText().toString() +" 00:00:00";

        //Log.e(LOG_TAG, "===========================>>>> dateStar:   " + dateStart);
        //Log.e(LOG_TAG, "===========================>>>> dateStop:   " + dateStop);

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date d1;
        Date d2;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            mDiasLeft.setText(String.format(" %03d ",diffDays));
            mHoursLeft.setText(String.format(" %02d ",diffHours));
            mMinutesLeft.setText(String.format(" %02d ", diffMinutes));
            mSecondsLeft.setText(String.format(" %02d ", diffSeconds));

            //salida = "( " + diffDays + " dias, " + diffHours + " horas, " + diffMinutes + " minutos, " + diffSeconds + " segundos."+ " )" ;
            //mTitle.setText(salida);
            //Log.e(LOG_TAG, "***diff dates: :   " + diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds.");

            //mTitle.invalidate();
            mDiasLeft.invalidate();
            mHoursLeft.invalidate();
            mMinutesLeft.invalidate();
            mSecondsLeft.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * use to show datepicker
     *
     * @param mContext
     * @param format    of the date format
     * @param mTextView in which you have to set selected date
     */
    public static void showDatePickerDialog(final Context mContext,
                                            final String format, final TextView mTextView) {

        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
                dateTime.set(year, monthOfYear, dayOfMonth);

                mTextView.setText(dateFormatter.format(dateTime.getTime()).toString());
            }
        }, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH),
                dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Get today's date in any format.
     *
     * @param dateFormat pass format for get like: "yyyy-MM-dd hh:mm:ss"
     * @return current date in string format
     */
    public static String getCurrentDate(String dateFormat) {
        Date d = new Date();
        String currentDate = new SimpleDateFormat(dateFormat).format(d.getTime());
        return currentDate;
    }

    /**
     * Convert date in string format to Date format
     *
     * @param strdate which you have to convert in Date format
     * @param format  of the date like "yyyy-MM-dd"
     * @return date in Date format
     */
    public static Date stringToDate(String strdate, String format) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            date = formatter.parse(strdate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


}
