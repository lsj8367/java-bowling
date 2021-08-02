package bowling.domain.score;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("점수를 계산하기 위한 Score 클래스 테스트")
class ScoreTest {

    // 점수는 상태값에 따라 계산하는 방식이 달라진다.
    // 상태에 따라선 계산이 불가능 할 수 있다.
    // 따라서 계산 불가 Score 객체가 있으면 좋을듯
    @DisplayName("점수객체는 점수를 가지고 초기화 한다")
    @Test
    void init() {
        assertThat(new SomeScore(10)).isInstanceOf(Score.class);
    }

    static class SomeScore extends Score {

        protected SomeScore(int score) {
            super(score);
        }
    }
}
