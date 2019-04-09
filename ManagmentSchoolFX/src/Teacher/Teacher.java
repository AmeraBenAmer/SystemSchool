package Teacher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Teacher {
    private int id;
    private String fullName;
    private int natNumber;
    private String birthDay;
    private String address;
    private String dateStartWork;
    private String NawWork;
    private String specialy;
    private int phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNatNumber() {
        return natNumber;
    }

    public void setNatNumber(int natNumber) {
        this.natNumber = natNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateStartWork() {
        return dateStartWork;
    }

    public void setDateStartWork(String dateStartWork) {
        this.dateStartWork = dateStartWork;
    }

    public String getNawWork() {
        return NawWork;
    }

    public void setNawWork(String nawWork) {
        NawWork = nawWork;
    }

    public String getSpecialy() {
        return specialy;
    }

    public void setSpecialy(String specialy) {
        this.specialy = specialy;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
