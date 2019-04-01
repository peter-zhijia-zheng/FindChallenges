package com.duolingo.challenges.usecases;

import com.duolingo.challenges.data.models.WordCoordinate;

import java.util.ArrayList;
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
        List<WordCoordinate> coordinateList = new ArrayList<>();
        coordinateList.add(coordinateHead);
        if (coordinateHead.x != coordinateTail.x) {
            return coordinateList;
        }

        int offset = coordinateHead.y > coordinateTail.y ? -1 : 1;
        int indexY = coordinateHead.y;
        do {
            indexY += offset;
            coordinateList.add(new WordCoordinate(coordinateHead.x, indexY));
        } while (indexY != coordinateTail.y);
        return coordinateList;
    }

    public List<WordCoordinate> calculateCoordinatesOnSameColumn(
            WordCoordinate coordinateHead,
            WordCoordinate coordinateTail
    ) {
        List<WordCoordinate> coordinateList = new ArrayList<>();
        coordinateList.add(coordinateHead);
        if (coordinateHead.y != coordinateTail.y) {
            return coordinateList;
        }

        int offset = coordinateHead.x > coordinateTail.x ? -1 : 1;
        int indexX = coordinateHead.x;
        do {
            indexX += offset;
            coordinateList.add(new WordCoordinate(indexX, coordinateHead.y));
        } while (indexX != coordinateTail.x);
        return coordinateList;
    }

    public List<WordCoordinate> calculateCoordinatesDiagonally(
            WordCoordinate coordinateHead,
            WordCoordinate coordinateTail
    ) {
        int offsetX = coordinateHead.x > coordinateTail.x ? -1 : 1;
        int offsetY = coordinateHead.y > coordinateTail.y ? -1 : 1;
        List<WordCoordinate> coordinateList = new ArrayList<>();
        coordinateList.add(coordinateHead);
        int indexX = coordinateHead.x;
        int indexY = coordinateHead.y;
        do {
            indexX += offsetX;
            indexY += offsetY;
            coordinateList.add(new WordCoordinate(indexX, indexY));
        } while (indexX != coordinateTail.x && indexY != coordinateTail.y);
        return coordinateList;
    }

}