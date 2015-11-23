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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lab.acme.noviembre15.provider.Provider;

public class DetailActivity extends AppCompatActivity {
	
    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    private Context mContext;
    private Activity activity;

	//TODO review
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

        Log.d(LOG_TAG, "******** mVId:   " + mVId);


        initView();
        TextView mID = (TextView) findViewById(R.id.detail_ID_textview);
        TextView mTitle = (TextView) findViewById(R.id.detail_title_textview);
        TextView mDate = (TextView) findViewById(R.id.detail_date_textview);
        TextView mFact = (TextView) findViewById(R.id.detail_fact_textview);
        TextView mCategory = (TextView) findViewById(R.id.detail_category_textview);
        TextView mValue = (TextView) findViewById(R.id.detail_value_textview);
        TextView mCoord_lat = (TextView) findViewById(R.id.detail_lat_textview);
        TextView mCoord_long = (TextView) findViewById(R.id.detail_long_textview);
        ImageView factCardImage = (ImageView) findViewById(R.id.detail_icon);
        // test
        Uri oneTitle = Uri.parse("content://lab.acme.noviembre15/facts/" + (mVId + 1) );

        Log.e(LOG_TAG, "******** URI:   " + oneTitle.toString());

        //Uri oneTitle = Uri.parse("content://lab.acme.noviembre15/facts/1");
        Cursor c = managedQuery(oneTitle, null, null, null, null);
       if (c.moveToFirst()) {
            mID.setText(c.getString(c.getColumnIndex(Provider.COLUMN_ID)));
            mTitle.setText(c.getString(c.getColumnIndex(Provider.COLUMN_TITLE)));
            mDate.setText(c.getString(c.getColumnIndex(Provider.COLUMN_DATE)));
            mFact.setText(c.getString(c.getColumnIndex(Provider.COLUMN_FACT)));
            //mFact.setText("" + mVId);
            mCategory.setText(c.getString(c.getColumnIndex(Provider.COLUMN_CATEGORY)));
            mValue.setText(c.getString(c.getColumnIndex(Provider.COLUMN_VALUE)));
            mCoord_lat.setText(c.getString(c.getColumnIndex(Provider.COLUMN_COORD_LAT)));
            mCoord_long.setText(c.getString(c.getColumnIndex(Provider.COLUMN_COORD_LONG)));


            switch (c.getInt(c.getColumnIndex(Provider.COLUMN_CATEGORY_ID))) {
                case 1:
                    Picasso.with(mContext).load(R.drawable.category_1).into(factCardImage );
                    break;
                case 2:
                    Picasso.with(mContext).load(R.drawable.category_2).into(factCardImage );
                    break;
                case 3:
                    Picasso.with(mContext).load(R.drawable.category_3).into(factCardImage );
                    break;
                default:
                    Picasso.with(mContext).load(R.drawable.no_category).into(factCardImage );
            }



            Log.d(LOG_TAG, "******** Provider title:   " + c.getString(c.getColumnIndex(Provider.COLUMN_TITLE)) );
        }
        
        
/***
ArrayList<WhateverTypeYouWant> mArrayList = new ArrayList<WhateverTypeYouWant>();
for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
    // The Cursor is now set to the right position
    mArrayList.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
}

ArrayList<String> mArrayList = new ArrayList<String>();
mCursor.moveToFirst();
while(!mCursor.isAfterLast()) {
     mArrayList.add(mCursor.getString(mCursor.getColumnIndex(dbAdapter.KEY_NAME))); //add the item
     mCursor.moveToNext();
}

ArrayList<String> mArrayList = new ArrayList<String>();
while(mCursor.moveToNext()) {
     mArrayList.add(mCursor.getString(mCursor.getColumnIndex(dbAdapter.KEY_NAME))); //add the item
}


ArrayList<String> mArrayList = new ArrayList<String>();
int columnIndex=mCursor.getColumnIndex(dbAdapter.KEY_NAME)
while(mCursor.moveToNext()) {
     mArrayList.add(mCursor.getString(columnIndex)); //add the item
}


*/        
        
        
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
       // mInfo = (TextView) findViewById(R.id.info_txt);

       // mListButton = (Button) findViewById(R.id.save_button);
       // mCopyButton = (Button) findViewById(R.id.button_copy);
        
      /*  mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaTest();
            }
        });*/
        // Copy db from local storage to public storage
      /*  mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copyDatabase(DbHelper.DB_NAME ) ;
                //Log.d(LOG_TAG, DbHelper.DB_NAME);
            }
        });*/
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
                //mInfo.append(mReg);
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