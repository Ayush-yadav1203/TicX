package com.ticx.ai;

import com.ticx.model.Board;
import com.ticx.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyAI {

    private final Random random = new Random();

    public int[] chooseMove(Board board, Player player) {

        List<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                if (board.getCell(row, col) == ' ') {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            return null;
        }

        return emptyCells.get(
                random.nextInt(emptyCells.size())
        );
    }
}
