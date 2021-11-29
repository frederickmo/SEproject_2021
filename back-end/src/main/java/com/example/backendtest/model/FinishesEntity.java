package com.example.backendtest.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Finishes", schema = "backend_demo", catalog = "")
@IdClass(FinishesEntityPK.class)
public class FinishesEntity {
    private int studentId;
    private int taskId;
    private Timestamp finishTime;
    private Byte finished;
    private String answer;
    private Integer score;

    @Id
    @Column(name = "studentId")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Id
    @Column(name = "taskId")
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "finishTime")
    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @Basic
    @Column(name = "finished")
    public Byte getFinished() {
        return finished;
    }

    public void setFinished(Byte finished) {
        this.finished = finished;
    }

    @Basic
    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinishesEntity that = (FinishesEntity) o;

        if (studentId != that.studentId) return false;
        if (taskId != that.taskId) return false;
        if (finishTime != null ? !finishTime.equals(that.finishTime) : that.finishTime != null) return false;
        if (finished != null ? !finished.equals(that.finished) : that.finished != null) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + taskId;
        result = 31 * result + (finishTime != null ? finishTime.hashCode() : 0);
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }
}
