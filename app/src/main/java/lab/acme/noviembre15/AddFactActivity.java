package lab.acme.noviembre15;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lab.acme.noviembre15.models.FactItem;
import lab.acme.noviembre15.provider.Provider;

public class AddFactActivity extends AppCompatActivity {

    private final String LOG_TAG = AddFactActivity.class.getSimpleName();

    private EditText mDate, mTitle, mCategory, mFact, mValue, mLong, mLat;
    Button mSaveButton;
    private View mRootView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fact);
       //mContext = AppIconAct.this;
        mContext = AddFactActivity.this;
        //init();
        initView();
    }

    private void initView() {
        mDate = (EditText) findViewById(R.id.edit_text_date);
        mTitle = (EditText)findViewById(R.id.edit_text_title);
        mCategory = (EditText) findViewById(R.id.edit_text_category);
       //  mPhone.setInputType(InputType.TYPE_CLASS_PHONE);
       // mPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mFact = (EditText) findViewById(R.id.edit_text_fact);
        mValue = (EditText) findViewById(R.id.edit_text_value);
        mLat = (EditText) findViewById(R.id.edit_text_latitude);
        mLong = (EditText) findViewById(R.id.edit_text_longitude);

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requiredFieldsCompleted()){
                    saveFact();
                }
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
        FactItem mFact;
        mFact = new FactItem();
        mFact.setDate(mDate.getText().toString());
        mFact.setTitle(mTitle.getText().toString());
        mFact.setCategory(mCategory.getText().toString());
        mFact.setFact(mFact.getText().toString());
        mFact.setValue(mValue.getText().toString());
        mFact.setCoord_lat(mLat.getText().toString());
        mFact.setCoord_long(mLong.getText().toString());
        //Save to the database
        // add a row (reg)
        ContentValues values = new ContentValues();
        values.put(Provider.COLUMN_DATE, mDate.getText().toString()); //Get date from input form
        values.put(Provider.COLUMN_CATEGORY_ID, 1);
        values.put(Provider.COLUMN_VALUE, mValue.getText().toString()); ///Get value from input form
        values.put(Provider.COLUMN_COORD_LAT, mLat.getText().toString());
        values.put(Provider.COLUMN_COORD_LONG, mLong.getText().toString());
        values.put(Provider.COLUMN_TITLE, mTitle.getText().toString()); //"Prueba DB"
        values.put(Provider.COLUMN_FACT, mFact.getText().toString()); // Long description
        values.put(Provider.COLUMN_CATEGORY, mCategory.getText().toString());
        //Uri uri = getContentResolver().insert(Provider.CONTENT_URI, values);
        //Uri uri =
        getContentResolver().insert(Provider.CONTENT_URI, values);

        // test
        Uri allTitles = Uri.parse("content://lab.acme.noviembre15/facts");

        Cursor c = managedQuery(allTitles, null, null, null, "date desc");

        if (c.moveToFirst()) {
            do {
                String mReg = c.getString(c.getColumnIndex(Provider.COLUMN_DATE))
                        + ", \""
                        + c.getString(c.getColumnIndex(Provider.COLUMN_FACT))
                        + "\"";
                Log.d(LOG_TAG, mReg);
            } while (c.moveToNext());
        }
        //mAttendant.save();
        //Wipe all fields
        resetFields();
        //Provide feedback to the user
        Toast.makeText(mContext, mFact.getTitle() + " saved", Toast.LENGTH_SHORT).show();
    }

}