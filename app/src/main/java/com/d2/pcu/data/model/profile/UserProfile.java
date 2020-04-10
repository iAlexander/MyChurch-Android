package com.d2.pcu.data.model.profile;

import com.d2.pcu.data.model.diocese.Diocese;
import com.d2.pcu.data.model.map.temple.BaseTemple;

import java.util.Date;

public class UserProfile {

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String phone;

    private boolean acceptAgreement;

    private BaseTemple church;

    private Diocese diocese;

    private String member;

    private String san;

    private Date birthday;

    private Date angelday;

    private String firebaseToken;

    public UserProfile() {
        firstName = "";
        lastName = "";
        middleName = "";
        email = "";
        phone = "";
        acceptAgreement = true;
        church = new BaseTemple();
        diocese = new Diocese();
        member = "";
        san = "";
        birthday = new Date();
        angelday = new Date();
        firebaseToken = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAcceptAgreement() {
        return acceptAgreement;
    }

    public void setAcceptAgreement(boolean acceptedAgreement) {
        this.acceptAgreement = acceptedAgreement;
    }

    public BaseTemple getChurch() {
        return church;
    }

    public void setChurch(BaseTemple church) {
        this.church = church;
    }

    public Diocese getDiocese() {
        return diocese;
    }

    public void setDiocese(Diocese diocese) {
        this.diocese = diocese;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSan() {
        return san;
    }

    public void setSan(String san) {
        this.san = san;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getAngelday() {
        return angelday;
    }

    public void setAngelday(Date angelday) {
        this.angelday = angelday;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
