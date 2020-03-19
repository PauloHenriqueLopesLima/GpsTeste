package com.example.gpsteste;

import java.text.SimpleDateFormat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class PosicaoUnicaActivity extends Activity {

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posicao_unica);


		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    Activity#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for Activity#requestPermissions for more details.
			return;
		}
		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {


			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				Toast.makeText(getApplicationContext(), "Status alterado", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onProviderEnabled(String arg0) {
				Toast.makeText(getApplicationContext(), "Provider Habilitado", Toast.LENGTH_LONG).show();

			}

			@Override
			public void onProviderDisabled(String arg0) {
				Toast.makeText(getApplicationContext(), "Provider Desabilitado", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onLocationChanged(Location location) {
				TextView latitude = (TextView) findViewById(R.id.latitude);
				TextView longitude = (TextView) findViewById(R.id.longitude);
				TextView time = (TextView) findViewById(R.id.time);
				TextView acuracy = (TextView) findViewById(R.id.Acuracy);
				TextView provider = (TextView) findViewById(R.id.provider);

				if (location != null) {
					Log.i("GPS", "location ok");
					latitude.setText("Latitude: " + location.getLatitude());
					longitude.setText("Longitude: " + location.getLongitude());
					acuracy.setText("Precisão: " + location.getAccuracy() + "");

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					time.setText("Data:" + sdf.format(location.getTime()));
					provider.setText(location.getProvider());
				}

			}
		}, null);
	}

}
