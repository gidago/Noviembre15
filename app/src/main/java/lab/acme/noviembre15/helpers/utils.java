import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

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

class utils {
	
//
//http://stackoverflow.com/questions/13247657/copy-files-from-internal-storage-to-external-storage
//
// InputStream in = new FileInputStream("data/data/packagename/files/myfile.pdf") 
// InputStream in = new FileInputStream(Environment.getDataDirectory()+"/files/myfile.pdf")
//
public static boolean copyFile(String from, String to) {
	try {
	    int bytesum = 0;
	    int byteread = 0;
	    File oldfile = new File(from);
	    if (oldfile.exists()) {
	        InputStream inStream = new FileInputStream(from);
	        FileOutputStream fs = new FileOutputStream(to);
	        byte[] buffer = new byte[1444];
	        while ((byteread = inStream.read(buffer)) != -1) {
	            bytesum += byteread;
	            fs.write(buffer, 0, byteread);
	        }
	        inStream.close();
	        fs.close();
	    }
	    return true;
	} catch (Exception e) {
	    return false;
	}
}


 /*   public boolean copyFile(File src, File dst) {
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
     }*/
//
//http://stackoverflow.com/questions/4770004/how-to-move-rename-file-from-internal-app-storage-to-external-storage-on-android
//
//Make sure you have the following permissions if writing/reading external memory 
//  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> 
//  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> 
//
//To copy files from internal memory to SD card and vice-versa using following piece of code:
//
/**public static void copyFile(File src, File dst) throws IOException
{
    FileChannel inChannel = new FileInputStream(src).getChannel();
    FileChannel outChannel = new FileOutputStream(dst).getChannel();
    try
    {
        inChannel.transferTo(0, inChannel.size(), outChannel);
    }
    finally
    {
        if (inChannel != null)
            inChannel.close();
        if (outChannel != null)
            outChannel.close();
    }
}*/
//
//
//
/**public boolean copyFile(File src, File dst) {
    boolean returnValue = true;

   FileChannel inChannel = null, outChannel = null;

    try {

        inChannel = new FileInputStream(src).getChannel();
        outChannel = new FileOutputStream(dst).getChannel();

   } catch (FileNotFoundException fnfe) {

        Log.d(logtag, "inChannel/outChannel FileNotFoundException");
        fnfe.printStackTrace();
        return false;
   }

   try {
       inChannel.transferTo(0, inChannel.size(), outChannel);

   } catch (IllegalArgumentException iae) {

         Log.d(logtag, "TransferTo IllegalArgumentException");
         iae.printStackTrace();
         returnValue = false;

   } catch (NonReadableChannelException nrce) {

         Log.d(logtag, "TransferTo NonReadableChannelException");
         nrce.printStackTrace();
         returnValue = false;

   } catch (NonWritableChannelException nwce) {

        Log.d(logtag, "TransferTo NonWritableChannelException");
        nwce.printStackTrace();
        returnValue = false;

   } catch (ClosedByInterruptException cie) {

        Log.d(logtag, "TransferTo ClosedByInterruptException");
        cie.printStackTrace();
        returnValue = false;

   } catch (AsynchronousCloseException ace) {

        Log.d(logtag, "TransferTo AsynchronousCloseException");
        ace.printStackTrace();
        returnValue = false;

   } catch (ClosedChannelException cce) {

        Log.d(logtag, "TransferTo ClosedChannelException");
        cce.printStackTrace(); 
        returnValue = false;

    } catch (IOException ioe) {

        Log.d(logtag, "TransferTo IOException");
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
}*/



/* Checks if external storage is available for read and write */
public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
        return true;
    }
    return false;
}

    private final String LOG_TAG = utils.class.getSimpleName();

