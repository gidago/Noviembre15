package lab.acme.noviembre15;

import android.app.Application;
import android.util.Log;


public class MyApplication extends Application {
    private final String LOG_TAG = MyApplication.class.getSimpleName();
    private final String mMsg = "The application is starting - by ILG";
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, mMsg);
    }
}
