package ru.rfpgu.classes.model;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 23.02.14
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public class ObjectModel {

    public static class Classes {

        private int id;
        private String subject;
        private int teacherId;
        private int groupId;
        private String roomNumber;
        private int lessonNumber;
        private int day;
        private int week;
        private String lastUpdate;

        @Override
        public String toString() {
            return String.valueOf(id) + " - " + subject;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public int getLessonNumber() {
            return lessonNumber;
        }

        public void setLessonNumber(int lessonNumber) {
            this.lessonNumber = lessonNumber;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }

    public static class Teacher {

        private int teacherId;
        private String teacherName;
        private String lastUpdate;

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }

    public static class Updates {

        public int id;
        public int lastUpdate;

        public String toString() {
            return id + " - " + lastUpdate;
        }
    }
}
