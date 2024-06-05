package com.example.marvelstudios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelstudios.adaptadores.SuperheroAdaptador;
import com.example.marvelstudios.clases.Comic;
import com.example.marvelstudios.clases.Superhero;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment {

    List<String> comicTitles = new ArrayList<>();
    List<Comic> comicList = new ArrayList<>();
    Spinner spnComics;
    Button btnRequest;
    TextView txtComic;
    ImageView imgComic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        btnRequest = view.findViewById(R.id.btnRequest);
        spnComics = view.findViewById(R.id.spnComics);
        txtComic = view.findViewById(R.id.txtComic);
        imgComic = view.findViewById(R.id.imgComic);
        loadInfo();
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = spnComics.getSelectedItemPosition();
                if (selectedPosition==0)
                    Toast.makeText(getContext(), "Por favor, seleccione un cómic", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getContext(), "Cómic seleccionado", Toast.LENGTH_SHORT).show();
                    Comic selectedComic = comicList.get(selectedPosition - 1);
                    txtComic.setText(selectedComic.getTitle());
                    Picasso.get().load(selectedComic.getImage()).into(imgComic);
                    txtComic.setVisibility(View.VISIBLE);
                    imgComic.setVisibility(View.VISIBLE);
                }
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
            comicTitles.add("Selecciona un cómic");
            JSONObject data = response.getJSONObject("data");
            for (int i = 0; i<data.getJSONArray("results").length(); i++){
                String title = data.getJSONArray("results").getJSONObject(i).getString("title");
                comicTitles.add(title);
                String image = data.getJSONArray("results").getJSONObject(i).getJSONObject("thumbnail").getString("path") + "." + data.getJSONArray("results").getJSONObject(i).getJSONObject("thumbnail").getString("extension");
                Comic c = new Comic(title, image);
                comicList.add(c);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_style, comicTitles);
            adapter.setDropDownViewResource(R.layout.spinner_style2);
            spnComics.setAdapter(adapter);

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}