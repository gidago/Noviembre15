import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import lab.acme.noviembre15.DetailActivity;

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





}
