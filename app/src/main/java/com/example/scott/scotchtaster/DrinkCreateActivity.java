package com.example.scott.scotchtaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class DrinkCreateActivity extends AppCompatActivity {
    private EditText mPriceEditText;
    private EditText mTitleEditText;
    private Drink mDrink;
    private RatingBar mRatingBar;
    private static String root = null;
    private static String imageFolderPath = null;
    private String imageName = null;
    private List<String> mTags;
    private TextView mTagTextView;
    private String mDesc;
    private TagGroup mTagGroup;
    private ImageView mPictureView;
    private static Uri mFileUri = null;
    private static final int CAMERA_IMAGE_REQUEST=1;
    private static final int TAGS_CREATE_TAGS_CODE = 2;
    private static final int TAGS_CREATE_DESC_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_drink_activity);
        mPriceEditText = (EditText) findViewById(R.id.editTextPrice);
        mTitleEditText = (EditText) findViewById(R.id.editTextTitle);
        mTagTextView = (TextView) findViewById(R.id.title_tags);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mPictureView = (ImageView) findViewById(R.id.imageViewScotch);
        mDesc = "";
        if (savedInstanceState != null)
        {
            if (savedInstanceState.getString("PictureUri") != null && savedInstanceState.getString("PictureUri") != "" ) {
                mFileUri = Uri.parse(savedInstanceState.getString("PictureUri"));
                mPictureView.setImageURI(mFileUri);
            }
            if (savedInstanceState.getString("tags") != null ){
                mTags = savedInstanceState.getStringArrayList("Tags");
                mTagGroup.setTags(mTags);
                mTagTextView.setVisibility(View.VISIBLE);
            }
        }
        else
            mFileUri = Uri.parse("");

        mTagGroup = (TagGroup) findViewById(R.id.drinkTagGroup);
        mTags = new ArrayList<>();
        mPriceEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPriceEditText.getWindowToken(), 0);
                    mPriceEditText.clearFocus();
                    return true;
                } else
                    return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("PictureUri",mFileUri.toString());
        savedInstanceState.putStringArrayList("tags", (ArrayList<String>)mTags);
        // etc.
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_drink_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.add_tags: {
                Intent intent = new Intent(this, TagsActivity.class);
                Integer code = new Integer(TAGS_CREATE_TAGS_CODE);
                intent.putStringArrayListExtra("Tags",(ArrayList<String>)mTags);
                startActivityForResult(intent, code);
                break;
            }

            case R.id.add_description: {
                Intent intent = new Intent(this, DescriptionActivity.class);
                Integer code = new Integer(TAGS_CREATE_DESC_CODE);
                intent.putExtra("Desc",mDesc);
                startActivityForResult(intent, code);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddImage(View view) {
       captureImage();
    }

    public void captureImage() {

        ImageView imageView = (ImageView) findViewById(R.id.imageViewScotch);

        // fetching the root directory
        root = Environment.getExternalStorageDirectory().toString()
                + "/Your_Folder";

        // Creating folders for Image
        imageFolderPath = root + "/saved_images";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file
        String timestamp = String.valueOf(System.currentTimeMillis());
        imageName = "test"+timestamp+".png";

        // Creating image here

        File image = new File(imageFolderPath, imageName);

        mFileUri = Uri.fromFile(image);

        imageView.setTag(imageFolderPath + File.separator + imageName);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

        startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case CAMERA_IMAGE_REQUEST:
                    Bitmap bitmap = null;
                    try {
                        GetImageThumbnail getImageThumbnail = new GetImageThumbnail();
                        bitmap = getImageThumbnail.getThumbnail(mFileUri, this);
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }


                    mPictureView.setImageBitmap(bitmap);

                    break;
                case TAGS_CREATE_TAGS_CODE:
                    if (!data.getStringArrayListExtra("Tags").isEmpty())
                    {
                        mTags = data.getStringArrayListExtra("Tags");
                        mTagGroup.setTags(mTags);
                        mTagTextView.setVisibility(View.VISIBLE);

                    }
                    break;
                case TAGS_CREATE_DESC_CODE:
                    if(!data.getStringExtra("Desc").isEmpty())
                    {
                        mDesc = data.getStringExtra("Desc");
                    }
                    break;
                default:
                    Toast.makeText(this, "Something went wrong...",
                            Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    public void addDrinkButton(View view) {
        AddDrink();
    }
    public void AddDrink(){
        if (!mTitleEditText.getText().toString().isEmpty() && !mPriceEditText.getText().toString().isEmpty())
        {
            mDrink = new Drink(mTitleEditText.getText().toString(), Double.valueOf(mPriceEditText.getText().toString()),
                    mRatingBar.getRating(),mDesc,mTags.toArray(new String[mTags.size()]),mFileUri.toString());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Drink",mDrink);
            DrinkCreateActivity.this.setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
        else
            Toast.makeText(this, "please fill the fields",
                    Toast.LENGTH_SHORT).show();
    }
}
