package com.yevhenii.organisationSystem.dto.mapper;


import com.yevhenii.organisationSystem.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public Long toIdEntity(BaseEntity entity) {
        return entity.getId();
    }
    public City  toCity(String name){
        return  new City(name);
    }
    public Street toStreet(Long name){
        return  new Street(name);
    }
    public User toUser(Long id) {
        return new User(id);
    }
    public Venue toVenue(Long id){return new Venue(id);}
    public Profile toProfile(Long id){return  new Profile(id);}
    public ActivityBanner toBanner(Long id){return  new ActivityBanner(id);}

}
