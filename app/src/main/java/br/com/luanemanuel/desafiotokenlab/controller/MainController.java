package br.com.luanemanuel.desafiotokenlab.controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.luanemanuel.desafiotokenlab.controller.data.MovieData;
import br.com.luanemanuel.desafiotokenlab.model.db.MovieDataAccess;
import br.com.luanemanuel.desafiotokenlab.model.Movie;
import br.com.luanemanuel.desafiotokenlab.view.MainActivity;

public class MainController {

    private static final String TAG = "MainActivity";
    public MovieDataAccess movieDataAccess;

    //Realiza o download do JSON contido na API e o converte no objeto Movie que será salvo na lista e no banco
    public void getMovies(MainActivity activity){
        String movieAPIURL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies";
        movieDataAccess = new MovieDataAccess(activity);
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, movieAPIURL, null, response -> {
            try{
                MovieData.getMovieList().clear();
                if(response.length() > 0){
                    for(int i = 0; i < response.length(); i++){
                        Movie movie = jsonToMovie(response.getJSONObject(i));
                        movieDataAccess.insertMovieToDatabase(movie);
                        MovieData.getMovieList().add(movie);
                    }
                }
                activity.setProgressBarDisabled(true);
                activity.movieAdapter.notifyDataSetChanged();
            }catch (JSONException e){
                Log.e(TAG, "Exception on get movies >> ", e);
            }
        }, error -> {
            if(MovieData.getMovieList().size() <= 0){
                activity.setNoConnection(true);
            }else{
                activity.setProgressBarDisabled(true);
            }

            activity.movieAdapter.notifyDataSetChanged();
        });

        queue.add(request);
    }

    //Realiza a atualização da pagina de filmes
    public void refreshMovies(MainActivity activity){
        activity.setProgressBarDisabled(false);
        MovieData.getMovieList().clear();
        movieDataAccess.clearMovieDatabase();
        getMovies(activity);
    }

    //Conversor JSON para objeto Movie
    private Movie jsonToMovie(JSONObject obj) throws JSONException {
        Movie movie = new Movie();
        movie.setId(obj.getInt("id"));
        movie.setTitle(obj.getString("title"));
        movie.setPosterURL(obj.getString("poster_url"));
        return movie;
    }
}
