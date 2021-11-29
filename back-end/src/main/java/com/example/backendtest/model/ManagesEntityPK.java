package com.example.backendtest.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ManagesEntityPK implements Serializable {
    private int teacherId;
    private int courseId;

    @Column(name = "teacherId")
    @Id
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name = "courseId")
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

        ManagesEntityPK that = (ManagesEntityPK) o;

        if (teacherId != that.teacherId) return false;
        if (courseId != that.courseId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teacherId;
        result = 31 * result + courseId;
        return result;
    }
}
