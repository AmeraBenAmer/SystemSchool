package Degree;

public class Degree {

    private int id;
    private String nameStudent;
    private String classRoom;
    private String subjectNmae;
    private String nameTeachar;
    private Double workDegree;
    private Double finalDegree;
    private Double sumDegree;

    public Double getSumDegree() {
        return sumDegree;
    }

    public void setSumDegree(Double sumDegree) {
        this.sumDegree = sumDegree;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getSubjectNmae() {
        return subjectNmae;
    }

    public void setSubjectNmae(String subjectNmae) {
        this.subjectNmae = subjectNmae;
    }

    public String getNameTeachar() {
        return nameTeachar;
    }

    public void setNameTeachar(String nameTeachar) {
        this.nameTeachar = nameTeachar;
    }

    public Double getWorkDegree() {
        return workDegree;
    }

    public void setWorkDegree(Double workDegree) {
        this.workDegree = workDegree;
    }

    public Double getFinalDegree() {
        return finalDegree;
    }

    public void setFinalDegree(Double finalDegree) {
        this.finalDegree = finalDegree;
    }
}
