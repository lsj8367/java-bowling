package bowling.domain.frame;

import bowling.domain.state.State;

import java.util.List;

public class NormalFrame implements Frame{
    private List<State> state;

    private NormalFrame(List<State> state) {
        this.state = state;
    }

    public static NormalFrame of(List<State> state) {
        return new NormalFrame(state);
    }


}
