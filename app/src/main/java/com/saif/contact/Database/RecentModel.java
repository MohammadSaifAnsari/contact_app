package com.saif.contact.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Blob;

@Entity(tableName = "RecentList")
public class RecentModel {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "FirstName")
    private  String firstname;

    @ColumnInfo(name = "SurName")
    private  String surname;

    @ColumnInfo(name = "Phone no")
    private  String phoneNo;

    @ColumnInfo(name = "Email")
    private  String email;

    @ColumnInfo(name = "ProfileImg",typeAffinity = ColumnInfo.BLOB)
    private byte[] profileImg;

    @Ignore
    public RecentModel() {
    }

    public RecentModel(int id, String firstname, String surname, String phoneNo, String email, byte[] profileImg) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.email = email;
        this.profileImg = profileImg;
    }

    @Ignore
    public RecentModel(String firstname, String surname, String phoneNo, String email,byte[] profileImg) {
        this.firstname = firstname;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.email = email;
        this.profileImg = profileImg;
    }


    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

