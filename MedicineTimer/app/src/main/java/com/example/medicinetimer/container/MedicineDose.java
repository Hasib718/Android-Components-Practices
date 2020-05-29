package com.example.medicinetimer.container;

import androidx.annotation.NonNull;

public class MedicineDose {
    private String time;
    private String dose;

    public MedicineDose() { }

    public MedicineDose(String time, String dose) {
        this.time = time;
        this.dose = dose;
    }

    public MedicineDose(String time, Double dose) {
        this.time = time;
        this.dose = Double.toString(dose);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDose() {
        return "(Take : "+dose+")";
    }

    public Double getDoseCount() {
        return Double.parseDouble(dose);
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @NonNull
    @Override
    public String toString() {
        return ("Time " + time + "\n" +
                "Dose " + dose + "\n");
    }
}
