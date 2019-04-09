package users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Users {
    private int id;
    private String username;
    private int password;
    private String level;
    private String birthDay;
    private String fullName;
    private String address;

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public static int validatePhoneNumber(String phone){

        Pattern p = Pattern.compile("(09)[1245][0-9]{7}");
        Matcher m = p.matcher(phone);
        if((m.find() && m.group().equals(phone)))
            return 1;
        else
            return -1;
    }
}
