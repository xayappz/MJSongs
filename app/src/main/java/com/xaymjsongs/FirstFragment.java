package com.xaymjsongs;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.xaymjsongs.adapters.SongsAdapter;
import com.xaymjsongs.listeners.RecyclerClick;
import com.xaymjsongs.models.DataPojo;
import com.xaymjsongs.vmodels.SongsVM;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class FirstFragment extends Fragment implements RecyclerClick {
    RecyclerView songsRecyclerView;
    SongsAdapter songsAdapter;
    SongsVM model;
    ArrayList<DataPojo> myList = new ArrayList<>();
    ProgressDialog progressDialog;
    FloatingActionButton actionButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(SongsVM.class);
        songsRecyclerView = view.findViewById(R.id.listRecyclerView);
        prepareRecyclerView(myList);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait");

        actionButton = view.findViewById(R.id.fabshuffle);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Playlist Shuffled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                SongsAdapter.arrayList.clear();

                Collections.shuffle(myList);
                //  songsAdapter.setDatain(myList);
                prepareRecyclerView(myList);
            }
        });

        model.getImages().observe(getViewLifecycleOwner(), new Observer<ArrayList<Bitmap>>() {
            @Override
            public void onChanged(ArrayList<Bitmap> bitmaps) {
                if (bitmaps != null) {
                    SongsAdapter.arrayList.clear();

                    bitmaps = SongsAdapter.arrayList;
                    Log.e("bitto", bitmaps + "");
                    songsAdapter.setDatainImages(bitmaps);

                }
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.create();
        model.getImages().observe(getViewLifecycleOwner(), new Observer<ArrayList<Bitmap>>() {
            @Override
            public void onChanged(ArrayList<Bitmap> bitmaps) {
                Log.e("bmps", bitmaps.size() + ".");
            }
        });
        model.isUpdating().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressDialog.show();


                } else {
                    progressDialog.dismiss();
                }
            }
        });
        model.getResultsItemObserver().observe(getViewLifecycleOwner(), new Observer<ArrayList<DataPojo>>() {
            @Override
            public void onChanged(ArrayList<DataPojo> dataPojos) {


                if (dataPojos != null) {
                    Log.e("datapojos", dataPojos.size() + ".");
                    myList = dataPojos;
                    songsAdapter.setDatain(dataPojos);
                    //   prepareRecyclerView(dataPojos);
                } else {
                    Toast.makeText(getContext(), "No result found", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Log.e("onCREATEd", "called");

    }


    private void prepareRecyclerView(ArrayList<DataPojo> dataPojos) {
        SongsAdapter.arrayList.clear();
        songsAdapter = new SongsAdapter(dataPojos, getActivity());
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        songsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        songsRecyclerView.setAdapter(songsAdapter);
        songsAdapter.setRecyclerClick(this);
        songsAdapter.notifyDataSetChanged();

    }


    @Override
    public void recyclerviewClick(int position) {
        loadFragment(position);
    }

    private void loadFragment(int pos) {
        Log.e("posit", pos + "");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bundle bundle = new Bundle();
        String encodedBitmap = "";
        try {

            SongsAdapter.arrayList.get(pos).compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encodedBitmap = Base64.encodeToString(byteArray, Base64.DEFAULT);

        } catch (Exception e) {
            Log.e("imgwexc", e.getLocalizedMessage());
        } finally {
            bundle.putString("imageBitmap", encodedBitmap);

        }

        bundle.putParcelableArrayList("SongDetails", (ArrayList<? extends Parcelable>) myList);
        bundle.putString("TrackNameId", myList.get(pos).getTrackId());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        SongDetails newCustomFragment = new SongDetails();
        newCustomFragment.setArguments(bundle);
        transaction.replace(R.id.nav_host_fragment, newCustomFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

}


