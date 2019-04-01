package com.duolingo.challenges.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Translation implements Parcelable {
    public static final Parcelable.Creator<Translation> CREATOR = new Parcelable.Creator<Translation>() {
        @Override
        public Translation createFromParcel(Parcel source) {
            return new Translation(source);
        }

        @Override
        public Translation[] newArray(int size) {
            return new Translation[size];
        }
    };
    public final String sourceLanguage;
    public final String targetLanguage;
    public final String word;
    public final int gridSize;
    public List<String> characterList;
    public List<WordLocation> locations;

    public Translation(
            String sourceLanguage,
            String targetLanguage,
            String word,
            int gridSize,
            List<String> characterList,
            List<WordLocation> locations
    ) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.word = word;
        this.gridSize = gridSize;
        this.characterList = characterList;
        this.locations = locations;
    }

    private Translation(Parcel source) {
        this.sourceLanguage = source.readString();
        this.targetLanguage = source.readString();
        this.word = source.readString();
        this.gridSize = source.readInt();
        this.characterList = source.createStringArrayList();
        source.readList(this.locations, WordLocation.class.getClassLoader());
    }

    public List<List<WordCoordinate>> getAllSolutionsCoordinates() {
        ArrayList<List<WordCoordinate>> coordinates = new ArrayList<>();
        for (WordLocation location : locations) {
            coordinates.add(location.coordinates);
        }
        return coordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sourceLanguage);
        dest.writeString(targetLanguage);
        dest.writeString(word);
        dest.writeInt(gridSize);
        dest.writeStringList(characterList);
        dest.writeList(locations);
    }
}