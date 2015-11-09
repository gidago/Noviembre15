package lab.acme.noviembre15;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import lab.acme.noviembre15.models.Attendant;


public class AddAttendantActivity extends AppCompatActivity {
    private EditText mName, mEmail, mPhone, mStreet, mCity, mZip, mState;
    Button mSaveButton;
    private View mRootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_attendant);
       // mContext = AppIconAct.this;

        //init();
        initView();
    }
    //public AddAttendantFragment() {
        // Required empty public constructor
   // }


  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_attendant, container, false);
        initView();
        return mRootView;
    }*/

    private void initView() {
        mName = (EditText) findViewById(R.id.edit_text_client_name);
        mEmail = (EditText)findViewById(R.id.edit_text_client_email);
        mPhone = (EditText) findViewById(R.id.edit_text_client_phone);
        mPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        mPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        mStreet = (EditText) findViewById(R.id.edit_text_client_street_address);
        mCity = (EditText) findViewById(R.id.edit_text_client_city);
        mState = (EditText) findViewById(R.id.edit_text_client_state);
        mZip = (EditText) findViewById(R.id.edit_text_client_zip_code);

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requiredFieldsCompleted()){
                   // saveAttendant();
                }

            }
        });

    }

    private boolean requiredFieldsCompleted() {
        //Check if required information are entered, here we are only checking for
        //Name and email and you can check for more or none
        boolean result = false;
        if (mName.getText() != null && !mName.getText().toString().isEmpty()) {
            result = true;
        } else {
            mName.setError(getString(R.string.required_fields_empty));
        }

        if (mEmail.getText() != null && !mEmail.getText().toString().isEmpty()) {
            result = true;
        } else {
            mEmail.setError(getString(R.string.required_fields_empty));
        }
        return result;
    }

    private void resetFields(){
        //Wipe out any information the user entered so they can enter another
        //Attendant
        mName.setText("");
        mEmail.setText("");
        mPhone.setText("");
        mStreet.setText("");
        mCity.setText("");
        mState.setText("");
        mZip.setText("");
    }


    private void saveAttendant() {
        //populate the Attendant object with the data entered in the screen
        Attendant mAttendant;
        mAttendant = new Attendant();
        mAttendant.setName(mName.getText().toString());
        mAttendant.setEmail(mEmail.getText().toString());
        mAttendant.setPhone(mPhone.getText().toString());
        mAttendant.setStreetAddress(mStreet.getText().toString());
        mAttendant.setCity(mCity.getText().toString());
        mAttendant.setState(mState.getText().toString());
        mAttendant.setPostalCode(mZip.getText().toString());

        //Save to the database
        //mAttendant.save();

        //Wipe all fields
        resetFields();

        //Provide feedback to the user
        //Toast.makeText(getActivity(), mAttendant.getName() + " saved", Toast.LENGTH_SHORT).show();

    }


}