package com.example.backendtest.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Checks", schema = "backend_demo", catalog = "")
@IdClass(ChecksEntityPK.class)
public class ChecksEntity {
    private int teacherId;
    private int taskId;
    private int studentId;
    private Timestamp checkTime;
    private Integer score;

    @Id
    @Column(name = "teacherId")
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Id
    @Column(name = "taskId")
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Id
    @Column(name = "studentId")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "checkTime")
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
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

        ChecksEntity that = (ChecksEntity) o;

        if (teacherId != that.teacherId) return false;
        if (taskId != that.taskId) return false;
        if (studentId != that.studentId) return false;
        if (checkTime != null ? !checkTime.equals(that.checkTime) : that.checkTime != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teacherId;
        result = 31 * result + taskId;
        result = 31 * result + studentId;
        result = 31 * result + (checkTime != null ? checkTime.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }
}
