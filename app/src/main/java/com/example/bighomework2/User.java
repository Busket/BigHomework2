package com.example.bighomework2;

import java.io.Serializable;

public class User implements Serializable {
    String State;//用于登录返回是否正确
    String Acount_Number;//账号
    String  Password;//密码
    String  Name;//名字
    int Credibility;//信誉值


    public User() {
        super();
    }

    public User(String Acount_Number,String  password,String  Name,int Credibility) {
        super();
       this.Acount_Number=Acount_Number;
       this.Password=Password;
       this.Name=Name;
       this.Credibility=Credibility;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAcount_Number() {
        return Acount_Number;
    }

    public void setAcount_Number(String acount_Number) {
        Acount_Number = acount_Number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = Password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCredibility() {
        return Credibility;
    }

    public void setCredibility(int credibility) {
        Credibility = credibility;
    }



}
