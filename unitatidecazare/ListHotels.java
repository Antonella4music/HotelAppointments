package com.example.antonellab.unitatidecazare;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.ListActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by AntonellaB on 20-Oct-16.
 */

public class ListHotels extends ListActivity {

    private ListView listView;
    HotelsDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.addNew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListHotels.this, NewHotel.class);
                startActivity(intent);
            }
        });

        dbHelper = new HotelsDBHelper(this);

        final Cursor cursor = dbHelper.getAllHotels();
        String [] columns = new String[] {
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_NAME,
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_ADDRESS,
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_WEBPAGE,
                HotelsDBSchema.HotelsTable.HOTEL_COLUMN_PHONE
        };
        int [] widgets = new int[] {
                R.id.hName,
                R.id.hAddress,
                R.id.hWebpage,
                R.id.hPhone
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.hotel_list, cursor, columns, widgets, 0);
        listView = (ListView)findViewById(android.R.id.list);
        listView.setAdapter(cursorAdapter);

    }

    public void openWeb(View v)
    {
        ViewParent relativeLayout = v.getParent();
        ViewParent linearLayout = relativeLayout.getParent();

        TextView tvPhone = (TextView) ((View) linearLayout).findViewById(R.id.hWebpage);
        String url = tvPhone.getText().toString();
        //Toast.makeText(ListHotels.this, url, Toast.LENGTH_SHORT).show();

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void callPhone(View v)
    {
        ViewParent relativeLayout = v.getParent();
        ViewParent linearLayout = relativeLayout.getParent();

        TextView tvPhone = (TextView) ((View) linearLayout).findViewById(R.id.hPhone);
        String phoneNumber = tvPhone.getText().toString();
        //Toast.makeText(ListHotels.this, phoneNumber, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showMap(View v) {
        ViewParent relativeLayout = v.getParent();
        ViewParent linearLayout = relativeLayout.getParent();

        TextView tvMap = (TextView) ((View) linearLayout).findViewById(R.id.hAddress);
        String text = tvMap.getText().toString();

        String[] coordArr = text.split(",");
        for (int i = 0 ; i < coordArr.length; i++ )
        {
            coordArr[i]= coordArr[i];
            //Toast.makeText(ListHotels.this,  coordArr[i], Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" + coordArr[0] + "," + coordArr[1]));
            startActivity(intent);
        }

    }

}
