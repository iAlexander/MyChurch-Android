package com.d2.pcu.utils;

import com.d2.pcu.data.model.map.Temple;

import java.util.Arrays;
import java.util.List;

public class MockCreator {

    public static List<Temple> getTemplesMock() {
        Temple temple1 = new Temple();
        temple1.setTitle("Some Title 1");
        temple1.setDistance("100");
        temple1.setDescription("SOme big text with descriptionm");

        Temple temple2 = new Temple();
        temple2.setTitle("Some Title 2");
        temple2.setDistance("121");
        temple2.setDescription("Some big text with descriptionm");

        Temple temple3 = new Temple();
        temple3.setTitle("Some Title 3");
        temple3.setDistance("200");
        temple3.setDescription("Some big text with descriptionm");

        return Arrays.asList(temple1, temple2, temple3);
    }
}
