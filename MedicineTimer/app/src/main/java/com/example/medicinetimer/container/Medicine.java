package com.example.medicinetimer.container;

import java.util.List;

public class Medicine {
    private String name;
    private boolean activityState;
    private List<String> times;
    private String days;
    private String startingDay;
    private List<MedicineDose> medicineDoses;

    public Medicine() {}

    public Medicine(String name, boolean activityState, List<String> times, String days, String startingDay, List<MedicineDose> medicineDoses) {
        this.name = name;
        this.activityState = activityState;
        this.times = times;
        this.days = days;
        this.startingDay = startingDay;
        this.medicineDoses = medicineDoses;
    }

    public Medicine(String name, boolean activityState, List<String> times, String days, String startingDay) {
        this.name = name;
        this.activityState = activityState;
        this.times = times;
        this.days = days;
        this.startingDay = startingDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActivityState() {
        return activityState;
    }

    public void setActivityState(boolean activityState) {
        this.activityState = activityState;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStartingDay() {
        return startingDay;
    }

    public void setStartingDay(String startingDay) {
        this.startingDay = startingDay;
    }

    public List<MedicineDose> getMedicineDoses() {
        return medicineDoses;
    }

    public void setMedicineDoses(List<MedicineDose> medicineDoses) {
        this.medicineDoses = medicineDoses;
    }
}
