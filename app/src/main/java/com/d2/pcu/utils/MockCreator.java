package com.d2.pcu.utils;

import com.d2.pcu.data.model.map.temple.Temple;

import java.util.Arrays;
import java.util.List;

public class MockCreator {

    public static List<Temple> getTemplesMock() {
        Temple temple1 = new Temple();
        temple1.setName("Some Title 1");
        temple1.setHistory("SOme big text with descriptionm");

        Temple temple2 = new Temple();
        temple2.setName("Some Title 2");
        temple2.setHistory("Some big text with descriptionm");

        Temple temple3 = new Temple();
        temple3.setName("Some Title 3");
        temple3.setHistory("Some big text with descriptionm");

        return Arrays.asList(temple1, temple2, temple3);
    }
}
