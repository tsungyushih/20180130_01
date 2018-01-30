package com.frandog.a20180130_01;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);    //獲得定位
    }

    public void click1(View v) {        //點擊按鈕前記得先在模擬器開定位權限
        //下方if段落不用自己key，用下下一段的requestLocationUpdates帶出，意思為當手機沒有開定位權限時怎麼辦
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //可參考綠豆湯，6.0權限文章
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 321
            );
            return;
        } else {
            startLoc();
        }
    }
        //先KEY下行這段，然後在Manifest給權限，但還是會出現紅線錯誤，此時點選精靈的permission check，讓精靈自動帶出上方的if段落
//        之後把本行丟到下方的startLoc裡
//       lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyListener());     //參數為(定位手段,定位時間間格,定位距離間格,監聽器)


        //可參考綠豆湯
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 321)
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //取得權限，進行檔案存取
                    startLoc();
                } else {
                    //已有權限則甚麼都不做
                }
            }
        }


    //可參考綠豆湯
    private void startLoc() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyListener());        //參數為(定位手段,定位時間間格,定位距離間格,監聽器)
    }

    class MyListener implements LocationListener{   //監聽位置用，LocationListener為介面，要實作


        @Override
        public void onLocationChanged(Location location) {      //在模擬器的右下More(三個點)的Location可模擬調整座標
            Log.d("LOG","Change!!!"+location.getLatitude()+","+location.getLongitude());   //每當位置改變就跳出

            Location loc101 = new Location("LOC");      //provider隨便打，因為是自己的

            //台北101的經緯度
            loc101.setLatitude(25.0336);
            loc101.setLongitude(121.5646);

            //內建計算兩點間距離的方法
            float dist = location.distanceTo(loc101);

            Log.d("LOC","Dist"+dist);
        }

        //使用內建的Geocoder，就不會有每日2500次的限制問題
        Geocoder geocoder = new Geocoder(MainActivity.this);
            try {
            List<Address> mylist = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address addr = mylist.get(0);
            Log.d("LOC", addr.getAddressLine(0));

        } catch (IOException e) {
            e.printStackTrace();
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


    }


}


