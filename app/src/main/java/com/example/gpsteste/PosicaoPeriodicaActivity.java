package com.example.gpsteste;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class PosicaoPeriodicaActivity extends Activity {

	List<Location> locs = new ArrayList<Location>();
	LocationListener listener;

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posicao_periodica);


		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


		long tempo = 100; //1 minuto
		float distancia = 30; // 30 metros

		this.listener = new LocationListener() {

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
				TextView numero = (TextView) findViewById(R.id.numero);
				TextView latitude = (TextView) findViewById(R.id.latitude);
				TextView longitude = (TextView) findViewById(R.id.longitude);
				TextView time = (TextView) findViewById(R.id.time);
				TextView acuracy = (TextView) findViewById(R.id.Acuracy);

				if (location != null) {
					locs.add(location);
					numero.setText("Número de posições travadas: " + locs.size());
					latitude.setText("Latitude: " + location.getLatitude());
					longitude.setText("Longitude: " + location.getLongitude());
					acuracy.setText("Precisão: " + location.getAccuracy() + "");

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					time.setText("Data:" + sdf.format(location.getTime()));

				}

			}
		};

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
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tempo, distancia, this.listener, null);
		
	}
	
	public void parar(View v){
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		
		locationManager.removeUpdates( this.listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.posicao_periodica, menu);
		return true;
	}

}
