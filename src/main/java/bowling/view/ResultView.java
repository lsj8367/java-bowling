package bowling.view;

import bowling.domain.frame.LastFrame;
import bowling.domain.state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ResultView {
    private static final String NAME = "| NAME |";
    private static final String FRAME = "  %02d  |";
    private static final int FIRST_FRAME_NUMBER = 1;
    private static final int LAST_FRAME_NUMBER = 10;

    // 프레임 헤드
    private static void printHeader() {
        print(NAME);
        IntStream.rangeClosed(FIRST_FRAME_NUMBER, LAST_FRAME_NUMBER)
            .forEach(frameNumber -> {
                print(String.format(FRAME, frameNumber));
            });
        System.out.println();
    }

    private static void print(String message) {
        System.out.print(message);
    }

    private static void printBoards(Board board) {
        board.getBoards().forEach(System.out::print);
    }

    public static void currentResult(Board board) {
        printHeader();
        printBoards(board);
        System.out.println();
    }

    public static void pitchedBall(int frameNum) {
        System.out.println(frameNum + " 프레임 투구");
    }

    public static String lastFrame(LastFrame lastFrame, Board board) {
        List<State> stateList = lastFrame.getState();
        List<String> resultList = new ArrayList<>();
        for (State state : stateList) {
            strike(board, resultList);
            spareOrMiss(board, resultList, state);
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < resultList.size(); i++) {
            String s = resultList.get(i);
            if (i == 1 && resultList.get(i).length() >= 3) {
                s = resultList.get(i).substring(1, 3);
            }
            result.append(s);
        }

        return result.toString();
    }

    private static void spareOrMiss(final Board board, final List<String> resultList, final State state) {
        if(state.firstPins() != 10 && state.secondPins() >= 0) {
            resultList.set(resultList.size() - 1, board.lastSpareOrMiss(state));
        }
    }

    private static void strike(final Board board, final List<String> resultList) {
        resultList.add(board.lastStrike());
    }
}
