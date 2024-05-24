package com.example.marvelstudios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelstudios.adaptadores.SuperheroAdaptador;
import com.example.marvelstudios.clases.Superhero;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment {

    List<String> comicTitles = new ArrayList<>();
    Spinner spnComics;
    Button btnRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        btnRequest = view.findViewById(R.id.btnRequest);
        spnComics = view.findViewById(R.id.spnComics);
        loadInfo();
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedComic = spnComics.getSelectedItem().toString();
                if (selectedComic.isEmpty())
                    Toast.makeText(getContext(), "Por favor, seleccione un cómic", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Cómic seleccionado", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void loadInfo() {
        String url="https://gateway.marvel.com:443/v1/public/comics?ts=1&apikey=95de3fa89516f96fbb4ff8b6e89880b9&hash=e20fcd1090dd331853efd97dd122f323";
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
            JSONObject data = response.getJSONObject("data");
            for (int i = 0; i<data.getJSONArray("results").length(); i++){
                String title = data.getJSONArray("results").getJSONObject(i).getString("title");
                comicTitles.add(title);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, comicTitles);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Asignar el adaptador al Spinner
            spnComics.setAdapter(adapter);

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}