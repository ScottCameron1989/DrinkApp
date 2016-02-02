package com.example.scott.scotchtaster;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DescriptionActivity extends AppCompatActivity {
    private EditText mDescEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        mDescEditText = (EditText) findViewById(R.id.description);

        if ( getIntent().getExtras() != null){
           mDescEditText.setText(getIntent().getStringExtra("Desc"));
        }
    }

    public void addDescriptionButton(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Desc",mDescEditText.getText().toString());
        DescriptionActivity.this.setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
