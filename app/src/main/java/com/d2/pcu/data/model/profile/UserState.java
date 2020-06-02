package com.d2.pcu.data.model.profile;

public enum UserState {
    AUTHENTICATED, MODERATING, NON_AUTH, SIGNED_UP;

    @Override
    public String toString() {
        switch (ordinal()) {
            case 0:
                return "AUTHENTICATED";
            case 1:
                return "MODERATING";
            case 2:
                return "NON_AUTH";
            case 3:
                return "SIGNED_UP";
            default:
                return "undefined";
        }
    }

    public static UserState fromString(String userState) {
        switch (userState) {
            case "AUTHENTICATED":
                return AUTHENTICATED;
            case "MODERATING":
                return MODERATING;
            case "SIGNED_UP":
                return SIGNED_UP;
            default:
                return NON_AUTH;
        }
    }
}
