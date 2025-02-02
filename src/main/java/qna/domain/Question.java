package qna.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import qna.CannotDeleteException;

@Entity
public class Question extends AbstractEntity {
    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Answer> answers = new ArrayList<>();

    private boolean deleted = false;

    public Question() {
    }

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Question(long id, String title, String contents) {
        super(id);
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Question setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public User getWriter() {
        return writer;
    }

    public Question writeBy(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    private Question authValidation(User loginUser) throws CannotDeleteException{
        if(!writer.equals(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        return this;
    }

    private void ownerValidation(User loginUser) throws CannotDeleteException{
        for (Answer answer : answers) {
            answer.otherPersonComment(loginUser);
        }
    }

    public List<DeleteHistory> delete(User loginUser) {
        this.authValidation(loginUser);
        this.ownerValidation(loginUser);

        List<DeleteHistory> deleteHistories = deleteQuestion();
        deleteAnswers(deleteHistories);

        return deleteHistories;
    }

    private List<DeleteHistory> deleteQuestion() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        this.deleted = true;
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, this.getWriter()));
        return deleteHistories;
    }

    public boolean isDeleted() {
        return deleted;
    }

    private void deleteAnswers(List<DeleteHistory> deleteHistories) {
        for (Answer answer : answers) {
            answer.delete(deleteHistories);
        }
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
