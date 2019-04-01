package com.duolingo.challenges.usecases;

import com.duolingo.challenges.data.models.WordCoordinate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PositionCoordinateUseCase  {
    @Inject
    public PositionCoordinateUseCase() {

    }

    public WordCoordinate coordinateFromPosition(int gridSize, int position) {
        int x = position / gridSize;
        int y = position % gridSize;
        return new WordCoordinate(x, y);
    }

    public List<Integer> positionsFromCoordinates(int gridSize, List<WordCoordinate> coordinates) {
        int size = coordinates.size();
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            positions.add(positionFromCoordinate(gridSize, coordinates.get(i)));
        }
        return positions;
    }

    public int positionFromCoordinate(int gridSize, WordCoordinate coordinate) {
        return (coordinate.x * gridSize) + coordinate.y;
    }
}