package br.com.luanemanuel.desafiotokenlab.controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.luanemanuel.desafiotokenlab.model.Movie;
import br.com.luanemanuel.desafiotokenlab.view.MainActivity;

public class MainController {

    private final List<Movie> movieList = new ArrayList<>();
    private static final String TAG = "MainActivity";

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void getMovies(MainActivity activity){
        String movieAPIURL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies";

        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, movieAPIURL, null, response -> {
            try{
                for(int i = 0; i < response.length(); i++){
                    movieList.add(jsonToMovie(response.getJSONObject(i)));
                }
                activity.movieAdapter.notifyDataSetChanged();
                activity.setProgressBarDisabled(true);
            }catch (JSONException e){
                Log.e(TAG, "Exception on get movies >> ", e);
            }
        }, error -> Log.e(TAG, "Error on get movies >> " + error));

        queue.add(request);
    }

    private Movie jsonToMovie(JSONObject obj) throws JSONException {
        Movie movie = new Movie();
        movie.setId(obj.getInt("id"));
        movie.setTitle(obj.getString("title"));
        movie.setPosterURL(obj.getString("poster_url"));
        return movie;
    }
}
