package com.chanbook.dark.launch;

import com.chanbook.dark.launch.feature.DarkFeature;

public class DarkDemo {
    public static void main(String[] args) {
        DarkLaunch darkLaunch = new DarkLaunch();
        DarkFeature defaultFeature = darkLaunch.getDarkFeature("call_newapi_getUserById");
        System.out.println(defaultFeature.enabled());
        System.out.println(defaultFeature.dark(893));
    }
}
