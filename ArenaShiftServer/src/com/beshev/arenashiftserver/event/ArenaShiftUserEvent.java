package com.beshev.arenashiftserver.event;


public class ArenaShiftUserEvent {

    private String dateAsString;
    private String userEvent;

    public ArenaShiftUserEvent() {}

    public ArenaShiftUserEvent(String dateAsString, String userEvent) {
        this.dateAsString = dateAsString;
        this.userEvent = userEvent;
    }

    public String getDateAsString() {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }

    public String getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(String userEvent) {
        this.userEvent = userEvent;
    }

}
