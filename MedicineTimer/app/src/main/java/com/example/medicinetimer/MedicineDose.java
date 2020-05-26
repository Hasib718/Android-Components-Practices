package com.example.medicinetimer;

public class MedicineDose {
    private String time;
    private String dose;

    public MedicineDose(String time, String dose) {
        this.time = time;
        this.dose = dose;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
