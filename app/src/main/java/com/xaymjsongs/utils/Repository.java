package com.xaymjsongs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.xaymjsongs.apis.Api;
import com.xaymjsongs.models.DataPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Repository {

    String line = "";
    DataPojo dataPojo = new DataPojo();
    ArrayList<DataPojo> list = new ArrayList<DataPojo>();
    private MutableLiveData<ArrayList<DataPojo>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> Updating = new MutableLiveData<>();
    private static Repository instance;
    private ArrayList<String> stt = new ArrayList<>();

    private MutableLiveData<ArrayList<Bitmap>> bitmaps = new MutableLiveData<>();
    ArrayList<Bitmap> bitmap;
    String myDdata = "";


    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public MutableLiveData<Boolean> isUpdating() {

        return Updating;
    }

    public MutableLiveData<ArrayList<Bitmap>> getBitmaps() {
        for (int i = 0; i < stt.size(); i++) {
            new loadImages().execute();

        }

        return bitmaps;
    }

    public MutableLiveData<ArrayList<DataPojo>> getMutableLiveData() {
        Updating.postValue(true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("came", "yes");

                    URL url = new URL(Api.UrlToLoadSongs);
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        myDdata = sb.toString();
                        Log.e("mydata", sb.toString());
                        JSONObject jsonObject = new JSONObject(myDdata);
                        Log.e("JSONOBJs", jsonObject + "n");
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        Log.e("JSONARRAY", jsonArray + "n");
                        JSONObject jsonObject1;


                        for (int i = 0; i < jsonArray.length(); i++) {
                            DataPojo dataPojo = new DataPojo();
                            jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.getString("artistName").equals("Michael Jackson") && jsonObject1.getString("wrapperType").equals("track")) {
                                dataPojo.setWrapperType(jsonObject1.optString("wrapperType"));
                                dataPojo.setArtistId(String.valueOf(jsonObject1.optInt("artistId")));
                                dataPojo.setArtistName(jsonObject1.optString("artistName"));
                                dataPojo.setArtistViewUrl(jsonObject1.optString("artistViewUrl"));
                                dataPojo.setArtworkUrl100(jsonObject1.optString("artworkUrl100"));
                                dataPojo.setCollectionCensoredName(jsonObject1.optString("collectionCensoredName"));
                                dataPojo.setCollectionExplicitness(jsonObject1.optString("collectionExplicitness"));
                                dataPojo.setCollectionId(String.valueOf(jsonObject1.optInt("collectionId")));
                                dataPojo.setCollectionName(jsonObject1.optString("collectionName"));
                                dataPojo.setCollectionPrice(String.valueOf(jsonObject1.optDouble("collectionPrice")));
                                dataPojo.setCollectionViewUrl(jsonObject1.optString("collectionViewUrl"));
                                dataPojo.setCopyright(jsonObject1.optString("copyright"));
                                dataPojo.setCountry(jsonObject1.optString("country"));
                                dataPojo.setPrimaryGenreName(jsonObject1.optString("primaryGenreName"));
                                dataPojo.setPreviewUrl(jsonObject1.optString("previewUrl"));
                                dataPojo.setReleaseDate(jsonObject1.optString("releaseDate"));
                                dataPojo.setCurrency(jsonObject1.optString("currency"));
                                dataPojo.setDescription(jsonObject1.optString("description"));
                                dataPojo.setTrackname(jsonObject1.optString("trackName"));
                                dataPojo.setTrackprice(String.valueOf(jsonObject1.optDouble("trackPrice")));
                                dataPojo.setTrackExplicit(jsonObject1.optString("trackExplicitness"));
                                dataPojo.setTrackId(String.valueOf(jsonObject1.optDouble("trackId")));
                                String imgurl = dataPojo.getArtworkUrl100();
                                dataPojo.setArtworkUrl100(imgurl);
                                list.add(dataPojo);
                                mutableLiveData.postValue(list);
                                Updating.postValue(false);
                                bitmap = null;
                            }
                        }

                        if (inputStream != null) {
                            inputStream.close();
                        }

                    } else {
                        Log.e("Connection Error: ", "Error response code: " + urlConnection.getResponseCode() + "");
                    }

                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }

                } catch (MalformedURLException e) {
                    Log.e("MAL", e.getLocalizedMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("IO", e.getLocalizedMessage());

                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.e("JSON", e.getLocalizedMessage());
                    getMutableLiveData();
                    e.printStackTrace();
                }
            }

        });

        thread.start();
        return mutableLiveData;
    }


    class loadImages extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        Bitmap bmp = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmap);
            Log.e("yes", bitmaps.size()+",")
            ; }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... voids) {
            URL url = null;
            ArrayList<Bitmap> arrayList = new ArrayList<>();
            try {


                url = new URL(voids[0]);
   Log.e("URL", url + "null");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bmp = BitmapFactory.decodeStream(bis);
                bitmap.add(bmp);
                // Log.e("BMP", bitmap + "n");
                arrayList.add(bmp);

                bitmaps.postValue(arrayList);
                bis.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return arrayList;
        }
    }
}

