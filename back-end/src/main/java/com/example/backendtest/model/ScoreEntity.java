package com.example.backendtest.model;

import javax.persistence.*;

@Entity
@Table(name = "score", schema = "backend", catalog = "")
@IdClass(ScoreEntityPK.class)
public class ScoreEntity {
    private int studentId;
    private int courseId;
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
    @Column(name = "courseId")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

        ScoreEntity that = (ScoreEntity) o;

        if (studentId != that.studentId) return false;
        if (courseId != that.courseId) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + courseId;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }
}
