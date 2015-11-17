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
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;

import lab.acme.noviembre15.provider.DbHelper;
import lab.acme.noviembre15.provider.Provider;
//import lab.acme.noviembre15.provider.Provider;

public class DetailActivity extends AppCompatActivity {

    Button mListButton;
    Button mCopyButton;

    private TextView mInfo;
    private TextView mTitle;
    private Context mContext;
    private Activity activity;

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = DetailActivity.this;

        activity = DetailActivity.this;

        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
//            arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());

//            DetailFragment fragment = new DetailFragment();
//            fragment.setArguments(arguments);

 //           getSupportFragmentManager().beginTransaction()
//                    .add(R.id.weather_detail_container, fragment)
//                    .commit();
        }
        initView();

        mTitle = (TextView) findViewById(R.id.info_txt_title);
        // test
        Uri oneTitle = Uri.parse("content://lab.acme.noviembre15/facts/2");
        Cursor c = managedQuery(oneTitle, null, null, null, null);
        if (c.moveToFirst()) {
        mTitle.setText(c.getString(c.getColumnIndex(Provider.COLUMN_TITLE)));
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
        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                micopia();
                //copyDatabase(DbHelper.DB_NAME ) ;
                Log.d(LOG_TAG, DbHelper.DB_NAME);
            }
        });
    }

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
        if (id == R.id.action_settings) {
          //  startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void micopia() {

        String[] dbList =  {"facts"};

        if ( checkDatabase("facts.db"))
            Toast.makeText(getBaseContext(), "O.k. db", Toast.LENGTH_LONG).show();


        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Log.e(LOG_TAG, "1:  " + sd.toString());
            Log.e(LOG_TAG, "2:  " + data.toString());


            if (sd.canWrite()) {
                String currentDBPath = "//data//"+ "lab.acme.noviembre15" +"//databases//"+  dbList[0];  //
                String backupDBPath = dbList[0];  //
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.e(LOG_TAG, "3:   " +  currentDB.toString());
                Log.e(LOG_TAG, "4:   " +  backupDB.toString());

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }




    /**
     * Copies the database from assets to the application's data directory
     *
     * @param dbName Name of the database to be copied
     */
    private void copyDatabase(String dbName) {
        String DATA_PATH =  Environment.getExternalStorageDirectory().toString() + "/Nov 2015/";

        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream in = assetManager.open("data/facts.db");

            OutputStream out = new FileOutputStream(DATA_PATH + "facts.db");

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
      catch (FileNotFoundException e) {
          Log.e(LOG_TAG, e.getMessage());
        e.printStackTrace();
    } catch (IOException e) {

            Log.e(LOG_TAG, e.getMessage());
        e.printStackTrace();
    }

        String  path = this.activity.getApplicationInfo().dataDir + "/" + dbName;
        File file = new File(path);

        File salida = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Nov 2015/" + dbName);

        if (copyFile(file, salida))
            Log.e(LOG_TAG, "Copiada BD");

    }

    public File getBDStorageDir(String bd) {
        // Get the directory for the user's public dowload directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), bd);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }



    public boolean copyFile(File src, File dst) {
     boolean returnValue = true;

     FileChannel inChannel = null, outChannel = null;

     try {

     inChannel = new FileInputStream(src).getChannel();
     outChannel = new FileOutputStream(dst).getChannel();

     } catch (FileNotFoundException fnfe) {

     Log.d(LOG_TAG, "inChannel/outChannel FileNotFoundException");
     fnfe.printStackTrace();
     return false;
     }

     try {
     inChannel.transferTo(0, inChannel.size(), outChannel);

     } catch (IllegalArgumentException iae) {

     Log.d(LOG_TAG, "TransferTo IllegalArgumentException");
     iae.printStackTrace();
     returnValue = false;

     } catch (NonReadableChannelException nrce) {

     Log.d(LOG_TAG, "TransferTo NonReadableChannelException");
     nrce.printStackTrace();
     returnValue = false;

     } catch (NonWritableChannelException nwce) {

     Log.d(LOG_TAG, "TransferTo NonWritableChannelException");
     nwce.printStackTrace();
     returnValue = false;

     } catch (ClosedByInterruptException cie) {

     Log.d(LOG_TAG, "TransferTo ClosedByInterruptException");
     cie.printStackTrace();
     returnValue = false;

     } catch (AsynchronousCloseException ace) {

     Log.d(LOG_TAG, "TransferTo AsynchronousCloseException");
     ace.printStackTrace();
     returnValue = false;

     } catch (ClosedChannelException cce) {

     Log.d(LOG_TAG, "TransferTo ClosedChannelException");
     cce.printStackTrace();
     returnValue = false;

     } catch (IOException ioe) {

     Log.d(LOG_TAG, "TransferTo IOException");
     ioe.printStackTrace();
     returnValue = false;


     } finally {

     if (inChannel != null)

     try {

     inChannel.close();
     } catch (IOException e) {
     e.printStackTrace();
     }

     if (outChannel != null)
     try {
     outChannel.close();
     } catch (IOException e) {
     e.printStackTrace();
     }

     }

     return returnValue;
     }


    /**
     * Checks if the database exists at the application's data directory
     *
     * @param dbName Name of the database to check the existence of
     * @return True if the database exists, false if not
     */
    private boolean checkDatabase(String dbName) {
        File dbFile = new File(this.activity.getApplicationInfo().dataDir + "/" + dbName);
        return dbFile.exists();
    }

}