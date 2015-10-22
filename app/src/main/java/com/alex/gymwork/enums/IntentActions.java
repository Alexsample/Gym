package com.alex.gymwork.enums;

public enum IntentActions {
    ACTION_ADD_EXERCISE("com.alex.gymwork.intent.action.addExercise"),
    ACTION_ADD_PROGRAM("com.alex.gymwork.intent.action.addProgramName"),
    ACTION_ADD_WEEKDAY("com.alex.gymwork.intent.action.addWeekday"),
    ACTION_ADD_EXERCISE_IN_PROGRAM("com.alex.gymwork.intent.action.addExerciseInProgram"),
    ACTION_ADD_SET_WEIGHT_REPEATS("com.alex.gymwork.intent.action.addSetWeightRepeats");

    private String action;

    IntentActions(final String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

}
