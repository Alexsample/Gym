package com.alex.gymwork.enums;

public enum IntentKeys {
    GROUP("group"),
    EXERCISE_NAME("exercise_name"),
    EXERCISE_DESCRIPTION("exercise_description"),
    PROGRAM_NAME("program_name"),
    WEEKDAY_NAME("weekday_name"),
    EXERCISE_IN_PROGRAM("exercise_in_program"),
    SET("set"),
    WEIGHT("weight"),
    REPEATS("repeats");


    private String intentKey;

    IntentKeys(final String intentKey) {
        this.intentKey = intentKey;
    }

    public String getIntentKey() {
        return intentKey;
    }
}
