package com.example.qrcodescanwithretrofitvolleyandmockapi;


//entity for data model
public class History {
    private int id;

    private String location ;
    private String date;
    private String time;

    History(String location, String date, String time){
        this.location = location;
        this.date = date;
        this.time = time;
    }


    //setter getter
    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return this.location;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return this.date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return this.time;
    }
}
