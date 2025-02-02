package bowling;

import bowling.domain.Board;
import bowling.domain.Player;
import bowling.domain.frame.LastFrame;
import bowling.domain.state.State;
import bowling.domain.state.StateFactory;
import bowling.view.InputView;
import bowling.view.ResultView;

import java.util.ArrayList;
import java.util.List;

public class Bowling {
    private static final int MAX_NORMAL_FRAME = 9;
    private static final int LAST_FRAME = 10;

    public static void main(String[] args) {
        Player player = Player.of(InputView.inputName());
        Board board = new Board(player.addName());
        ResultView.currentResult(board);

        normalFrame(board);

        List<State> resultList = new ArrayList<>();
        lastFrame(board, resultList);
    }

    private static void normalFrame(Board board) {
        int count = 0;
        int startFrame = 1;
        while (count < MAX_NORMAL_FRAME) {
            ResultView.pitchedBall(startFrame);
            State state = StateFactory.first(InputView.inputPins());
            board.addStrike(startFrame);

            if (!state.isFinished()) {
                ResultView.pitchedBall(startFrame);
                state = state.nextPitch(InputView.inputPins());
                board.addFrame(startFrame, state);
            }

            ResultView.currentResult(board);
            startFrame++;
            count++;
        }
    }

    private static void lastFrame(Board board, List<State> resultList) {
        ResultView.pitchedBall(LAST_FRAME);

        State state = StateFactory.first(InputView.inputPins());

        if (state.isFinished()) {
            firstStrike(resultList, state);
        }

        if (!state.isFinished()) {
            ResultView.pitchedBall(LAST_FRAME);
            secondSpare(resultList, state);
        }

        LastFrame lastFrame = new LastFrame(resultList);
        board.addLast(LAST_FRAME, ResultView.lastFrame(lastFrame, board));
        ResultView.currentResult(board);
    }

    private static void secondSpare(List<State> list, State state) {
        state = state.nextPitch(InputView.inputPins());
        list.add(state);
        ResultView.pitchedBall(LAST_FRAME);
        state.lastSpare(list, state);
    }

    private static State firstStrike(List<State> list, State state) {
        list.add(state);
        ResultView.pitchedBall(LAST_FRAME);
        state = StateFactory.first(InputView.inputPins());

        if (!state.isFinished()) {
            ResultView.pitchedBall(LAST_FRAME);
            state = state.nextPitch(InputView.inputPins());
            list.add(state);
            return state;
        }

        list.add(state);
        ResultView.pitchedBall(LAST_FRAME);
        state = StateFactory.last(InputView.inputPins());
        list.add(state); //마지막 3번째 결과
        return state;
    }
}
