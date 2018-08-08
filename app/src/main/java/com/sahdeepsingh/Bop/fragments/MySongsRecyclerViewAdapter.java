package com.sahdeepsingh.Bop.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahdeepsingh.Bop.R;
import com.sahdeepsingh.Bop.SongData.Song;
import com.sahdeepsingh.Bop.fragments.FragmentSongs.OnListFragmentInteractionListener;
import com.sahdeepsingh.Bop.playerMain.Main;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} that can display  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySongsRecyclerViewAdapter extends RecyclerView.Adapter<MySongsRecyclerViewAdapter.ViewHolder> {

    private final List<Song> songs;

    private OnListFragmentInteractionListener mListener;

    public MySongsRecyclerViewAdapter(List<Song> items, OnListFragmentInteractionListener listener) {
        songs = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_songs, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.songName.setText(songs.get(position).getTitle());
        holder.songBy.setText(songs.get(position).getArtist());
        holder.songName.setSelected(true);
        String path = Main.songs.getAlbumArt(songs.get(position));
        if (path != null)
            Picasso.get().load(new File(path)).fit().centerCrop().error(R.drawable.ic_pause_dark).into(holder.circleImageView);
        else  Picasso.get().load(R.drawable.ic_cancel_dark).fit().centerCrop().into(holder.circleImageView);
        //holder.circleImageView.setImageBitmap(Main.songs.getAlbumArt(songs.get(position)));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.mView.getContext();
                if (context instanceof OnListFragmentInteractionListener) {
                    mListener = (OnListFragmentInteractionListener) context;
                } else {
                    throw new RuntimeException(context.toString()
                            + " must implement OnListFragmentInteractionListener");
                }
                if (mListener != null) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.getAdapterPosition(), "singleSong");


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if ((Main.songs.songs != null) && (!Main.songs.songs.isEmpty()))
            return Main.songs.songs.size();

        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView songName;
        public final TextView songBy;
        public final CircleImageView circleImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            songName = view.findViewById(R.id.songName);
            songBy = view.findViewById(R.id.songBy);
            circleImageView = view.findViewById(R.id.albumArt);
        }

    }
}
