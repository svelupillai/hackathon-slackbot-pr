package com.application;

public class WebHookHelper {

    public static String getUserFriendlyAction(String action){

        switch(action){
            case "synchronize":
                return "updated with push";
            case "edited":
                return "description edited";
            default:
                return action;
        }
    }
}
