package com.xaymjsongs;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xaymjsongs.adapters.SongsAdapter;
import com.xaymjsongs.models.DataPojo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SongDetails extends Fragment {

    String Tid;
    FloatingActionButton fabplay;
    public MediaPlayer player = new MediaPlayer();

    private ArrayList<DataPojo> myList = new ArrayList<>();
    ArrayList<DataPojo> showDetails = new ArrayList<>();
    ImageView imageview;
    Bitmap decodedBitmap;
    TextView description, album, price, nameofsinger, genere, date, country, collprice;
    private boolean playPause;
    private boolean intialStage = true;

    DataPojo dataPojo = new DataPojo();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layoutdetails, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                myList = bundle.getParcelableArrayList("SongDetails");
            }
            Tid = bundle.getString("TrackNameId");
            String img = bundle.getString("imageBitmap");
            Log.e("imgbitm", img);
            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
            decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Log.e("mylist", myList.size() + ".")
            ;
        }
        for (DataPojo pojo : myList) {
            if (pojo.getTrackId().equals(Tid)) {
                dataPojo = pojo;
            }
        }

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.titletool);
        if (dataPojo.getTrackExplicit().equals("notExplicit")) {

            collapsingToolbarLayout.setTitle(dataPojo.getTrackname());

        } else {
            collapsingToolbarLayout.setTitle(dataPojo.getTrackname() + " (Explicit)");


        }
        imageview = view.findViewById(R.id.backgroundimage);
        Log.e("DECODED", decodedBitmap + "");
        imageview.setImageBitmap(decodedBitmap);
        description = view.findViewById(R.id.description);
        nameofsinger = view.findViewById(R.id.singername);
        country = view.findViewById(R.id.county);
        price = view.findViewById(R.id.currency);
        date = view.findViewById(R.id.date);
        album = view.findViewById(R.id.album);
        genere = view.findViewById(R.id.genere);
        collprice = view.findViewById(R.id.collectionp);
        fabplay = view.findViewById(R.id.fabplay);
        fabplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("play", playPause + "");
                    if (!playPause) {
                        if (player.isPlaying()) {
                            player.stop();
                        }
                        fabplay.setImageResource(R.drawable.ic_baseline_pause_24);

                        Toast.makeText(getActivity(), "Playing " + dataPojo.getTrackname(), Toast.LENGTH_SHORT).show();
                        if (intialStage)
                            new Player()
                                    .execute(dataPojo.getPreviewUrl());
                        else {
                            if (!player.isPlaying())
                                player.start();
                        }
                        playPause = true;
                    } else {
                        Toast.makeText(getActivity(), "Song Paused", Toast.LENGTH_SHORT).show();
                        if (player.isPlaying())
                            fabplay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        player.pause();
                        playPause = false;
                    }


                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
        collprice.setText("Collection Price : " + dataPojo.getCurrency() + " " + dataPojo.getCollectionPrice());

        genere.setText("Genre (" + dataPojo.getPrimaryGenreName() + ")");


        nameofsinger.setText("Singer : " + dataPojo.getArtistName());
        description.setText(dataPojo.getDescription());
        country.setText("Country : " + dataPojo.getCountry());
        price.setText("Track Price : " + dataPojo.getCurrency() + " " + dataPojo.getTrackprice());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dataPojo.getReleaseDate());
            SimpleDateFormat sdfnewformat = new SimpleDateFormat("MMM dd yyyy");
            String finalDateString = sdfnewformat.format(convertedDate);
            date.setText("Release Date : " + finalDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("datapojo", dataPojo.getTrackname() + "null");
        try {

            if (dataPojo.getCollectionExplicitness().equals("notExplicit")) {

                album.setText("Album : " + dataPojo.getCollectionName());

            } else {

                album.setText("Album : " + dataPojo.getCollectionName() + " (Explicit)");

            }

        } catch (Exception e) {
        }

    }

    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared = null;
            try {

                player.setDataSource(params[0]);

                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.e("COMPLETERD", "YES");
                        fabplay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause = false;
                        player.stop();
                        player.reset();

                    }
                });
                player.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                fabplay.setImageResource(R.drawable.ic_baseline_pause_24);
                progress.cancel();
            }
            Log.d("Prepared", "" + result);
            player.start();


            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.setCancelable(false);
            this.progress.show();

            if (player.isPlaying()) {
                player.stop();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playPause) {
            player.stop();
            ;
            player.pause();
            player.release();
            player = null;
            new Player().cancel(true);

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        FirstFragment newCustomFragment = new FirstFragment();
        transaction.replace(R.id.nav_host_fragment, newCustomFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}