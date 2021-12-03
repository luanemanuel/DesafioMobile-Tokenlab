package br.com.luanemanuel.desafiotokenlab.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.luanemanuel.desafiotokenlab.R;
import br.com.luanemanuel.desafiotokenlab.controller.AboutController;
import br.com.luanemanuel.desafiotokenlab.model.Movie;

public class AboutActivity extends AppCompatActivity {

    private ProgressBar aboutProgressBar;
    private TextView aboutNoConnection;
    public SwipeRefreshLayout refreshAbout;

    private ImageView movieImage;
    private TextView movieTitle;
    private TextView movieVotes;
    private TextView movieAdult;
    private TextView movieDate;
    private TextView movieOverview;

    private final AboutController aboutController = new AboutController();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutProgressBar = findViewById(R.id.aboutProgressBar);
        aboutNoConnection = findViewById(R.id.aboutNoConnection);
        refreshAbout = findViewById(R.id.refreshAbout);

        movieImage = findViewById(R.id.aboutMovieImage);
        movieTitle = findViewById(R.id.aboutMovieTitle);
        movieVotes = findViewById(R.id.aboutMovieVotes);
        movieAdult = findViewById(R.id.aboutMovieAdult);
        movieDate = findViewById(R.id.aboutMovieDate);
        movieOverview = findViewById(R.id.aboutMovieOverview);

        setProgressBarDisabled(false);

        int movieID = getIntent().getIntExtra("MOVIE_ID", -1);

        if(movieID != -1){
            aboutController.getMovieAbout(movieID, this);
        }

        refreshAbout.setOnRefreshListener(() -> {
            aboutController.getMovieAbout(movieID, this);
        });
    }

    // Atualiza os dados na tela
    public void updateScreen(Movie movie){
        movieTitle.setText(movie.getTitle());
        movieVotes.setText(getString(R.string.nota, movie.getVotes()));
        movieAdult.setText(getString(R.string.adulto, getString(movie.isAdult() ? R.string.sim : R.string.nao)));
        movieDate.setText(getString(R.string.data, movie.getReleaseDate()));
        movieOverview.setText(movie.getOverview());

        Picasso.get().load(movie.getPosterURL()).into(movieImage, new Callback() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(R.drawable.image_notfound).into(movieImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        movieImage.setImageResource(R.drawable.image_notfound);
                    }
                });
            }
        });
        setProgressBarDisabled(true);
        refreshAbout.setRefreshing(false);
    }

    // Desativa ou ativa certos componentes durante o carregamento
    public void setProgressBarDisabled(boolean disabled){
        aboutProgressBar.setVisibility(disabled ? ProgressBar.GONE : ProgressBar.VISIBLE);
        movieImage.setVisibility(disabled ? ImageView.VISIBLE : ImageView.GONE);
        movieTitle.setVisibility(disabled ? TextView.VISIBLE : TextView.GONE);
        movieVotes.setVisibility(disabled ? TextView.VISIBLE : TextView.GONE);
        movieAdult.setVisibility(disabled ? TextView.VISIBLE : TextView.GONE);
        movieDate.setVisibility(disabled ? TextView.VISIBLE : TextView.GONE);
        movieOverview.setVisibility(disabled ? TextView.VISIBLE : TextView.GONE);
        aboutNoConnection.setVisibility(TextView.GONE);
        refreshAbout.setRefreshing(false);
    }

    // Torna visivel a mensagem de erro de conexão
    public void setNoConnection(boolean withoutConnection){
        setProgressBarDisabled(!withoutConnection);
        aboutProgressBar.setVisibility(withoutConnection ? ProgressBar.GONE : ProgressBar.VISIBLE);
        aboutNoConnection.setVisibility(withoutConnection ? TextView.VISIBLE : TextView.GONE);
        refreshAbout.setRefreshing(false);
    }
}
