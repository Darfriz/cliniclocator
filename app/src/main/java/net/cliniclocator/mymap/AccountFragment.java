package net.cliniclocator.mymap;
import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import net.cliniclocator.mymap.R;

public class AccountFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextNewUsername;
    private EditText editTextNewPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonSwitch;

    private LinearLayout loginLayout;
    private LinearLayout registerLayout;

    private boolean isLoginView = true; // Default view is login

    private static final String PREFS_NAME = "MyPrefsFile";

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize UI elements
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextNewUsername = view.findViewById(R.id.editTextNewUsername);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonSwitch = view.findViewById(R.id.buttonSwitch);
        loginLayout = view.findViewById(R.id.loginLayout);
        registerLayout = view.findViewById(R.id.registerLayout);

        // Set click listeners
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });

        // Toggle between login and register views
        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleViews();
            }
        });

        return view;
    }

    private void toggleViews() {
        if (isLoginView) {
            // Switch to register view
            loginLayout.setVisibility(View.GONE);
            registerLayout.setVisibility(View.VISIBLE);
            buttonSwitch.setText(getString(R.string.switch_to_login));
        } else {
            // Switch to login view
            loginLayout.setVisibility(View.VISIBLE);
            registerLayout.setVisibility(View.GONE);
            buttonSwitch.setText(getString(R.string.switch_to_register));
        }

        // Toggle the flag
        isLoginView = !isLoginView;
    }

    private void handleLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check if username and password are not empty
        if (!username.isEmpty() && !password.isEmpty()) {
            // Retrieve stored username and password from SharedPreferences
            SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String storedUsername = preferences.getString("username", "");
            String storedPassword = preferences.getString("password", "");

            // Check if the input credentials match the stored credentials
            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                // Login successful
                showMessage("Login successful!");
                // Add additional logic here, such as navigating to a different screen
            } else {
                // Login failed
                showMessage("Invalid username or password. Please try again.");
            }
        } else {
            // Username or password is empty
            showMessage("Please enter both username and password.");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    private void handleRegister() {
        String newUsername = editTextNewUsername.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        // Implement your register logic here
        // For simplicity, we'll store the data in SharedPreferences
        saveUserData(newUsername, newPassword);
    }

    private void saveUserData(String username, String password) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        showMessage("User registered successfully!");
    }


}
