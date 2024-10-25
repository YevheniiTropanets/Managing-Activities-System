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
@Table(name = "venue")
public class Venue extends BaseEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "maximumseats")
    private int maximumSeats;
    @Column(name = "description")
    private String description;
    @Column(name = "adresindex")
    private String adresIndex;
    @Column(name = "rentprice")
    private int rentPrice;
    @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn(name = "streetid")
    Street street;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "userid")
    User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venue")
    //@JsonManagedReference
    List <Edge> edgeList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venue")
    List<PlannedActivities> plannedActivitiesList;

    public Venue(Long id) {
        super(id);
    }

    public Venue(String title, int maximumSeats, String description, String adresIndex, int rentPrice) {
        this.title = title;
        this.maximumSeats = maximumSeats;
        this.description = description;
        this.adresIndex = adresIndex;
        this.rentPrice = rentPrice;
    }



    @Override
    public String toString() {
        return "Venue{" +
                "title='" + title + '\'' +
                ", maximumSeats=" + maximumSeats +
                ", description='" + description + '\'' +
                ", adresIndex='" + adresIndex + '\'' +
                ", rentPrice=" + rentPrice +
                '}';
    }


}
