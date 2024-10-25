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
@Table(name = "street")
public class Street extends BaseEntity {
    @Column(name = "streetname")
    private String streetName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityid")
    City city;
    @OneToMany(mappedBy = "street",fetch = FetchType.EAGER)
    List<Venue> venue;

    public Street(Long id) {
        super(id);
    }

    public Street(String streetName) {
        this.streetName = streetName;
    }
}
