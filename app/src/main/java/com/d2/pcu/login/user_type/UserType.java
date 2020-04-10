package com.d2.pcu.login.user_type;

public enum UserType {
    BELIEVER, CLERGY, BISHOP, EMPTY;

    @Override
    public String toString() {
        switch (ordinal()){
            case 0 : return "believer";
            case 1 : return "сlergy";
            case 2 : return "bishop";
            default: return "undefined";
        }
    }

    public static UserType fromString(String userType){
        switch (userType){
            case "believer" : return BELIEVER;
            case "сlergy" : return CLERGY;
            case "bishop" : return BISHOP;
            default   : return EMPTY;
        }
    }

//    public String toUrlHeader() {
//        switch (ordinal()){
//            case 0 : return "ua-UA";
//            case 1 : return "ru-RU";
//            case 2 : return "en-EN";
//            default: return "ua-UA";
//        }
//    }
}
