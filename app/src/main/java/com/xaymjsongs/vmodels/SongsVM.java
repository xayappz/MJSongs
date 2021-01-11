package com.xaymjsongs.vmodels;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xaymjsongs.utils.Repository;
import com.xaymjsongs.models.DataPojo;

import java.util.ArrayList;

public class SongsVM extends ViewModel {
    private Repository repository;
    private MutableLiveData<ArrayList<DataPojo>> resultsItem;
    private MutableLiveData<ArrayList<Bitmap>> bitmaps = new MutableLiveData<>();
    Bitmap bitmap;
    String myDdata = "";
    ArrayList<DataPojo> list = new ArrayList<DataPojo>();
    private MutableLiveData<ArrayList<DataPojo>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> Updating = new MutableLiveData<>();

    public SongsVM() {
        repository = Repository.getInstance();

        mutableLiveData = new MutableLiveData<>();
        bitmaps=new MutableLiveData<>();
        mutableLiveData = repository.getMutableLiveData();
       // bitmaps=repository.getBitmaps();


        Log.e("init", "called");
    }



    public MutableLiveData<Boolean> isUpdating() {
        return repository.isUpdating();

    }

    public MutableLiveData<ArrayList<DataPojo>> getResultsItemObserver() {

        return mutableLiveData;
    }
    public MutableLiveData<ArrayList<Bitmap>> getImages() {

        return bitmaps;
    }

}