public File getAlbumStorageDir(String albumName) {
    // Get the directory for the user's public pictures directory. 
    File file = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), albumName);
    if (!file.mkdirs()) {
        Log.e(LOG_TAG, "Directory not created");
    }
    return file;
}
//
// DIRECTORY_DCIM
// DIRECTORY_DOWNLOADS 
//


    /**
     * Copies the database from assets to the application's data directory
     *
     * @param dbName Name of the database to be copied
     */
 /*   private void copyDatabase(String dbName) {
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
    }*/



    private void populateList() {
        // test
        Uri allTitles = Uri.parse("content://lab.acme.noviembre15/facts");
        Cursor cursor = managedQuery(allTitles, null, null, null, "date desc");
        if (cursor.moveToFirst()) {
            do {
                FactItem guest = new FactItem();
                guest.setDate(cursor.getString(cursor.getColumnIndex(Provider.COLUMN_DATE)));
                guest.setTitle(cursor.getString(cursor.getColumnIndex(Provider.COLUMN_FACT)));

                switch (cursor.getInt(cursor.getColumnIndex(Provider.COLUMN_CATEGORY_ID))) {

                    case (1):
                        guest.setProfileImageId(R.drawable.category_1);
                        break;
                    case (2):
                        guest.setProfileImageId(R.drawable.category_2);
                        break;
                    case (3):
                        guest.setProfileImageId(R.drawable.category_3);
                        break;
                    default:
                        guest.setProfileImageId(R.drawable.headshot_9);
                        break;
                }
                mFactItemList.add(guest);
                Log.d(LOG_TAG, "** Items.  " + cursor.getString(cursor.getColumnIndex(Provider.COLUMN_ID)));
            } while (cursor.moveToNext());
        }
    }

   private void addTestGuessList() {

        FactItem  guest1 = new FactItem();
        guest1.setDate("12 noviembre 2015");
        guest1.setTitle("Informes parcial");
	    guest1.setFact("Texto largo de Informes parcial");
	    guest1.setCategory("Test");
        guest1.setProfileImageId(R.drawable.category_3);
        mFactItemList.add(guest1);

        /**
        values.put(Provider.COLUMN_DATE, "25 enero 2015");
        values.put(Provider.COLUMN_CATEGORY_ID, 1);
        values.put(Provider.COLUMN_VALUE, 20);
        values.put(Provider.COLUMN_COORD_LAT, 20.40);
        values.put(Provider.COLUMN_COORD_LONG, 50.40);
        values.put(Provider.COLUMN_TITLE, "Walking Out the Door");
        values.put(Provider.COLUMN_FACT, "John Doe write Walking Out the Door");
        values.put(Provider.COLUMN_CATEGORY, "Pruebas");
        * */


        FactItem  guest2 = new FactItem();
        guest2.setDate("24 diciembre 2015");
        guest2.setTitle("Navidad");
	    guest2.setCategory("Test");
	    
        guest2.setProfileImageId(R.drawable.headshot_2);
        mFactItemList.add(guest2);

        FactItem  guest3 = new FactItem();
        guest3.setDate("28 octubre 2015");
        guest3.setTitle("Viaje a Venecia");
        guest3.setCategory("Test");
        guest3.setProfileImageId(R.drawable.category_1);
        mFactItemList.add(guest3);

        FactItem  guest4 = new FactItem();
        guest4.setDate("01 enero 2016");
        guest4.setTitle("La gran decisión");
        guest4.setCategory("Test");
        guest4.setProfileImageId(R.drawable.no_category);
        mFactItemList.add(guest4);

        FactItem  guest5 = new FactItem();
        guest5.setDate("01 mayo 2016");
        guest5.setTitle("Una actividad");
        guest5.setProfileImageId(R.drawable.headshot_5);
        mFactItemList.add(guest5);

        FactItem  guest6 = new FactItem();
        guest6.setDate("22 mayo 2016");
        guest6.setTitle("Cualquier hecho");
        guest6.setProfileImageId(R.drawable.category_2);
        mFactItemList.add(guest6);

        FactItem  guest7 = new FactItem();
        guest7.setDate("20 marzo 2014");
        guest7.setTitle("Autoescuela");
        guest7.setProfileImageId(R.drawable.headshot_7);
        mFactItemList.add(guest7);

        FactItem  guest8 = new FactItem();
        guest8.setDate("21 agosto 2016");
        guest8.setTitle("Dentista");
        guest8.setProfileImageId(R.drawable.headshot_8);
        mFactItemList.add(guest8);

        FactItem  guest9 = new FactItem();
        guest9.setDate("11 noviembre 2016");
        guest9.setTitle("Regalos");
        guest9.setProfileImageId(R.drawable.headshot_9);
        mFactItemList.add(guest9);

        FactItem  guest10 = new FactItem();
        guest10.setDate("01 junio 2016");
        guest10.setTitle("Patines");
        guest10.setProfileImageId(R.drawable.headshot_10);
        mFactItemList.add(guest10);
    }


}
