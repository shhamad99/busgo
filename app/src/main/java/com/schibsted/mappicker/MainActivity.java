package com.schibsted.mappicker;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.schibstedspain.leku.LekuPoi;
import com.schibstedspain.leku.LocationPicker;
import com.schibstedspain.leku.LocationPickerActivity;
import com.schibstedspain.leku.tracker.LocationPickerTracker;
import com.schibstedspain.leku.tracker.TrackEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StrictMode.setThreadPolicy(
        new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    setContentView(R.layout.activity_main);
    View mapButton = findViewById(R.id.map_button);
    mapButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LocationPickerActivity.class);
        intent.putExtra(LocationPickerActivity.LATITUDE, 26.9);
        intent.putExtra(LocationPickerActivity.LONGITUDE, 75.7);
        //intent.putExtra(LocationPickerActivity.LAYOUTS_TO_HIDE, "street|city"); // this is optional if you want to hide some info
        //intent.putExtra(LocationPickerActivity.SEARCH_ZONE, "es_ES"); // this is optional if an specific search location
        //intent.putExtra(LocationPickerActivity.BACK_PRESSED_RETURN_OK, true);
          // this is optional if you want to return RESULT_OK if you don't set the latitude/longitude and click back button
        intent.putExtra("test", "this is a test");
        startActivityForResult(intent, 1);
      }
    });

    View mapPoisButton = findViewById(R.id.map_button_with_pois);
    mapPoisButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LocationPickerActivity.class);
        intent.putExtra(LocationPickerActivity.LATITUDE, 41.4036299);
        intent.putExtra(LocationPickerActivity.LONGITUDE, 2.1743558);
        List<LekuPoi> pois = getLekuPois();
        intent.putExtra(LocationPickerActivity.POIS_LIST, new ArrayList<>(pois));
        startActivityForResult(intent, 2);
      }
    });

    initializeLocationPickerTracker();
  }

  public List<LekuPoi> getLekuPois() {
    List<LekuPoi> pois = new ArrayList<>();

    Location locationPoi1 = new Location("leku");
    locationPoi1.setLatitude(41.4036339);
    locationPoi1.setLongitude(2.1721618);
    LekuPoi poi1 = new LekuPoi(UUID.randomUUID().toString(), "Los bellota", locationPoi1);
    pois.add(poi1);

    Location locationPoi2 = new Location("leku");
    locationPoi2.setLatitude(41.4023265);
    locationPoi2.setLongitude(2.1741417);
    LekuPoi poi2 = new LekuPoi(UUID.randomUUID().toString(), "Starbucks", locationPoi2);
    poi2.setAddress("Pla??a de la Sagrada Fam??lia, 19, 08013 Barcelona");
    pois.add(poi2);

    return pois;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      Log.d("RESULT****", "OK");
      if (requestCode == 1) {
        double latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
        Log.d("LATITUDE****", String.valueOf(latitude));
        double longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
        Log.d("LONGITUDE****", String.valueOf(longitude));
        String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
        Log.d("ADDRESS****", String.valueOf(address));
        String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);
        Log.d("POSTALCODE****", String.valueOf(postalcode));
        Bundle bundle = data.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
        Log.d("BUNDLE TEXT****", bundle.getString("test"));
        Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
        Log.d("FULL ADDRESS****", fullAddress.toString());
      } else if (requestCode == 2) {
        double latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
        Log.d("LATITUDE****", String.valueOf(latitude));
        double longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
        Log.d("LONGITUDE****", String.valueOf(longitude));
        String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
        Log.d("ADDRESS****", String.valueOf(address));
        LekuPoi lekuPoi = data.getParcelableExtra(LocationPickerActivity.LEKU_POI);
        Log.d("LekuPoi****", String.valueOf(lekuPoi));
      }
    }
    if (resultCode == RESULT_CANCELED) {
      Log.d("RESULT****", "CANCELLED");
    }
  }

  private void initializeLocationPickerTracker() {
    LocationPicker.setTracker(new LocationPickerTracker() {
      @Override
      public void onEventTracked(TrackEvents event) {
        //Toast.makeText(MainActivity.this, "Event: " + event.getEventName(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}
