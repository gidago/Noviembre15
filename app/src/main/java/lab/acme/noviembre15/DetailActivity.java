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

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lab.acme.noviembre15.provider.FactsContract;

public class DetailActivity extends AppCompatActivity {
	
    private final String LOG_TAG = DetailActivity.class.getSimpleName();    
    //private Activity activity;
    private Context mContext;
    protected int mVId;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);        
        //activity = DetailActivity.this;
        setTitle("Detalles");
        setContentView(R.layout.activity_detail);
        if(savedInstanceState != null) {
            this.mVId = savedInstanceState.getInt("ID");
        }
        else if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            this.mVId = extras.getInt("ID");
        }
        Log.e(LOG_TAG, "******** mVId:   " + mVId);
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
		//
        Log.e(LOG_TAG, "******** URI:   " + oneTitle.toString());
        //Uri oneTitle = Uri.parse("content://lab.acme.noviembre15/facts/1");
        Cursor c = managedQuery(oneTitle, null, null, null, null);
		if (c.moveToFirst()) {
        	mID.setText("ID: " + c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_ID)));
            mTitle.setText(c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_TITLE)));
            mDate.setText(c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_DATE)));
            mFact.setText(c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_FACT)));
            //mFact.setText("" + mVId);
            mCategory.setText(c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_CATEGORY)));
            mValue.setText(c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_VALUE)));
            mCoord_lat.setText(c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_COORD_LAT)));
            mCoord_long.setText(c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_COORD_LONG)));
            switch (c.getInt(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_CATEGORY_ID))) {
                case 1:
                    Picasso.with(mContext).load(R.drawable.category_1).into(factCardImage);
                    break;
                case 2:
                    Picasso.with(mContext).load(R.drawable.category_2).into(factCardImage);
                    break;
                case 3:
                    Picasso.with(mContext).load(R.drawable.category_3).into(factCardImage);
                    break;

                default:
                    Picasso.with(mContext).load(R.drawable.no_category).into(factCardImage);

            }
            Log.d(LOG_TAG, "** Provider title:   " + c.getString(c.getColumnIndex(FactsContract.FactsEntry.COLUMN_TITLE)) );
        }
    }
}