package com.duolingo.challenges.usecases;

import com.duolingo.challenges.data.models.WordCoordinate;

import javax.inject.Inject;

public class CoordinatesComparatorUseCase {

    @Inject
    public CoordinatesComparatorUseCase() {

    }

    public boolean isCoordinateOnSameRow(
            WordCoordinate pivotCoordinate,
            WordCoordinate coordinate
    ) {
        return coordinate.x == pivotCoordinate.x;
    }

    public boolean isCoordinateOnSameColumn(
            WordCoordinate pivotCoordinate,
            WordCoordinate coordinate
    ) {
        return coordinate.y == pivotCoordinate.y;
    }
}