package br.com.luanemanuel.desafiotokenlab.controller.data;

import java.util.List;

import br.com.luanemanuel.desafiotokenlab.model.db.MovieDataAccess;
import br.com.luanemanuel.desafiotokenlab.model.Movie;

public class MovieData {

    private static MovieDataAccess movieDataAccess;
    private static List<Movie> movieList;

    public static void setMovieDataAccess(MovieDataAccess movieDataAccess) {
        MovieData.movieDataAccess = movieDataAccess;
    }

    public static MovieDataAccess getMovieDataAccess() {
        return movieDataAccess;
    }

    public static void setMovieList(List<Movie> movieList) {
        MovieData.movieList = movieList;
    }

    public static List<Movie> getMovieList() {
        return movieList;
    }
}
