package com.xaymjsongs.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xaymjsongs.R;
import com.xaymjsongs.listeners.RecyclerClick;
import com.xaymjsongs.models.DataPojo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class SongsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<DataPojo> dataPojos;
    DataPojo pojo;
    Context c;
    public static ArrayList<Bitmap> arrayList = new ArrayList<>();
    Bitmap bitmap = null;
    private AdapterView.OnItemClickListener onItemClickListener;
    RecyclerClick recyclerClick;

    public SongsAdapter(ArrayList<DataPojo> blogList, Context c) {
        arrayList.clear();;
        this.dataPojos = blogList;
        this.c=c;

    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        setZoomInAnimation(holder.itemView);
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false));


    }

    public void setDatain(ArrayList<DataPojo> songList) {
        arrayList.clear();;
        this.dataPojos = songList;
        notifyDataSetChanged();
    }

    public void setDatainImages(ArrayList<Bitmap> bitmaps) {
        arrayList = bitmaps;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (dataPojos != null && dataPojos.size() > 0) {
            return dataPojos.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends BaseViewHolder {

        ImageView ivThumbnail;
        TextView tvTitle;
        TextView albumname;

        RelativeLayout mainLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            mainLayout = itemView.findViewById(R.id.mainlay);
            albumname = itemView.findViewById(R.id.albumname);

        }

        protected void clear() {
            ivThumbnail.setImageDrawable(null);
        }

        public void onBind(int position) {
            super.onBind(position);

            pojo = dataPojos.get(position);
            if (pojo.getArtistViewUrl() != null) {
                 new loadImages().execute(pojo.getArtworkUrl100());
                //  Log.e("BITMAPS", bitmap + "");

             //   ivThumbnail.setImageBitmap(pojo.getImgs());


            }

            if (pojo.getArtistName() != null) {
                tvTitle.setText(dataPojos.get(position).getTrackname());

            }
            if (pojo.getCollectionName() != null) {
                albumname.setText(pojo.getCollectionName());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerClick.recyclerviewClick(getAdapterPosition());


                }
            });

        }


        class loadImages extends AsyncTask<String, Void, Bitmap> {

            Bitmap bmp = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
              ivThumbnail.setImageBitmap(bitmap);
            }

            @Override
            protected Bitmap doInBackground(String... voids) {
                URL url = null;
                try {


                    url = new URL(voids[0]);
                    //  Log.e("URL", url + "");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bmp = BitmapFactory.decodeStream(bis);
                    bitmap = bmp;
                    // Log.e("BMP", bitmap + "n");
                    bis.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                arrayList.add(bitmap);

                return bitmap;
            }
        }
    }

    private void setZoomInAnimation(View view) {
        Animation zoomIn = AnimationUtils.loadAnimation(c, R.anim.enter);// animation file
        view.startAnimation(zoomIn);
    }

    public void setRecyclerClick(RecyclerClick recyclerClick) {
        this.recyclerClick = recyclerClick;
    }

}