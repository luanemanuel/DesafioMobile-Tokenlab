package br.com.luanemanuel.desafiotokenlab.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.luanemanuel.desafiotokenlab.R;
import br.com.luanemanuel.desafiotokenlab.model.Movie;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private final Context mContext;
    private final int mResource;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> movies) {
        super(context, resource, movies);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView movieImage = convertView.findViewById(R.id.movieImage);
        TextView movieTitle = convertView.findViewById(R.id.movieTitle);

        Picasso.get().load(getItem(position).getPosterURL()).into(movieImage, new Callback() {

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
        movieTitle.setText(getItem(position).getTitle());

        return convertView;
    }
}
