package com.alialperen.timeTableGenerator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

import java.util.ArrayList;


public class Subject {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private Long id;

    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int hours;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private int sessionsPerWeek;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private int sessionsScheduled = 0;
    private  static int counter=0;
    private List<Integer> sessionDurations = new ArrayList<>();

    public Subject(String name, int hours, int sessionsPerWeek, List<Integer> sessionDurations) {
        this.name = name;
        this.hours = hours;
        this.sessionsPerWeek = sessionsPerWeek;
        this.sessionDurations = sessionDurations;
    }

    public Subject(Long id,String name, int hours, int sessionsPerWeek, List<Integer> sessionDurations) {
        this.id=id;
        this.name = name;
        this.hours = hours;
        this.sessionsPerWeek = sessionsPerWeek;
        this.sessionDurations = sessionDurations;
    }



    public Subject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        //System.out.print(sessionsScheduled+"/"+ sessionsPerWeek);
        //System.out.println(getSessionDurations());
        int last_Index=sessionDurations.size()-1;
        if (sessionsScheduled>=last_Index){
            return  sessionDurations.get(last_Index);
        }
        return sessionDurations.get(0);
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getSessionsPerWeek() {
        return sessionsPerWeek;
    }

    public void setSessionsPerWeek(int sessionsPerWeek) {
        this.sessionsPerWeek = sessionsPerWeek;
    }

    public List<Integer> getSessionDurations() {
        return sessionDurations;
    }

    public void setSessionDurations(List<Integer> sessionDurations) {
        this.sessionDurations = sessionDurations;
    }

    public int getSessionsScheduled() {
        return sessionsScheduled;
    }

    public void setSessionsScheduled(int sessionsScheduled) {
        this.sessionsScheduled = sessionsScheduled;
    }

    public boolean canScheduleMoreSessions() {
        return sessionsScheduled < sessionsPerWeek;
    }

    public void incrementSessionsScheduled() {
        sessionsScheduled++;
        counter+=1;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hours=" + hours +
                ", sessionsPerWeek=" + sessionsPerWeek +
                ", sessionDurations=" + sessionDurations +
                ", sessionsScheduled=" + sessionsScheduled +
                '}';
    }
}
