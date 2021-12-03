package br.com.luanemanuel.desafiotokenlab.controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import br.com.luanemanuel.desafiotokenlab.controller.data.MovieData;
import br.com.luanemanuel.desafiotokenlab.model.Movie;
import br.com.luanemanuel.desafiotokenlab.view.AboutActivity;

public class AboutController {

    private static final String TAG = "AboutActivity";
    private Movie selectedMovie;

    //Realiza o download do JSON contido na API e o converte no objeto Movie que será salvo na lista e no banco
    public void getMovieAbout(int id, AboutActivity activity){
        String movieAPIURL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies/" + id;

        for(Movie movie : MovieData.getMovieList()){
            if(movie.getId() == id){
                selectedMovie = movie;
                break;
            }
        }

        if (selectedMovie != null && selectedMovie.getOverview() != null) {
            activity.updateScreen(selectedMovie);
        }

        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, movieAPIURL, null, response -> {
            try{
                if(selectedMovie != null){
                    selectedMovie.setOverview(response.getString("overview"));
                    selectedMovie.setVotes(response.getString("vote_average"));
                    selectedMovie.setAdult(response.getBoolean("adult"));
                    selectedMovie.setReleaseDate(response.getString("release_date").replace("-", "/"));
                    MovieData.getMovieDataAccess().updateMovieOnDatabase(selectedMovie);
                    activity.updateScreen(selectedMovie);
                    selectedMovie = null;
                }

            }catch (JSONException e){
                Log.e(TAG, "Exception on get about movie >> ", e);
            }
        }, error -> {
            if(selectedMovie == null || selectedMovie.getOverview() == null){
                activity.setNoConnection(true);
            }
        });

        queue.add(request);
    }

}
