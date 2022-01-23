package com.android.parleagro.model;


public class RecordModel {

    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTimeID() {
        return TimeID;
    }

    public void setTimeID(int timeID) {
        TimeID = timeID;
    }

    public String getTimeValue() {
        return TimeValue;
    }

    public void setTimeValue(String timeValue) {
        TimeValue = timeValue;
    }

    public int getPlantID() {
        return PlantID;
    }

    public void setPlantID(int plantID) {
        PlantID = plantID;
    }

    public int getMachineID() {
        return MachineID;
    }

    public void setMachineID(int machineID) {
        MachineID = machineID;
    }

    public int getFill_Volume_All_Valves() {
        return Fill_Volume_All_Valves;
    }

    public void setFill_Volume_All_Valves(int fill_Volume_All_Valves) {
        Fill_Volume_All_Valves = fill_Volume_All_Valves;
    }

    public Double getFill_Volume_3_valves_every_Hour() {
        return Fill_Volume_3_valves_every_Hour;
    }

    public void setFill_Volume_3_valves_every_Hour(Double fill_Volume_3_valves_every_Hour) {
        Fill_Volume_3_valves_every_Hour = fill_Volume_3_valves_every_Hour;
    }


    public String getPackage_Appearance() {
        return Package_Appearance;
    }

    public void setPackage_Appearance(String package_Appearance) {
        Package_Appearance = package_Appearance;
    }

    public String getDate_Coding_Rub_test() {
        return Date_Coding_Rub_test;
    }

    public void setDate_Coding_Rub_test(String date_Coding_Rub_test) {
        Date_Coding_Rub_test = date_Coding_Rub_test;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    private int TimeID;
    private String TimeValue;
    private int PlantID;
    private int MachineID;
    private int Fill_Volume_All_Valves;
    private Double Fill_Volume_3_valves_every_Hour;
    private int Fill_Closure_Torque;

    public int getFill_Closure_Torque() {
        return Fill_Closure_Torque;
    }

    public void setFill_Closure_Torque(int fill_Closure_Torque) {
        Fill_Closure_Torque = fill_Closure_Torque;
    }

    public int getClosure_jump_test() {
        return Closure_jump_test;
    }

    public void setClosure_jump_test(int closure_jump_test) {
        Closure_jump_test = closure_jump_test;
    }

    public int getClosure_Pifer_band() {
        return Closure_Pifer_band;
    }

    public void setClosure_Pifer_band(int closure_Pifer_band) {
        Closure_Pifer_band = closure_Pifer_band;
    }

    public int getClosure_Secure_Seal() {
        return Closure_Secure_Seal;
    }

    public void setClosure_Secure_Seal(int closure_Secure_Seal) {
        Closure_Secure_Seal = closure_Secure_Seal;
    }

    public int getStress_crack_Test() {
        return Stress_crack_Test;
    }

    public void setStress_crack_Test(int stress_crack_Test) {
        Stress_crack_Test = stress_crack_Test;
    }

    public int getDrop_Test() {
        return Drop_Test;
    }

    public void setDrop_Test(int drop_Test) {
        Drop_Test = drop_Test;
    }

    private int Closure_jump_test;
    private int Closure_Pifer_band;
    private int Closure_Secure_Seal;
    private int Stress_crack_Test;
    private int Drop_Test;
    private String Package_Appearance;
    private String Date_Coding_Rub_test;
    private String DATE;


}
