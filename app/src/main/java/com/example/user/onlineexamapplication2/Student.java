package com.example.user.onlineexamapplication2;

public class Student {
    private String name;
    private String phn;
    private String email;


    public Student(String name, String phn, String email) {
        this.name = name;
        this.phn = phn;
        this.email = email;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

}
