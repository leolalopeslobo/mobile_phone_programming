package com.example.earthquakeviewer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toolbar;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
public class RSSFeedActivity extends ListActivity {
    private ProgressBar pDialog;
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<>();
    RSSParser rssParser = new RSSParser();
    Toolbar toolbar;
    List<RSSItem> rssItems = new ArrayList<>();
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);
        String rss_link = getIntent().getStringExtra("rssLink");
        new LoadRSSFeedItems().execute(rss_link);
        ListView lv = getListView();
    }
    public class LoadRSSFeedItems extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBar(RSSFeedActivity.this, null, android.R.attr.progressBarStyleLarge);
            RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            pDialog.setLayoutParams(lp);
            pDialog.setVisibility(View.VISIBLE);
            relativeLayout.addView(pDialog);
        }
        @Override
        protected String doInBackground(String... args) {
            //rss link url
            String rss_url = args[0];
            //list of rss items
            rssItems = rssParser.getRSSFeedItems(rss_url);
            //looping through each item
            for(RSSItem item : rssItems) {
                //creating new HashMap
                if(item.link.toString().equals(""))
                    break;
                HashMap<String, String> map = new HashMap<String, String>();
                //adding each child node to HashMap => value
                String givenDateString = item.pubdate.trim();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                try {
                    Date mDate = sdf.parse(givenDateString);
                    SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE, dd MMMM yyyy - hh:mm a", Locale.US);
                    item.pubdate = sdf2.format(mDate);
                }
                catch(ParseException e) {
                    e.printStackTrace();
                }
                map.put(TAG_TITLE,item.title);
                map.put(TAG_LINK,item.link);
                map.put(TAG_PUB_DATE,item.pubdate); //if you want to parse the date
                //adding HashList to ArrayList
                rssItemList.add(map);
            }
            //updating the UI form Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            RSSFeedActivity.this,
                            rssItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_LINK, TAG_TITLE, TAG_PUB_DATE},
                            new int[]{R.id.page_url, R.id.title, R.id.pub_date});
                    //updating listview
                    setListAdapter(adapter);
                }
            });
            return null;
        }
        protected void onPostExecute(String args) {
            pDialog.setVisibility(View.GONE);
        }
    }
}

