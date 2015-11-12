package lab.acme.noviembre15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lab.acme.noviembre15.adapters.AttendantsAdapter;
import lab.acme.noviembre15.common.Common;
import lab.acme.noviembre15.models.Attendant;
import lab.acme.noviembre15.models.DrawerItem;


//REFS:
// http://valokafor.com/create-and-publish-your-first-android-app-part-2/


public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;

    public String HEADER_NAME = "Val Okafor";  //this shows your name in the navigation header
    public String HEADER_EMAIL = "valokafor@someemail.com";
    public int HEADER_IMAGE = 1; //we will change this later to point to a resource file

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout Drawer;
    private RecyclerView.Adapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<DrawerItem> dataList;

    private List<Attendant> mAttendantsList;
    private View mRootView;
	//private TextView versionCodeLabel;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = MainActivity.this;
        final Context ctxt = this;

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        // Inflate the layout for this fragment
        //mRootView = inflater.inflate(R.layout.fragment_attendance, container, false);




  //      android.support.design.widget.FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
  //      fab.setOnClickListener(new View.OnClickListener() {
  //          @Override
  //          public void onClick(View view) {
  //              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
  //                      .setAction("Action", null).show();
  //          }
  //      });
        
        //int intVersionCode = Common.getDeviceId(mContext);

        //--- text view---
      	TextView versionCodeLabel = (TextView) findViewById(R.id.text_id);
      	String msg = "Device ID: " + Common.getDeviceId(mContext);
      
      	// Display the Version Code in the UI	
    	versionCodeLabel.setText(msg);


        mRecyclerView = (RecyclerView) findViewById(R.id.attendants_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAttendantsList = new ArrayList<Attendant>();
        addTestGuessList();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //promptForAdd();
                Intent activityAdd;
                activityAdd = new Intent(mContext ,  AddAttendantActivity.class);
                startActivity(activityAdd);
            }
        });


        mAdapter = new AttendantsAdapter(mAttendantsList, mContext);
        mRecyclerView.setAdapter(mAdapter);





        final GestureDetector mGestureDetector =
                new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    //Drawer.closeDrawers();
                   // onTouchDrawer(recyclerView.getChildLayoutPosition(child));
                   // Snackbar.make(recyclerView, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    startActivity(new Intent(mContext , DetailActivity.class));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
              //  Snackbar.make(this, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });




    }

    private void addTestGuessList() {
        Attendant guest1 = new Attendant();
        guest1.setName("12 noviembre 2015");
        guest1.setEmail("Informes parcial");
        guest1.setProfileImageId(R.drawable.category_3);
        mAttendantsList.add(guest1);

        Attendant guest2 = new Attendant();
        guest2.setName("24 diciembre 2015");
        guest2.setEmail("Navidad");
        guest2.setProfileImageId(R.drawable.headshot_2);
        mAttendantsList.add(guest2);

        Attendant guest3 = new Attendant();
        guest3.setName("28 oct. 2015");
        guest3.setEmail("Viaje a Venecia");
        guest3.setProfileImageId(R.drawable.category_1);
        mAttendantsList.add(guest3);

        Attendant guest4 = new Attendant();
        guest4.setName("01 enero 2016");
        guest4.setEmail("La gran decisión");
        guest4.setProfileImageId(R.drawable.no_category);
        mAttendantsList.add(guest4);

        Attendant guest5 = new Attendant();
        guest5.setName("Sarah Domingo");
        guest5.setEmail("sarah@yahoo.com");
        guest5.setProfileImageId(R.drawable.headshot_5);
        mAttendantsList.add(guest5);

        Attendant guest6 = new Attendant();
        guest6.setName("22 mayo 2016");
        guest6.setEmail("Cualquier hecho");
        guest6.setProfileImageId(R.drawable.category_2);
        mAttendantsList.add(guest6);

        Attendant guest7 = new Attendant();
        guest7.setName("Chris VanHorn");
        guest7.setEmail("chris@worldmail.com");
        guest7.setProfileImageId(R.drawable.headshot_7);
        mAttendantsList.add(guest7);

        Attendant guest8 = new Attendant();
        guest8.setName("Frank Krueger");
        guest8.setEmail("frank@ymail.com");
        guest8.setProfileImageId(R.drawable.headshot_8);
        mAttendantsList.add(guest8);

        Attendant guest9 = new Attendant();
        guest9.setName("Bella Florentino");
        guest9.setEmail("bella@outlook.com");
        guest9.setProfileImageId(R.drawable.headshot_9);
        mAttendantsList.add(guest9);

        Attendant guest10 = new Attendant();
        guest10.setName("Donna Simons");
        guest10.setEmail("donna@company.com");
        guest10.setProfileImageId(R.drawable.headshot_10);
        mAttendantsList.add(guest10);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String mMsg = "Method onOptionsItemSelected";
        Log.i(LOG_TAG, mMsg);

        int id = item.getItemId();

        switch (id) {

            case (R.id.action_settings):
                Common.showAlertDialog(this, getString(R.string.app_name), "Your application version is: " + Common.getAppVersionCode(mContext) + ".", false);
                break;

            case (R.id.app_icon):
                Intent activityAppIcon;
                activityAppIcon = new Intent(this,  AppIconAct.class);
                startActivity(activityAppIcon);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
