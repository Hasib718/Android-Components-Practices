package com.example.medicinetimer.container;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.List;

public class Medicine implements Parcelable {
    private String name;
    private boolean activityState;
    private String times;
    private boolean continuous;
    private boolean numberOfDays;
    private String daysCount;
    private String startingDay;
    private boolean everyday;
    private boolean specificDay;
    private boolean daysInterval;
    private HashMap<String, Boolean> specifiedDays = new HashMap<>();
    private String daysIntervalCount;
    private List<MedicineDose> medicineDoses;

    public Medicine() {
        this.specifiedDays.put("Sunday", false);
        this.specifiedDays.put("Monday", false);
        this.specifiedDays.put("Tuesday", false);
        this.specifiedDays.put("Wednesday", false);
        this.specifiedDays.put("Thursday", false);
        this.specifiedDays.put("Friday", false);
        this.specifiedDays.put("Saturday", false);
    }

    public Medicine(String name, boolean activityState, String times, String daysCount, String startingDay, List<MedicineDose> medicineDoses) {
        this.name = name;
        this.activityState = activityState;
        this.times = times;
        this.daysCount = daysCount;
        this.startingDay = startingDay;
        this.medicineDoses = medicineDoses;

        this.specifiedDays.put("Sunday", false);
        this.specifiedDays.put("Monday", false);
        this.specifiedDays.put("Tuesday", false);
        this.specifiedDays.put("Wednesday", false);
        this.specifiedDays.put("Thursday", false);
        this.specifiedDays.put("Friday", false);
        this.specifiedDays.put("Saturday", false);
    }

    public Medicine(String name, boolean activityState, String times, String daysCount, String startingDay) {
        this.name = name;
        this.activityState = activityState;
        this.times = times;
        this.daysCount = daysCount;
        this.startingDay = startingDay;

        this.specifiedDays.put("Sunday", false);
        this.specifiedDays.put("Monday", false);
        this.specifiedDays.put("Tuesday", false);
        this.specifiedDays.put("Wednesday", false);
        this.specifiedDays.put("Thursday", false);
        this.specifiedDays.put("Friday", false);
        this.specifiedDays.put("Saturday", false);
    }

    protected Medicine(Parcel in) {
        name = in.readString();
        activityState = in.readBoolean();
        times = in.readString();
        continuous = in.readBoolean();
        numberOfDays = in.readBoolean();
        daysCount = in.readString();
        startingDay = in.readString();
        everyday = in.readBoolean();
        specificDay = in.readBoolean();
        daysInterval = in.readBoolean();
        daysIntervalCount = in.readString();
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

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public boolean isNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(boolean numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public boolean isEveryday() {
        return everyday;
    }

    public void setEveryday(boolean everyday) {
        this.everyday = everyday;
    }

    public boolean isSpecificDay() {
        return specificDay;
    }

    public void setSpecificDay(boolean specificDay) {
        this.specificDay = specificDay;
    }

    public boolean isDaysInterval() {
        return daysInterval;
    }

    public void setDaysInterval(boolean daysInterval) {
        this.daysInterval = daysInterval;
    }

    public HashMap<String, Boolean> getSpecifiedDays() {
        return specifiedDays;
    }

    public void setSpecifiedDays(HashMap<String, Boolean> specifiedDays) {
        this.specifiedDays = specifiedDays;
    }

    public String getDaysIntervalCount() {
        return daysIntervalCount;
    }

    public void setDaysIntervalCount(String daysIntervalCount) {
        this.daysIntervalCount = daysIntervalCount;
    }

    public String getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(String daysCount) {
        this.daysCount = daysCount;
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

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<this.medicineDoses.size(); i++) {
            stringBuilder.append(this.medicineDoses.get(i).getTime())
                    .append(", ");
        }
        times = stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", activityState=" + activityState +
                ", times='" + times + '\'' +
                ", continuous=" + continuous +
                ", numberOfDays=" + numberOfDays +
                ", daysCount='" + daysCount + '\'' +
                ", startingDay='" + startingDay + '\'' +
                ", everyday=" + everyday +
                ", specificDay=" + specificDay +
                ", daysInterval=" + daysInterval +
                ", specifiedDays=" + specifiedDays +
                ", daysIntervalCount='" + daysIntervalCount + '\'' +
                ", medicineDoses=" + medicineDoses +
                '}';
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeBoolean(activityState);
        dest.writeString(times);
        dest.writeBoolean(continuous);
        dest.writeBoolean(numberOfDays);
        dest.writeString(daysCount);
        dest.writeString(startingDay);
        dest.writeBoolean(everyday);
        dest.writeBoolean(specificDay);
        dest.writeBoolean(daysInterval);
        dest.writeMap(specifiedDays);
        dest.writeString(daysIntervalCount);
        dest.writeList(medicineDoses);
    }
}
