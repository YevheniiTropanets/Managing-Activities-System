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
@Table(name = "city")
public class City extends BaseEntity {

    @Column(name = "cityname")
    private String cityName;
    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    List<Street> streetList;

    public City(Long id) {
        super(id);
    }

    public City(String cityName) {
        this.cityName = cityName;
    }
}
