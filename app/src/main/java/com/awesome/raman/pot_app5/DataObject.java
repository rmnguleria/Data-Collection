package com.awesome.raman.pot_app5;

/**
 * Main Data . stored in csv format.
 */
public class DataObject {
    private int id;
    private long timeStamp;
    private float x_Acc;
    private float y_Acc;
    private float z_Acc;
    private float x_Gyro;
    private float y_Gyro;
    private float z_Gyro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public long getTimeStamp() {
        return timeStamp;
    }

    public float getX_Acc() {
        return x_Acc;
    }

    public float getZ_Gyro() {
        return z_Gyro;
    }

    public float getY_Gyro() {
        return y_Gyro;
    }

    public float getX_Gyro() {
        return x_Gyro;
    }

    public float getZ_Acc() {
        return z_Acc;
    }

    public float getY_Acc() {
        return y_Acc;
    }

    public DataObject(int id,long timeStamp,float x_Acc,float y_Acc,float z_Acc,float x_Gyro,float y_Gyro,float z_Gyro){
        this.id = id;
        this.timeStamp = timeStamp;
        this.x_Acc = x_Acc;
        this.y_Acc = y_Acc;
        this.z_Acc = z_Acc;

        this.x_Gyro = x_Gyro;
        this.y_Gyro = y_Gyro;
        this.z_Gyro = z_Gyro;
    }

    public String toString(){
        return getId() + "," + getTimeStamp() + "," + getX_Acc() + "," + getY_Acc() + "," + getZ_Acc() + "," + getX_Gyro() + "," + getY_Gyro() + "," + getZ_Gyro();
    }
}
