package br.com.luanemanuel.desafiotokenlab.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.luanemanuel.desafiotokenlab.R;
import br.com.luanemanuel.desafiotokenlab.controller.adapter.MovieAdapter;
import br.com.luanemanuel.desafiotokenlab.model.Movie;

public class MainActivity extends AppCompatActivity {

    private ListView movieList;
    //private ProgressBar progressBar;

    private List<Movie> movie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.movieList = findViewById(R.id.movieList);
        //this.progressBar = findViewById(R.id.progressBar);

        MovieAdapter movieAdapter = new MovieAdapter(this, R.layout.movie_layout, movie);
        movieList.setAdapter(movieAdapter);

        for(int i = 0; i< 10; i++){
            Movie movie1 = new Movie();
            movie1.setTitle("Miranha");
            movie1.setPosterURL("https://img.olhardigital.com.br/wp-content/uploads/2021/11/Homem-Aranha-819x1024.jpg");
            movie.add(movie1);
            Movie movie2 = new Movie();
            movie2.setTitle("teste");
            movie2.setPosterURL("https://image.tmdb.org/t/p/w200/xq1Ugd62d23K2knRUx6xxuALTZB.jpg");
            movie.add(movie2);
        }

        movieAdapter.notifyDataSetChanged();
    }


}