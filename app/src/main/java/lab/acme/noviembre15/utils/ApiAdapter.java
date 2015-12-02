package lab.acme.noviembre15.utils;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.TabWidget;

import java.io.IOException;
import java.util.List;

/**
 * A set of methods that may be implemented differently depending on the Android
 * API level.
 *
 * @author Bartlomiej Niechwiej
 */
public interface ApiAdapter {

    /**
     * Applies all the changes done to a given preferences editor. Changes may or
     * may not be applied immediately.
     * <p>
     * Due to changes in API level 9.
     *
     * @param editor the editor
     */
    void applyPreferenceChanges(SharedPreferences.Editor editor);

    /**
     * Enables strict mode where supported, only if this is a development build.
     * <p>
     * Due to changes in API level 9.
     */
    void enableStrictMode();

    /**
     * Copies elements from an input byte array into a new byte array, from
     * indexes start (inclusive) to end (exclusive). The end index must be less
     * than or equal to the input length.
     * <p>
     * Due to changes in API level 9.
     *
     * @param input the input byte array
     * @param start the start index
     * @param end the end index
     * @return a new array containing elements from the input byte array.
     */
    byte[] copyByteArray(byte[] input, int start, int end);


    /**
     * Returns true if GeoCoder is present.
     * <p>
     * Due to changes in API level 9.
     */
    boolean isGeoCoderPresent();

    /**
     * Returns true to revert menu icon color.
     * <p>
     * Due to changes in API level 9.
     */
    boolean revertMenuIconColor();

    /**
     * Gets a {@link BluetoothSocket}.
     * <p>
     * Due to changes in API level 10.
     *
     * @param bluetoothDevice
     */
    BluetoothSocket getBluetoothSocket(BluetoothDevice bluetoothDevice) throws IOException;

    /**
     * Hides the title. If the platform supports the action bar, do nothing.
     * Ideally, with the action bar, we would like to collapse the navigation tabs
     * into the action bar. However, collapsing is not supported by the
     * compatibility library.
     * <p>
     * Due to changes in API level 11.
     *
     * @param activity the activity
     */
    void hideTitle(Activity activity);

    /**
     * Configures the action bar with the Home button as an Up button. If the
     * platform doesn't support the action bar, do nothing.
     * <p>
     * Due to changes in API level 11.
     *
     * @param activity the activity
     */
    void configureActionBarHomeAsUp(Activity activity);


    /**
     * Handles the search menu selection. Returns true if handled.
     * <p>
     * Due to changes in API level 11.
     *
     * @param activity the activity
     */
    boolean handleSearchMenuSelection(Activity activity);

    /**
     * Adds all items to an array adapter.
     * <p>
     * Due to changes in API level 11.
     *
     * @param arrayAdapter the array adapter
     * @param items list of items
     */
    <T> void addAllToArrayAdapter(ArrayAdapter<T> arrayAdapter, List<T> items);

    /**
     * Invalidates the menu.
     * <p>
     * Due to changes in API level 11.
     */
    void invalidMenu(Activity activity);

    /**
     * Sets the tab background.
     * <p>
     * Due to changes in API level 11.
     *
     * @param tabWidget the tab widget
     */
    void setTabBackground(TabWidget tabWidget);

    /**
     * Returns true if has dialog title divider.
     * <p>
     * Due to changes in API level 11.
     */
    boolean hasDialogTitleDivider();

    /**
     * Sets title and subtitle.
     * <p>
     * Due to changes in API level 11;
     */
    void setTitleAndSubtitle(Activity activity, String title, String subtitle);

    /**
     * Handles the search key press. Returns true if handled.
     * <p>
     * Due to changes in API level 14.
     *
     * @param menu the search menu
     */
    boolean handleSearchKey(MenuItem menu);

    /**
     * Returns true if Google Feedback is available.
     * <p>
     * Due to changes in ApI level 14.
     */
    boolean isGoogleFeedbackAvailable();

    /**
     * Gets the app widget size.
     * <p>
     * Due to changes in API level 16.
     *
     * @param appWidgetManager the app widget manager
     * @param appWidgetId the app widget id
     */
    int getAppWidgetSize(AppWidgetManager appWidgetManager, int appWidgetId);

    /**
     * Sets the app widget size.
     * <p>
     * Due to changes in API level 16.
     *
     * @param appWidgetManager the app widget manager.
     * @param appWidgetId the app widgit id
     * @param size the size
     */
    void setAppWidgetSize(AppWidgetManager appWidgetManager, int appWidgetId, int size);

    /**
     * Removes the global layout listener.
     * <p>
     * Due to changes in API level 16.
     *
     * @param observer the observer
     * @param listener the listener
     */
    void removeGlobalLayoutListener(
            ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener);

    /**
     * Returns true if has location mode.
     * <p>
     * Due to changes in API level 19.
     */
    boolean hasLocationMode();
}