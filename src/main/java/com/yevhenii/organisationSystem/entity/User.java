package com.yevhenii.organisationSystem.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "email", unique = true)
    String email;

    String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Activity> activityList;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Venue> venueList;
    @OneToOne(mappedBy ="user", fetch  = FetchType.LAZY)
    Profile profile;


    public User(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(Long id) {
        super(id);
    }
}
