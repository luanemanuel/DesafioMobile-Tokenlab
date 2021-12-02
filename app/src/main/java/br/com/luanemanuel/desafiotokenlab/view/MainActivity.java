package br.com.luanemanuel.desafiotokenlab.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.luanemanuel.desafiotokenlab.R;
import br.com.luanemanuel.desafiotokenlab.controller.MainController;
import br.com.luanemanuel.desafiotokenlab.controller.data.MovieData;
import br.com.luanemanuel.desafiotokenlab.controller.adapter.MovieAdapter;
import br.com.luanemanuel.desafiotokenlab.controller.db.MovieDataAccess;
import br.com.luanemanuel.desafiotokenlab.model.Movie;

public class MainActivity extends AppCompatActivity {

    private ListView movieList;
    private ProgressBar progressBar;
    private TextView noConnection;
    private SwipeRefreshLayout refreshMain;

    public MovieAdapter movieAdapter;
    private final MainController mainController = new MainController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.movieList = findViewById(R.id.movieList);
        this.progressBar = findViewById(R.id.progressBar);
        this.noConnection = findViewById(R.id.noConnection);
        this.refreshMain = findViewById(R.id.refreshMain);

        setProgressBarDisabled(false);

        MovieData.setMovieDataAccess(new MovieDataAccess(this));
        MovieData.setMovieList(MovieData.getMovieDataAccess().selectAllMoviesFromDatabase());
        mainController.getMovies(this);

        movieAdapter = new MovieAdapter(this, R.layout.movie_layout, MovieData.getMovieList());
        movieList.setAdapter(movieAdapter);

        movieList.setOnItemClickListener((parent, view, position, id) -> {
            Movie movie = (Movie) movieList.getItemAtPosition(position);
            openAboutActivity(movie.getId());
        });

        refreshMain.setOnRefreshListener(() -> {
            mainController.refreshMovies(this);
        });
    }

    public void setProgressBarDisabled(boolean disabled){
        progressBar.setVisibility(disabled ? ProgressBar.GONE : ProgressBar.VISIBLE);
        movieList.setVisibility(disabled ? ListView.VISIBLE : ListView.GONE);
        noConnection.setVisibility(TextView.GONE);
        refreshMain.setRefreshing(false);
    }

    public void setNoConnection(boolean withoutConnection){
        setProgressBarDisabled(!withoutConnection);
        progressBar.setVisibility(withoutConnection ? ProgressBar.GONE : ProgressBar.VISIBLE);
        noConnection.setVisibility(withoutConnection ? TextView.VISIBLE : TextView.GONE);
        refreshMain.setRefreshing(false);
    }

    public void openAboutActivity(int id){
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("MOVIE_ID", id);
        startActivity(intent);
    }
}