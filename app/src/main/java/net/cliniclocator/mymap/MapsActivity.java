package net.cliniclocator.mymap;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.cliniclocator.mymap.databinding.ActivityMapsBinding;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MarkerOptions marker;
    Vector<MarkerOptions> markerOptions;

    LatLng alorsetar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        markerOptions = new Vector<>();
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.44,100.20))
                .title("KLINIK DR WAN BALKIS")
                .snippet("Status: Open")
        );

        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.43,100.20))
                .title("MEDIKLINIK PUTRA KANGAR")
                .snippet("Status: Open")
        );

        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.45,100.19))
                .title("KLINIK DR HJ OTHMAN")
                .snippet("Status: Open")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.43,100.19))
                .title("QUALITAS HEALTH CLINIC MENON")
                .snippet("Status: Open")
        );

        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.43,100.18))
                .title("UniMAPCARE Clinic")
                .snippet("Status: Open")
        );




        alorsetar = new LatLng(6.12,100.3755);
        marker = new MarkerOptions().position(alorsetar).title("Alor Setar").snippet("Cawangan di buka 7am-9pm");

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                finish(); // Close the MapsActivity and go back to MainActivity
            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        //mMap.addMarker(marker);

        for (MarkerOptions mark : markerOptions){
            mMap.addMarker(mark);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alorsetar,8));
        enableMyLocation();

    }

    private void enableMyLocation() {

        String perms[] = {"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_NETWORK_STATE"};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                Log.d("Clinic Locator","permission granted");
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission

            Log.d("Clinic Locator","permission denied");
            ActivityCompat.requestPermissions(this,perms ,200);

        }
    }
}