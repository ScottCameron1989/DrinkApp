package com.example.scott.scotchtaster;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class DrinkViewActivity extends AppCompatActivity {
    private TextView mPriceEditText;
    private TextView mTitleEditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_view);

        mPriceEditText = (TextView) findViewById(R.id.viewTextPrice);
        mTitleEditText = (TextView) findViewById(R.id.viewTextName);
        mTagTextView = (TextView) findViewById(R.id.view_title_tags);
        mRatingBar = (RatingBar) findViewById(R.id.viewRatingBar);
        mPictureView = (ImageView) findViewById(R.id.viewImage);
        mTagGroup = (TagGroup) findViewById(R.id.viewDrinkTagGroup);

        if (getIntent().getExtras() != null) {
            mDrink = getIntent().getParcelableExtra("Drink");
            mPriceEditText.setText(String.valueOf(mDrink.getPrice()));
            mTitleEditText.setText(mDrink.getNom());
            mRatingBar.setRating(mDrink.getRating());
            if(mDrink.getTags()!=null && mDrink.getTags().length >0)
                mTagGroup.setTags(mDrink.getTags());
            if (mDrink.getPicture()!= "")
                mPictureView.setImageURI(Uri.parse(mDrink.getPicture()));
        }
    }
}
