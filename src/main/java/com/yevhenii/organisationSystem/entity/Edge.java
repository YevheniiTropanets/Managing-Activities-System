package com.yevhenii.organisationSystem.entity;

import com.yevhenii.organisationSystem.entity.enums.EApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "edge")
public class Edge extends BaseEntity {
    @Column(name = "startdate")
    private Timestamp date;
    @Column(name = "ismatching")
    private boolean isMatching;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EApplicationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venueid")
    Venue venue;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applvenueid")
    //@JsonBackReference
    ApplicationToGetVenue applicationToGetVenue;

    public Edge(Long id) {
        super(id);
    }

    public Edge(Long id, Timestamp date, boolean isMatching) {
        super(id);
        this.date = date;
        this.isMatching = isMatching;
    }


    public Edge(ApplicationToGetVenue applicationToGetVenue, Venue venue) {
        this.applicationToGetVenue = applicationToGetVenue;
        this.venue = venue;
    }

    //    public void setMatching(boolean matching) {
//        isMatching = matching;
//    }

    @Override
    public String toString() {
        return "Edge{" +
                "date=" + date +
                ", isMatching=" + isMatching +
                ", venue=" + venue +
                ", applicationToGetVenue=" + applicationToGetVenue +
                '}';
    }
}
