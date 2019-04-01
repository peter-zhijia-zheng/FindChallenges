package com.duolingo.challenges.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Solution implements Parcelable {
    public static final Parcelable.Creator<Solution> CREATOR = new Parcelable.Creator<Solution>() {
        @Override
        public Solution createFromParcel(Parcel source) {
            return new Solution(source);
        }

        @Override
        public Solution[] newArray(int size) {
            return new Solution[size];
        }
    };
    public List<Integer> positions;

    public Solution(List<Integer> positions) {
        this.positions = positions;
    }

    private Solution(Parcel source) {
        source.readList(positions, Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(positions);
    }
}