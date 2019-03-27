package com.duolingo.challenges.usecases;

import com.duolingo.challenges.data.models.WordCoordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class CoordinatesArrayUseCase {
    @Inject
    public CoordinatesArrayUseCase() {

    }

    public List<WordCoordinate> calculateCoordinatesOnSameRow(
            WordCoordinate coordinateHead,
            WordCoordinate coordinateTail
    ) {
        List<WordCoordinate> coordinateList = new ArrayList<>(Arrays.asList(coordinateHead));
        if (coordinateHead.x != coordinateTail.x) {
            return coordinateList;
        }

        int indexY = coordinateHead.y + 1;
        while (indexY <= coordinateTail.y) {
            coordinateList.add(new WordCoordinate(coordinateHead.x, indexY));
            indexY++;
        }
        return coordinateList;
    }

    public List<WordCoordinate> calculateCoordinatesOnSameColumn(
            WordCoordinate coordinateHead,
            WordCoordinate coordinateTail
    ) {
        List<WordCoordinate> coordinateList = new ArrayList<>(Arrays.asList(coordinateHead));
        if (coordinateHead.y != coordinateTail.y) {
            return coordinateList;
        }

        int indexX = coordinateHead.x + 1;
        while (indexX <= coordinateTail.x) {
            coordinateList.add(new WordCoordinate(indexX, coordinateHead.y));
            indexX++;
        }
        return coordinateList;
    }

    public List<WordCoordinate> calculateCoordinatesDiagonally(
            WordCoordinate coordinateHead,
            WordCoordinate coordinateTail
    ) {
        List<WordCoordinate> coordinateList = new ArrayList<>(Arrays.asList(coordinateHead));
        int indexX = coordinateHead.x + 1;
        int indexY = coordinateHead.y + 1;
        while (indexX <= coordinateTail.x && indexY <= coordinateTail.y) {
            coordinateList.add(new WordCoordinate(indexX, indexY));
            indexX++;
            indexY++;
        }
        return coordinateList;
    }

}