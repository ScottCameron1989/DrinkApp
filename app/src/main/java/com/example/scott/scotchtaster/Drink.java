package com.example.scott.scotchtaster;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by scott on 2016-01-21.
 */
public class Drink implements Parcelable{

    private String nom;
    private String description;
    private double price;
    private String[] tags;
    private float rating;

    public Drink(String nom, double price, float rating, String description, String[] tags) {
        this.nom = nom;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.tags = tags;
    }

    public Drink(String nom, double price, float rating) {
        this.nom = nom;
        this.price = price;
        this.rating = rating;
    }

    protected Drink(Parcel in) {
        nom = in.readString();
        description = in.readString();
        price = in.readDouble();
        tags = in.createStringArray();
        rating = in.readFloat();
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return nom + " " + price + "$";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeStringArray(tags);
        dest.writeFloat(rating);
    }
}
