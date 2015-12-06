import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import lab.acme.noviembre15.R;
import lab.acme.noviembre15.models.FactItem;
import lab.acme.noviembre15.provider.Provider;

class utils {
	
//

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


/* Checks if external storage is available for read and write */
public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(state);
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
}
