package com.example.backendtest.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class TakesEntityPK implements Serializable {
    private int studentId;
    private int courseId;

    @Column(name = "student_id")
    @Id
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Column(name = "course_id")
    @Id
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TakesEntityPK that = (TakesEntityPK) o;

        if (studentId != that.studentId) return false;
        if (courseId != that.courseId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + courseId;
        return result;
    }
}
