package com.example.marvelstudios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelstudios.clases.Superhero;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsFragment extends Fragment {

    TextView txtName, txtEmail, txtDate;
    Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtDate = view.findViewById(R.id.txtDate);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        loadInfo();
        loadInfo2();
        return view;
    }

    private void loadInfo() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");

        // String url="http://192.168.1.7:3100/api/users/" + userId; // ip mi casa
        // String url = "http://10.0.2.2:3100/api/users/" + userId;
        String url = "http://10.16.51.59:3100/api/users/" + userId; // ip uac
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    receiveResponse(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(requireContext());
        rq.add(myRequest);
    }

    private void receiveResponse(JSONObject response) {
        try {
            String name = response.getString("name");
            String email = response.getString("email");
            String date = response.getString("date");
            txtName.setText(name);
            txtEmail.setText(email);
            txtDate.setText(date);
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void logout() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        requireActivity().finish();
    }

    private void loadInfo2(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String date = sharedPreferences.getString("date", "");
        txtName.setText(name);
        txtEmail.setText(email);
        txtDate.setText(date);

    }
}