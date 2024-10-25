package com.yevhenii.organisationSystem.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "amountseats")
    private int amountSeats;
    @Column(name = "organisation")
    private String organisation;
    @Column(name= "genre")
    private String genre;
    @Column(name = "activitytype")
    private String activityType;
    @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userID")
    User user;
    @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
    List<ApplicationToGetVenue> applicationToGetVenueList;
    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    List<PlannedActivities> plannedActivitiesList;
    @OneToOne(mappedBy = "activity",fetch = FetchType.EAGER)
    ActivityBanner activityBanner;

    public Activity(Long id) {
        super(id);
    }

    public Activity(String title, String description, int amountSeats, String organisation, String genre, String activityType) {
        this.title = title;
        this.description = description;
        this.amountSeats = amountSeats;
        this.organisation = organisation;
        this.genre = genre;
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amountSeats=" + amountSeats +
                ", organisation='" + organisation + '\'' +
                ", genre='" + genre + '\'' +
                ", activityType='" + activityType + '\'' +
                '}';
    }
}
