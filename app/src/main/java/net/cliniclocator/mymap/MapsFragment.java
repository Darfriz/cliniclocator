package net.cliniclocator.mymap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import java.util.HashMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private HashMap<String, Marker> markerHashMap = new HashMap<>();

    MarkerOptions marker;
    Vector<MarkerOptions> markerOptions;

    LatLng alorsetar;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markerOptions = new Vector<>();
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.44, 100.20))
                .title("KLINIK DR WAN BALKIS")
                .snippet("Status: Open")
        );

        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.43, 100.20))
                .title("MEDIKLINIK PUTRA KANGAR")
                .snippet("Status: Open")
        );

        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.45, 100.19))
                .title("KLINIK DR HJ OTHMAN")
                .snippet("Status: Open")
        );
        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.43, 100.19))
                .title("QUALITAS HEALTH CLINIC MENON")
                .snippet("Status: Open")
        );

        markerOptions.add(new MarkerOptions()
                .position(new LatLng(6.43, 100.18))
                .title("UniMAPCARE Clinic")
                .snippet("Status: Open")
        );

        // Add more marker options as needed...

        alorsetar = new LatLng(6.12, 100.3755);
        marker = new MarkerOptions().position(alorsetar).title("Alor Setar").snippet("Cawangan di buka 7am-9pm");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        // Initialize the map view
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);


        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (MarkerOptions mark : markerOptions) {
            Marker marker = mMap.addMarker(mark);
            markerHashMap.put(mark.getTitle(), marker);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alorsetar, 8));
        enableMyLocation();

        // Highlight the nearest marker if location is available
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions nearestMarker = getNearestMarker(myLatLng);
                    if (nearestMarker != null) {
                        Marker oldMarker = markerHashMap.get(nearestMarker.getTitle());
                        if (oldMarker != null) {
                            oldMarker.remove();  // Remove the old marker
                        }

                        nearestMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        Marker updatedMarker = mMap.addMarker(nearestMarker);  // Add the updated marker
                        markerHashMap.put(nearestMarker.getTitle(), updatedMarker);  // Update the HashMap
                    }


                }
            });
        }

    }

    // Method to get the nearest marker
    private MarkerOptions getNearestMarker(LatLng myLatLng) {
        double minDistance = Double.MAX_VALUE;
        MarkerOptions nearestMarker = null;

        for (MarkerOptions markerOption : markerOptions) {
            LatLng markerLatLng = markerOption.getPosition();
            double distance = calculateDistance(myLatLng, markerLatLng);

            if (distance < minDistance) {
                minDistance = distance;
                nearestMarker = markerOption;
            }
        }

        return nearestMarker;
    }

    // Method to calculate distance between two LatLng points
    private double calculateDistance(LatLng point1, LatLng point2) {
        Location location1 = new Location("point1");
        location1.setLatitude(point1.latitude);
        location1.setLongitude(point1.longitude);

        Location location2 = new Location("point2");
        location2.setLatitude(point2.latitude);
        location2.setLongitude(point2.longitude);

        return location1.distanceTo(location2);
    }

    private void enableMyLocation() {
        String perms[] = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_NETWORK_STATE"};
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                Log.d("hafizxx", "permission granted");
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            Log.d("hafizxx", "permission denied");
            ActivityCompat.requestPermissions(requireActivity(), perms, 200);
        }
    }

}
