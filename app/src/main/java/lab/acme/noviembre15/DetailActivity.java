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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
	
    private final String LOG_TAG = DetailActivity.class.getSimpleName();    

    private Context mContext;
    protected int detailId;
    protected String detailDate;
    protected String detailTitle;
    protected String detailFact;
    protected int detailCategoryID;
    protected String detailCategory;
    protected String detailValue;
    protected String detailLat;
    protected String detailLong;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setTitle("Detalles");
        setContentView(R.layout.activity_detail);
        if(savedInstanceState != null) {
            detailId = savedInstanceState.getInt("ID");
        }
        else if(savedInstanceState == null) {
                Bundle extras =     getIntent().getExtras();
                detailId =          extras.getInt("ID");
                detailDate =        extras.getString("COL_FACTS_DATE");
                detailTitle =       extras.getString("COL_FACTS_TITLE");
                detailFact =        extras.getString("COL_FACTS_FACT");
                detailCategoryID =  extras.getInt("COL_FACTS_CATEGORY_ID");
                detailCategory  =   extras.getString("COL_FACTS_CATEGORY");
                detailValue =       extras.getString("COL_FACTS_VALUE");
                detailLat =         extras.getString("COL_FACTS_COORD_LAT");
                detailLong =        extras.getString("COL_FACTS_COORD_LONG");
        }
        TextView mID = (TextView) findViewById(R.id.detail_ID_textview);
        TextView mTitle = (TextView) findViewById(R.id.detail_title_textview);
        TextView mDate = (TextView) findViewById(R.id.detail_date_textview);
        TextView mFact = (TextView) findViewById(R.id.detail_fact_textview);
        TextView mCategory = (TextView) findViewById(R.id.detail_category_textview);
        TextView mLblEuro = (TextView) findViewById(R.id.detail_label_euro);
        TextView mValue = (TextView) findViewById(R.id.detail_value_textview);
        TextView mCoord_lat = (TextView) findViewById(R.id.detail_lat_textview);
        TextView mCoord_long = (TextView) findViewById(R.id.detail_long_textview);
        ImageView factCardImage = (ImageView) findViewById(R.id.detail_icon);
        TextView  mTimeAgo = (TextView) findViewById(R.id.timeAgo);

        mID.setText(" "+ (detailId + 1));
        mTitle.setText(detailTitle);
        mDate.setText(detailDate);
        mFact.setText(detailFact);
        mCategory.setText(detailCategory);
        if ( detailValue.compareTo("0") > 0 ) {
            mLblEuro.setText("€");
            mValue.setText(detailValue);
        }
        mCoord_lat.setText(detailLat);
        mCoord_long.setText(detailLong);
        switch (detailCategoryID) {
              case 1:
                    Picasso.with(mContext).load(R.drawable.category_1).into(factCardImage);
                    break;
                case 2:
                    Picasso.with(mContext).load(R.drawable.category_2).into(factCardImage);
                    break;
                case 3:
                    Picasso.with(mContext).load(R.drawable.category_3).into(factCardImage);
                    break;
                case 4:
                    Picasso.with(mContext).load(R.drawable.category_4).into(factCardImage);
                    break;
                case 5:
                    Picasso.with(mContext).load(R.drawable.category_5).into(factCardImage);
                    break;
                default:
                    Picasso.with(mContext).load(R.drawable.no_category).into(factCardImage);
        }
        try {
            long longTimeAgo    = timeStringToMillis(detailDate);
            PrettyTime prettyTime = new PrettyTime();
            mTimeAgo.setText(prettyTime.format(new Date(longTimeAgo)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long timeStringToMillis(String timeString) {
        long timeInMilliseconds = 0l;
        final String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date mDate = format.parse(timeString);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            //CrashUtils.trackException("Unable to parse last downloaded date",e);
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }
}