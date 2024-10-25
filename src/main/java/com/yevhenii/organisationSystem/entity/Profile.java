package com.yevhenii.organisationSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="profile")
public class Profile extends BaseEntity {
    @Column(name="organisation")
    String  organisation;
    @Column(name ="firstname")
    String firstname;
    @Column(name = "surname")
    String surname;
    @Column(name = "phone")
    String phone;
    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="userid")
    User user;

    public Profile(Long id) {
        super(id);
    }
}
