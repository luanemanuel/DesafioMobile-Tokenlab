package br.com.luanemanuel.desafiotokenlab.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.luanemanuel.desafiotokenlab.R;
import br.com.luanemanuel.desafiotokenlab.controller.MainController;
import br.com.luanemanuel.desafiotokenlab.controller.adapter.MovieAdapter;
import br.com.luanemanuel.desafiotokenlab.model.Movie;

public class MainActivity extends AppCompatActivity {

    private ListView movieList;
    private ProgressBar progressBar;

    public MovieAdapter movieAdapter;
    private final MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.movieList = findViewById(R.id.movieList);
        this.progressBar = findViewById(R.id.progressBar);

        movieAdapter = new MovieAdapter(this, R.layout.movie_layout, mainController.getMovieList());
        movieList.setAdapter(movieAdapter);

        mainController.getMovies(this);

        movieList.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = (Movie) movieList.getItemAtPosition(position);
            System.out.println("Movie ID >>> " + movie.getId());
        });
    }

    public void setProgressBarDisabled(boolean disabled){
        progressBar.setVisibility(disabled ? ProgressBar.GONE : ProgressBar.VISIBLE);
    }

}