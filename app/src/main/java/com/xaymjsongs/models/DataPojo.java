package com.xaymjsongs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DataPojo implements Parcelable {
    String wrapperType, artistId, collectionId, artistName, collectionName, collectionCensoredName, artistViewUrl, collectionViewUrl, artworkUrl100, collectionPrice, collectionExplicitness, copyright, country, currency, releaseDate, primaryGenreName, description, previewUrl, trackname, trackprice;
    String trackExplicit,trackId;


    public String getTrackExplicit() {
        return trackExplicit;
    }


    public void setTrackExplicit(String trackExplicit) {
        this.trackExplicit = trackExplicit;
    }

    protected DataPojo(Parcel in) {
        wrapperType = in.readString();
        artistId = in.readString();
        collectionId = in.readString();
        artistName = in.readString();
        collectionName = in.readString();
        collectionCensoredName = in.readString();
        artistViewUrl = in.readString();
        collectionViewUrl = in.readString();
        artworkUrl100 = in.readString();
        collectionPrice = in.readString();
        collectionExplicitness = in.readString();
        copyright = in.readString();
        country = in.readString();
        currency = in.readString();
        releaseDate = in.readString();
        primaryGenreName = in.readString();
        description = in.readString();
        previewUrl = in.readString();
        trackprice = in.readString();
        trackname = in.readString();
        trackExplicit = in.readString();
        trackId = in.readString();

    }

    public String getTrackprice() {
        return trackprice;
    }

    public void setTrackprice(String trackprice) {
        this.trackprice = trackprice;
    }

    public static final Creator<DataPojo> CREATOR = new Creator<DataPojo>() {
        @Override
        public DataPojo createFromParcel(Parcel in) {
            return new DataPojo(in);
        }

        @Override
        public DataPojo[] newArray(int size) {
            return new DataPojo[size];
        }
    };

    public String getTrackname() {
        return trackname;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public void setTrackname(String trackname) {
        this.trackname = trackname;
    }

    public DataPojo() {
    }


    public String getWrapperType() {
        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    public void setCollectionCensoredName(String collectionCensoredName) {
        this.collectionCensoredName = collectionCensoredName;
    }


    public static Creator<DataPojo> getCREATOR() {
        return CREATOR;
    }


    public String getArtistViewUrl() {
        return artistViewUrl;
    }

    public void setArtistViewUrl(String artistViewUrl) {
        this.artistViewUrl = artistViewUrl;
    }

    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    public void setCollectionViewUrl(String collectionViewUrl) {
        this.collectionViewUrl = collectionViewUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(String collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    public void setCollectionExplicitness(String collectionExplicitness) {
        this.collectionExplicitness = collectionExplicitness;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (wrapperType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(wrapperType);
        }
        if (artistId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(artistId);
        }

        if (collectionId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(collectionId);
        }
        if (artistName == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(artistName);
        }
        if (collectionName == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(collectionName);
        }
        if (collectionCensoredName == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(collectionCensoredName);
        }
        if (artistViewUrl == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(artistViewUrl);
        }
        if (collectionViewUrl == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(collectionViewUrl);
        }
        if (artworkUrl100 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(artworkUrl100);
        }
        if (collectionPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(collectionPrice);
        }
        if (collectionExplicitness == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(collectionExplicitness);
        }
        if (copyright == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(copyright);
        }
        if (country == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(country);
        }

        if (currency == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(currency);
        }
        if (releaseDate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(releaseDate);
        }

        if (primaryGenreName == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(primaryGenreName);
        }
        if (description == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(description);
        }

        if (previewUrl == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(previewUrl);
        }
        if (trackprice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(trackprice);
        }
        if (trackname == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(trackname);
        }


        if (trackExplicit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(trackExplicit);
        }
        if (trackId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(trackId);
        }

    }
}
