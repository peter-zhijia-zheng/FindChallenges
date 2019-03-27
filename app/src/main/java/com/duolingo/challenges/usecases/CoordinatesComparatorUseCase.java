package com.duolingo.challenges.usecases;

import com.duolingo.challenges.data.models.WordCoordinate;

public class CoordinatesComparatorUseCase {

    public boolean isCoordinateAbovePivot(
            WordCoordinate pivotCoordinate,
            WordCoordinate coordinate
    ) {
        return coordinate.y < pivotCoordinate.y;
    }

    public boolean isCoordinateLeftOfPivot(
            WordCoordinate pivotCoordinate,
            WordCoordinate coordinate
    ) {
        return coordinate.x < pivotCoordinate.x;
    }

    public boolean isCoordinateRightOfPivotOnSameRow(
            WordCoordinate pivotCoordinate,
            WordCoordinate coordinate
    ) {
        return coordinate.x == pivotCoordinate.x;
    }

    public boolean isCoordinateBelowPivotOnSameColumn(
            WordCoordinate pivotCoordinate,
            WordCoordinate coordinate
    ) {
        return coordinate.y == pivotCoordinate.y;
    }
}