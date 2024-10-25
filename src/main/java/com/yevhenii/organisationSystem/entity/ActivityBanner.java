package com.yevhenii.organisationSystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "banerpage")
public class ActivityBanner extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activityid")
    private Activity activity;
    @Column(name = "description", nullable = false) // Опис банера
    private String description;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image") // Поле для зображення
    private byte[] image;
    @OneToMany(mappedBy = "activityBanner",fetch = FetchType.LAZY)
    List<Announcement> announcementList;



    public ActivityBanner(Long id) {
        super(id);
    }

    public String generateBase64Image() {
        return Base64.encodeBase64String(this.image);
    }
}
