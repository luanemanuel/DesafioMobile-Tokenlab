package br.com.luanemanuel.desafiotokenlab.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.luanemanuel.desafiotokenlab.model.Movie;

public class MovieDataAccess {

    private final DatabaseConnection connection;
    private final SQLiteDatabase database;

    public MovieDataAccess(Context context) {
        connection = new DatabaseConnection(context);
        database = connection.getWritableDatabase();
    }

    public void insertMovieToDatabase(Movie movie){
        ContentValues values = new ContentValues();
        values.put("id", movie.getId());
        values.put("title", movie.getTitle());
        values.put("poster_url", movie.getPosterURL());
        values.put("overview", movie.getOverview());
        values.put("votes", movie.getVotes());
        values.put("release_date", movie.getReleaseDate());

        Cursor cursor = database.rawQuery("SELECT id FROM movie WHERE id = '" + movie.getId()+ "'", null);

        if(cursor.getCount() <= 0){
            database.insert("movie", null, values);
        }
        cursor.close();
    }

    public void updateMovieOnDatabase(Movie movie){
        ContentValues values = new ContentValues();
        values.put("title", movie.getTitle());
        values.put("poster_url", movie.getPosterURL());
        values.put("overview", movie.getOverview());
        values.put("votes", movie.getVotes());
        values.put("release_date", movie.getReleaseDate());

        database.update("movie", values, "id = ?", new String[]{String.valueOf(movie.getId())});
    }

    public List<Movie> selectAllMoviesFromDatabase(){
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = database.query("movie", new String[]{"id", "title", "poster_url", "overview", "votes", "release_date"},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setPosterURL(cursor.getString(2));
            movie.setOverview(cursor.getString(3));
            movie.setVotes(cursor.getString(4));
            movie.setReleaseDate(cursor.getString(5));
            movies.add(movie);
        }

        cursor.close();
        return movies;
    }

    public void clearMovieDatabase(){
        database.execSQL("DELETE FROM movie");
    }
}
