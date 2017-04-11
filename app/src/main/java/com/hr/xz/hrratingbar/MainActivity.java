package com.hr.xz.hrratingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hr.xz.hrratingbar.view.HrRatingBar;

public class MainActivity extends AppCompatActivity {

    private HrRatingBar mRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRb = (HrRatingBar) findViewById(R.id.my_rating_bar);
        mRb.setShowHalf(true);
        mRb.setRatingChangeListener(new HrRatingBar.OnRatingChangListener() {
            @Override
            public void onRatingChange(float rating) {
                Toast.makeText(MainActivity.this, rating + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
