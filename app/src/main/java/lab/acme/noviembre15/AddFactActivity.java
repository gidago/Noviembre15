package lab.acme.noviembre15;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
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
    private Spinner mCategoryTypeIcon;
    private String iconValue;

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

        Log.e(LOG_TAG, " 1 =======>   saveFact():  "
                + "\n  -> DATE:  " + mDate.getText().toString());

        if (Objects.equals(mCategory.getText().toString(), "Viajes"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "1");
        if (Objects.equals(mCategory.getText().toString(), "Hogar"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "2");
        if (Objects.equals(mCategory.getText().toString(), "Trabajo"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "3");
        if (Objects.equals(mCategory.getText().toString(), "Compras"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "4");
        if (Objects.equals(mCategory.getText().toString(), "Afición"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "5");
        if (Objects.equals(mCategory.getText().toString(), "Otros"))
            values.put(FactsContract.FactsEntry.COLUMN_CATEGORY_ID, "6");

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
