package com.example.medicinetimer;

import java.util.List;

public class Medicine {
    private String Name;
    private boolean activityState;
    private List<String> times;
    private String days;
    private String StartingDay;
    private List<MedicineDose> medicineDoses;

    public Medicine(String name, boolean activityState, List<String> times, String days, String startingDay, List<MedicineDose> medicineDoses) {
        Name = name;
        this.activityState = activityState;
        this.times = times;
        this.days = days;
        StartingDay = startingDay;
        this.medicineDoses = medicineDoses;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
        return StartingDay;
    }

    public void setStartingDay(String startingDay) {
        StartingDay = startingDay;
    }

    public List<MedicineDose> getMedicineDoses() {
        return medicineDoses;
    }

    public void setMedicineDoses(List<MedicineDose> medicineDoses) {
        this.medicineDoses = medicineDoses;
    }
}
