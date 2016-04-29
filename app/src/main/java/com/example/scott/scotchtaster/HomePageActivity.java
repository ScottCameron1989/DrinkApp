package com.example.scott.scotchtaster;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Drink> drinks;
    private String fileName = "drinks.txt";
    private RatingBar mRatingBar;
    private TextView emptyView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Control control = new Control();
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        if (savedInstanceState != null)
        {
            drinks = savedInstanceState.getParcelableArrayList("drinks");
        }
        else {
            drinks = new ArrayList<Drink>();
            VerifyStoragePermissions(HomePageActivity.this);
            if( control.getDrinks(HomePageActivity.this) != null)
            {
                drinks = control.getDrinks(HomePageActivity.this);
            }
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewTags);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DrinkAdapter(drinks);
        mRecyclerView.setAdapter(mAdapter);

        emptyView = (TextView) findViewById(R.id.text_empty);
        ActivateEmptyTextView();

//        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
//                drinks.remove(position);
//                mAdapter.notifyDataSetChanged();
//                ActivateEmptyTextView();
//                return true;
//            }
//        });

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public boolean onItemClicked(RecyclerView recyclerView, int position, View v) {
                Drink drink = drinks.get(position);
                StartDrinkViewActivity(drink);
                return true;
            }
        });
//
//        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.right_drawer);
//
//        // Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item, mPlanetTitles));
//        // Set the list's click listener
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putParcelableArrayList("drinks", drinks);
        // etc.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void StartDrinkCreationActivity(View view) {
        Intent intent = new Intent(this,DrinkCreateActivity.class);
        Integer code = new Integer(0);
        startActivityForResult(intent,code);
    }
    public void StartDrinkViewActivity(Drink drink){
        Intent intent = new Intent(this,DrinkViewActivity.class);
        Integer code = new Integer(0);
        intent.putExtra("Drink",drink);
        startActivityForResult(intent, code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Make sure the request was successful
        if (resultCode == RESULT_OK) {
            Drink drink = (Drink)data.getParcelableExtra("Drink");
            drinks.add(drink);
            mAdapter.notifyDataSetChanged();
            ActivateEmptyTextView();

            // Do something with the contact here (bigger example below)
        }
    }
    @Override
    public void onBackPressed() {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        VerifyStoragePermissions(HomePageActivity.this);
        control.saveDrinks(HomePageActivity.this,drinks);
        finish();
    }
    public void ActivateEmptyTextView()
    {
        if (drinks == null || drinks.isEmpty())
            emptyView.setVisibility(View.VISIBLE);
        else
            emptyView.setVisibility(View.INVISIBLE);
    }
    public static void VerifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}