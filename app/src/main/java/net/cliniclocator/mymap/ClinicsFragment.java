package net.cliniclocator.mymap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClinicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinicsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Clinic 1 GPS coordinates
    private static final double CLINIC1_LATITUDE = 6.44;
    private static final double CLINIC1_LONGITUDE = 100.20;

    // Clinic 2 GPS coordinates
    private static final double CLINIC2_LATITUDE = 6.43;
    private static final double CLINIC2_LONGITUDE = 100.20;

    // Clinic 3 GPS coordinates
    private static final double CLINIC3_LATITUDE = 6.45;
    private static final double CLINIC3_LONGITUDE = 100.19;

    // Clinic 4 GPS coordinates
    private static final double CLINIC4_LATITUDE = 6.43;
    private static final double CLINIC4_LONGITUDE = 100.19;

    // Clinic 5 GPS coordinates
    private static final double CLINIC5_LATITUDE = 6.43;
    private static final double CLINIC5_LONGITUDE = 100.18;

    // User's latitude and longitude
    private double userLatitude;
    private double userLongitude;

    public ClinicsFragment() {
        // Required empty public constructor
    }

    public static ClinicsFragment newInstance(String param1, String param2) {
        ClinicsFragment fragment = new ClinicsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Initialize user's location
        getUserLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clinics, container, false);

        // Display distance from Clinic 1 to user
        displayDistance(view, CLINIC1_LATITUDE, CLINIC1_LONGITUDE, R.id.distanceTextView1);

        // Display distance from Clinic 2 to user
        displayDistance(view, CLINIC2_LATITUDE, CLINIC2_LONGITUDE, R.id.distanceTextView2);

        // Display distance from Clinic 3 to user
        displayDistance(view, CLINIC3_LATITUDE, CLINIC3_LONGITUDE, R.id.distanceTextView3);

        // Display distance from Clinic 4 to user
        displayDistance(view, CLINIC4_LATITUDE, CLINIC4_LONGITUDE, R.id.distanceTextView4);

        // Display distance from Clinic 5 to user
        displayDistance(view, CLINIC5_LATITUDE, CLINIC5_LONGITUDE, R.id.distanceTextView5);

        return view;
    }

    private void getUserLocation() {
        // Get the user's location
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            // Check location permission
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // Get the last known location
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    userLatitude = lastKnownLocation.getLatitude();
                    userLongitude = lastKnownLocation.getLongitude();
                } else {
                    Toast.makeText(requireContext(), "Unable to retrieve user's location", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayDistance(View view, double clinicLatitude, double clinicLongitude, int textViewId) {
        // Calculate the distance between clinic and user
        float[] results = new float[1];
        Location.distanceBetween(userLatitude, userLongitude, clinicLatitude, clinicLongitude, results);

        // Convert meters to kilometers
        double distanceInKm = results[0] / 1000.0;

        // Display the distance in a TextView
        TextView distanceTextView = view.findViewById(textViewId);
        distanceTextView.setText(String.format("Distance to Clinic: %.2f km", distanceInKm));
    }
}

