package lab.acme.noviembre15;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import lab.acme.noviembre15.fragments.ChooseCategoryTypeDialogFragment;
import lab.acme.noviembre15.models.FactItem;
import lab.acme.noviembre15.provider.FactsContract;
import lab.acme.noviembre15.provider.Provider;
import lab.acme.noviembre15.utils.CategoryIconUtils;



public class AddFactActivity extends AppCompatActivity implements ChooseCategoryTypeDialogFragment.ChooseCategoryTypeCaller {

    private final String LOG_TAG = AddFactActivity.class.getSimpleName();

    private EditText mDate, mTitle, mCategory, mFact, mValue, mLong, mLat;
    Button mSaveButton;
    private Context mContext;
    private static Calendar dateTime = Calendar.getInstance();
    //TODO - en curso
	//private static final String ICON_VALUE_KEY = "icon_value_key";
    //private AutoCompleteTextView activityType;

    private Spinner activityTypeIcon;

    private Spinner mCategoryTypeIcon;
    private String iconValue;



	/**
	* Activity types.
	*/
//	public enum ActivityType {
//	CYCLING, RUNNING, WALKING, INVALID
//	}

	 /**
	* Gets the activity type.
	*
	* @param context the context
	* @param activityType the activity type
	*/
/*	public static ActivityType getActivityType(Context context, String activityType) {
		if (activityType == null || activityType.equals("")) {
			return ActivityType.INVALID;
		}
		if (TrackIconUtils.getIconValue(context, activityType).equals(TrackIconUtils.WALK)) {
			return ActivityType.WALKING;
		} else if (TrackIconUtils.getIconValue(context, activityType).equals(TrackIconUtils.RUN)) {
			return ActivityType.RUNNING;
		} else if (TrackIconUtils.getIconValue(context, activityType).equals(TrackIconUtils.BIKE)) {
			return ActivityType.CYCLING;
		}
		return ActivityType.INVALID;
	}*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = AddFactActivity.this;
        initView();
    }

    private void initView() {

        iconValue = "";
        mDate = (EditText) findViewById(R.id.edit_text_date);
        mTitle = (EditText)findViewById(R.id.edit_text_title);
        mCategory = (EditText) findViewById(R.id.edit_text_category);
        mFact = (EditText) findViewById(R.id.edit_text_fact);
        mValue = (EditText) findViewById(R.id.edit_text_value);
        mLat = (EditText) findViewById(R.id.edit_text_latitude);
        mLong = (EditText) findViewById(R.id.edit_text_longitude);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mContext, "dd/MM/yyyy", mDate);
            }
        });

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requiredFieldsCompleted()) {
                    saveFact();
                }
            }
        });

        //TODO - en curso
       /* Spinner spinner = (Spinner) findViewById(R.id.track_edit_activity_type_icon);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //TODO - test it
        // Spinner item selection Listener 
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());*/

        mCategory = (EditText) findViewById(R.id.edit_text_category);
        mCategoryTypeIcon = (Spinner) findViewById(R.id.spinner_category_type_icon);
        mCategoryTypeIcon.setAdapter(CategoryIconUtils.getIconSpinnerAdapter(this, iconValue));
        mCategoryTypeIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ChooseCategoryTypeDialogFragment.newInstance(mCategory.getText().toString()).show(
                            getSupportFragmentManager(),
                            ChooseCategoryTypeDialogFragment.CHOOSE_CATEGORY_TYPE_DIALOG_TAG);
                }
                return true;
            }
        });
        mCategoryTypeIcon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    ChooseCategoryTypeDialogFragment.newInstance(mCategory.getText().toString()).show(
                            getSupportFragmentManager(),
                            ChooseCategoryTypeDialogFragment.CHOOSE_CATEGORY_TYPE_DIALOG_TAG);
                }
                return true;
            }
        });
    }


    private boolean requiredFieldsCompleted() {
        //Check if required information are entered, here we are only checking for
        //Name and email and you can check for more or none
        boolean result = false;
        if (mDate.getText() != null && !mDate.getText().toString().isEmpty()) {
            result = true;
        } else {
            mDate.setError(getString(R.string.required_fields_empty));
        }

        if (mTitle.getText() != null && !mTitle.getText().toString().isEmpty()) {
            result = true;
        } else {
            mTitle.setError(getString(R.string.required_fields_empty));
        }
        return result;
    }

    private void resetFields(){
        //Wipe out any information the user entered so they can enter another
        //Fact
        mDate.setText("");
        mTitle.setText("");
        mCategory.setText("");
        mFact.setText("");
        mValue.setText("");
        mLat.setText("");
        mLong.setText("");
    }

    private void saveFact() {
        //populate the Attendant object with the data entered in the screen
        FactItem mmFact;
        mmFact = new FactItem();
        mmFact.setDate(mDate.getText().toString());
        mmFact.setTitle(mTitle.getText().toString());
        mmFact.setCategory(mCategory.getText().toString());
        mmFact.setFact(mFact.getText().toString());
        mmFact.setValue(mValue.getText().toString());
        mmFact.setCoord_lat(mLat.getText().toString());
        mmFact.setCoord_long(mLong.getText().toString());
        //Save to the database
        // add a row (reg)
        ContentValues values = new ContentValues();
        values.put(FactsContract.FactsEntry.COLUMN_DATE, mDate.getText().toString()); //Get date from input form

        if (Objects.equals(mCategory.getText().toString(), "Viajes"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "1");
        if (Objects.equals(mCategory.getText().toString(), "Hogar"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "2");
        if (Objects.equals(mCategory.getText().toString(), "Trabajo"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "3");
        if (Objects.equals(mCategory.getText().toString(), "Otros"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "4");

        values.put(FactsContract.FactsEntry.COLUMN_VALUE, mValue.getText().toString()); ///Get value from input form
        values.put(FactsContract.FactsEntry.COLUMN_COORD_LAT, mLat.getText().toString());
        values.put(FactsContract.FactsEntry.COLUMN_COORD_LONG, mLong.getText().toString());
        values.put(FactsContract.FactsEntry.COLUMN_TITLE, mTitle.getText().toString()); //"Prueba DB"
        values.put(FactsContract.FactsEntry.COLUMN_FACT, mFact.getText().toString()); // Long description
        values.put(FactsContract.FactsEntry.COLUMN_CATEGORY, mCategory.getText().toString());
        getContentResolver().insert(Provider.CONTENT_URI, values);
        //Wipe all fields
        resetFields();
        //Provide feedback to the user
        Toast.makeText(mContext, mmFact.getTitle() + " saved", Toast.LENGTH_SHORT).show();
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
     *  Set category from spinner
     */
    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            mCategory.setText(parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            // Another interface callback
        }
    }

    private void setmCategoryTypeIcon(String value) {
        iconValue = value;
        CategoryIconUtils.setIconSpinner(mCategoryTypeIcon, value);
    }

    @Override
    public void onChooseCategoryTypeDone(String value) {
        setmCategoryTypeIcon(value);
        mCategory.setText(getString(CategoryIconUtils.getIconActivityType(value)));
    }
}
