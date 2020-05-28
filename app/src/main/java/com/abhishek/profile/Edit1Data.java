package com.abhishek.profile;

import android.widget.ImageView;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Edit1Data implements Serializable {
    String title,name,mobileNo,gender,dob;
    transient ImageView profileImage;

    public Edit1Data(String title, String name, String mobileNo, String gender, String dob,ImageView profileImage) {
        this.title = title;
        this.name = name;
        this.mobileNo = mobileNo;
        this.gender = gender;
        this.dob = dob;
        this.profileImage = profileImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
