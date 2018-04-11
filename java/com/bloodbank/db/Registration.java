package com.bloodbank.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Registration")
public class Registration {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public char gender;
    public String email;
    public String mobileNumber;
    public String address;
    public String street;
    public String city;
    public String state;
    public String country;
    public String bloodGroup;
}
