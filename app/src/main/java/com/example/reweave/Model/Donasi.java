package com.example.reweave.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.UUID;

public class Donasi extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String brand;
    private String color;
    private String size;
    private String callTime;
    private String info;
    private String condition;
    private String type;
    private String wornYears;
    private String extraInfo;
    private String target;
    private String photoUri;
    private boolean permission;

    public Donasi() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getCallTime() { return callTime; }
    public void setCallTime(String callTime) { this.callTime = callTime; }

    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getWornYears() { return wornYears; }
    public void setWornYears(String wornYears) { this.wornYears = wornYears; }

    public String getExtraInfo() { return extraInfo; }
    public void setExtraInfo(String extraInfo) { this.extraInfo = extraInfo; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }

    public boolean isPermission() { return permission; }
    public void setPermission(boolean permission) { this.permission = permission; }
}
