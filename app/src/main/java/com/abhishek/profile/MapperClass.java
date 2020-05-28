package com.abhishek.profile;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "Profile")
public class MapperClass {
    String name,title,mobileNo,gender,dob,experience,hospital,city,country,designation;

    public String getName() {
        return name;
    }

    public MapperClass(String name, String title, String mobileNo, String gender, String dob, String experience, String hospital, String city, String country, String designation) {
        this.name = name;
        this.title = title;
        this.mobileNo = mobileNo;
        this.gender = gender;
        this.dob = dob;
        this.experience = experience;
        this.hospital = hospital;
        this.city = city;
        this.country = country;
        this.designation = designation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
