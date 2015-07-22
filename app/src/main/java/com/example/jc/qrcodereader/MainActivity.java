package com.example.jc.qrcodereader;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MyActivity";
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Camera.open();
        readQRCode();
    }


    private void readQRCode() {

        Log.d(TAG, "readQRCode() entry");
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);

        scanIntegrator.addExtra("SCAN_WIDTH", 800);
        scanIntegrator.addExtra("SCAN_HEIGHT", 200);
        scanIntegrator.addExtra("RESULT_DISPLAY_DURATION_MS", 90000L);
        scanIntegrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
        scanIntegrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);


    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "onActivityResult");
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            Log.d(TAG, "got a scan result");
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            TextView myText = (TextView)findViewById(R.id.myText);
            myText.setText(scanContent);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No Scan data", Toast.LENGTH_SHORT);
            toast.show();
        }

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
