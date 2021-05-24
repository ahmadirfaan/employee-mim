package com.mitramandiri.test.entities.enums;

public enum Gender {
    PRIA, WANITA;

    private static final Gender[] genderList = Gender.values();

    public static Gender getGender(int i){
        return genderList[i];
    }
}
