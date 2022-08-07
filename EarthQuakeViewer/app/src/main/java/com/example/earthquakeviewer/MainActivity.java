package com.example.earthquakeviewer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> rssLinks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRediff = findViewById(R.id.btnRediff);
        btnRediff.setOnClickListener(this);
        rssLinks.add("https://quakes.bgs.ac.uk/feeds/WorldSeismology.xml");//adding the first link
        rssLinks.add("https://www.emsc-csem.org/service/rss/rss.php?typ=emsc");
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnRediff:
                //is it necessary to add a switch case since we have only one case???
                //shift from main activity to rss activity using intents
                startActivity(new Intent(MainActivity.this,RSSFeedActivity.class).putExtra("rssLink",rssLinks.get(0)));//reads the first rsslink
                break;
        }
    }
}
