package com.example.scott.scotchtaster;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class TagsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText mTagEditText;
    private ArrayList<String> mTags;
    private TagGroup mTagGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        if (getIntent().getExtras() != null) {
            mTags = getIntent().getStringArrayListExtra("Tags");
            mTagGroup.setTags(mTags);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tags, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTagsButton(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putStringArrayListExtra("Tags", new ArrayList<String>(Arrays.asList(mTagGroup.getTags())));
        TagsActivity.this.setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
