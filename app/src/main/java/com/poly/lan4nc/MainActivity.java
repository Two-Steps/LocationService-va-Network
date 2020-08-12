package com.poly.lan4nc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView location1, location2;
    Button btn_loaction, btn_connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location1 = findViewById(R.id.location1);
        location2 = findViewById(R.id.location2);
        btn_loaction = findViewById(R.id.btn_location);
        btn_connection = findViewById(R.id.btn_conection);
        btn_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConection();
            }
        });
        btn_loaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGPS(v);
            }
        });
    }

    public void getGPS(View view) {
        // bước 1
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // hàm tự sinh ra khi làm bước 2
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // bước 3: viết câu lệnh xin quyền tại đây
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    304);
            return;
        }
        // bước 2
        // gọi locationManager.requestLocationUpdates để cập nhật vị trí
        // có thể thay NETWORK_PROVIDER bằng GPS_PROVIDER
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                location1.setText("Altitude: " + location.getAltitude());
                location2.setText("Longtitude: " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    public void checkConection() {
        // bước 1 sử dụng lớp connectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // bước 2 lấy kiểu kết nối
        NetworkInfo isWiFi = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI),
                is4G = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if (isWiFi.isConnected()) {
            Toast.makeText(MainActivity.this, "Using WiFi", Toast.LENGTH_SHORT).show();
        }
        if (is4G.isConnected()) {
            Toast.makeText(MainActivity.this, "Using 4G", Toast.LENGTH_SHORT).show();
        }
    }
}
