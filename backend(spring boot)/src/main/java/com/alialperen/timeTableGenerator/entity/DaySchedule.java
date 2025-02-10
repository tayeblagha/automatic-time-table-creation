package com.alialperen.timeTableGenerator.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;


@Entity
public class DaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "day_schedule_id") // Foreign key in the ScheduleSlot table
       private List<ScheduleSlot> slots = new ArrayList<>();

    public DaySchedule() {
    }

    public DaySchedule(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ScheduleSlot> getSlots() {
        slots.sort((sc1, sc2) -> sc1.getTime().compareTo(sc2.getTime()));
        return slots;
    }

    public void setSlots(List<ScheduleSlot> slots) {
        this.slots = slots;
    }

    public void addSlot(ScheduleSlot slot) {
        slots.add(slot);
    }

    public void removeSlot(ScheduleSlot slot) {
        slots.remove(slot);
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "id=" + id +
                ", date=" + date +
                ", slots=" + slots +
                '}';
    }
}
