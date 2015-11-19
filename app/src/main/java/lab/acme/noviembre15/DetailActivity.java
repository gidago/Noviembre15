/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lab.acme.noviembre15;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lab.acme.noviembre15.provider.Provider;

public class DetailActivity extends AppCompatActivity {

    Button mListButton;
    Button mCopyButton;

    private TextView mInfo;
    private TextView mTitle;
    private Context mContext;
    private Activity activity;

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    protected int mVId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = DetailActivity.this;
        activity = DetailActivity.this;

        setContentView(R.layout.activity_detail);


        if(savedInstanceState != null) {
            this.mVId = savedInstanceState.getInt("ID");
        }
        else if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            this.mVId = extras.getInt("ID");
        }

        Log.d(LOG_TAG, "******** mVId:   " + mVId );

      /*  if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
        }*/
        initView();

        mTitle = (TextView) findViewById(R.id.info_txt_title);

        // test
        Uri oneTitle = Uri.parse("content://lab.acme.noviembre15/facts/" + mVId + 1 );
        //Uri oneTitle = Uri.parse("content://lab.acme.noviembre15/facts/1");
        Cursor c = managedQuery(oneTitle, null, null, null, null);
        if (c.moveToFirst()) {
        mTitle.setText(c.getString(c.getColumnIndex(Provider.COLUMN_TITLE)));
            Log.d(LOG_TAG, "******** Provider title:   " +c.getString(c.getColumnIndex(Provider.COLUMN_TITLE)) );
        }
      /*  if (c.moveToFirst()) {
            do {
                Toast.makeText(
                        this,
                        c.getString(c.getColumnIndex(Provider.COLUMN_DATE))
                                + ", \""
                                + c.getString(c.getColumnIndex(Provider.COLUMN_FACT))
                                + "\"",
                        Toast.LENGTH_LONG).show();
                String mReg = c.getString(c.getColumnIndex(Provider.COLUMN_DATE))
                        + ", \""
                        + c.getString(c.getColumnIndex(Provider.COLUMN_FACT))
                        + "\" " ;
                Log.i(LOG_TAG, mReg);
            } while (c.moveToNext());
        }*/

    }

    private void initView() {
        mInfo = (TextView) findViewById(R.id.info_txt);

        mListButton = (Button) findViewById(R.id.save_button);
        mCopyButton = (Button) findViewById(R.id.button_copy);
        
        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaTest();
            }
        });
        // Copy db from local storage to public storage
        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //copyDatabase(DbHelper.DB_NAME ) ;
                //Log.d(LOG_TAG, DbHelper.DB_NAME);
            }
        });
    }


    //Lista filas en text view
    private void listaTest() {

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
                mInfo.append(mReg);
            } while (c.moveToNext());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
      /*  if (id == R.id.action_settings) {
          //  startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }




}