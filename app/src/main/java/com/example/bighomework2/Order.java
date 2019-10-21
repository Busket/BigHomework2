package com.example.bighomework2;

import java.sql.Time;
import java.util.Date;

public class Order {
    public String Title;
    public Date Time;
    public String Address;
    public int Pnumber;
    public String Event;
    public java.sql.Time Aging;
    public int Reward;
    public String Contact;
    public String Order_Number;
    public String Order_Status;
    public String Helper;
    public String Seeker;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getPnumber() {
        return Pnumber;
    }

    public void setPnumber(int pnumber) {
        Pnumber = pnumber;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public java.sql.Time getAging() {
        return Aging;
    }

    public void setAging(java.sql.Time aging) {
        Aging = aging;
    }

    public int getReward() {
        return Reward;
    }

    public void setReward(int reward) {
        Reward = reward;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getOrder_Number() {
        return Order_Number;
    }

    public void setOrder_Number(String order_Number) {
        Order_Number = order_Number;
    }

    public String getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(String order_Status) {
        Order_Status = order_Status;
    }

    public String getHelper() {
        return Helper;
    }

    public void setHelper(String helper) {
        Helper = helper;
    }

    public String getSeeker() {
        return Seeker;
    }

    public void setSeeker(String seeker) {
        Seeker = seeker;
    }
}
