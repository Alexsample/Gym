package com.alex.gymwork.enums;

public enum GroupsNames {
    ARMS("arms"),
    SHOULDERS("shoulders"),
    STOMACH("stomach"),
    BACK("back"),
    CHEST("chest"),
    LEGS("legs");

    private String groupName;

    GroupsNames(final String group) {
        this.groupName = group;
    }

    public String getGroupName() {
        return groupName;
    }

}
