package bowling.domain.state;

import bowling.domain.Pins;
import bowling.exception.NextPitchingException;

import java.util.List;

public class Miss implements State {
    private static final String MESSAGE = "두번째 투구";

    private final Pins firstPins;
    private final Pins secondPins;

    public Miss(Pins firstPins, Pins secondPins) {
        this.firstPins = firstPins;
        this.secondPins = secondPins;
    }

    @Override
    public State nextPitch(int pins) {
        throw new NextPitchingException(MESSAGE);
    }

    @Override
    public State lastPitch(int pins) {
        throw new NextPitchingException(MESSAGE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public List<State> lastSpare(List<State> list, State state) {
        return list;
    }

    @Override
    public int firstPins() {
        return firstPins.getFalledPins();
    }

    @Override
    public int secondPins() {
        return secondPins.getFalledPins();
    }
}
