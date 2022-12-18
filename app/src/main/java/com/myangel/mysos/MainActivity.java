package com.myangel.mysos;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText editBox, phoneNum;
    private TextView txtHeader;

    private LocationManager lm;
    private Location lc;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNum = (EditText) findViewById(R.id.editPhone);
        editBox = (EditText) findViewById(R.id.txtEdit);
        txtHeader = (TextView) findViewById(R.id.txtHeader);

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    public void onGPSClick(View v) {
        Intent it = new Intent(MainActivity.this, VideoActivity.class);
        startActivity(it);
    }

    public void onVideoClick(View v) {
        Intent it = new Intent(MainActivity.this, VideoActivity.class);
        startActivity(it);
    }

    public void onMapClick(View v) {
        Intent it = new Intent(MainActivity.this, GoogleMapActivity.class);
        startActivity(it);
    }

    public void onDialClick(View v)
    {
         Uri uri = Uri.parse("tel:" + phoneNum.getText().toString());
         Intent intent = new Intent(Intent.ACTION_DIAL, uri);
         startActivity(intent);
    }

    public void setInMotion(View v){
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        StringBuilder infoGPS = new StringBuilder("Available location providers:\n");
        for (String s : lm.getAllProviders()) infoGPS.append(s + "\n");
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, );

        //editBox.setText(infoGPS.toString());
        //txtHeader.setText(infoGPS.toString());

            /*
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            */

        boolean bGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Toast.makeText(getApplicationContext(), "GPS status: " + bGPSEnabled, Toast.LENGTH_LONG).show();

        //lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locationTrack = new LocationTrack(MainActivity.this);

        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_LONG).show();
        }

        String infoLocation = showLocation();
        editBox.setText(infoGPS.toString() + "\n\n\n" + infoLocation);
    }

    private String showLocation() {
        StringBuilder sb = new StringBuilder("GPS coordinates:\n\n");
        String[] provides = {
                LocationManager.GPS_PROVIDER,
                LocationManager.FUSED_PROVIDER,
                LocationManager.PASSIVE_PROVIDER,
                LocationManager.NETWORK_PROVIDER};

        for(String p : provides ){
            lc = lm.getLastKnownLocation(p);
            if( lc != null) {
                SOSApp.getInstance().currentLocation = lc;
                sb.append( p + "\n" + composeLocation(lc) + "\n\n");
            }
        }
        return sb.toString();
    }

    private String composeLocation(Location location) {
        StringBuilder sb = new StringBuilder("");
        sb.append("当前的位置信息：\n");
        sb.append("经度Longitude：" + location.getLongitude() + "\n");
        sb.append("纬度Latitude：" + location.getLatitude() + "\n");
        sb.append("高度：" + location.getAltitude() + "\n");
        sb.append("速度：" + location.getSpeed() + "\n");
        sb.append("方向：" + location.getBearing() + "\n");
        sb.append("定位精度：" + location.getAccuracy() + "\n");
        return sb.toString();
    }

}