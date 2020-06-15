package qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteHistoriesGroup {

    private final List<DeleteHistory> deleteHistories = new ArrayList<>();

    public List<DeleteHistory> getDeleteHistories() {
        return Collections.unmodifiableList(deleteHistories);
    }

    public void addQuestionHistory(Question question, long questionId) {
        DeleteHistory deleteHistory =
                new DeleteHistory(ContentType.QUESTION, questionId, question.getWriter(), LocalDateTime.now());
        deleteHistories.add(deleteHistory);
    }
}
