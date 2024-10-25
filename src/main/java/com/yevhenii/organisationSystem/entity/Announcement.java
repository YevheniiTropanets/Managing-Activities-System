package com.yevhenii.organisationSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcement")
public class Announcement extends BaseEntity {
    @Column(name = "description")
    String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerid")
    ActivityBanner activityBanner;
    @Column(name = "dateposted")
    Timestamp datePosted;


    public Announcement(Long id) {
        super(id);
    }

    public String getFormattedDate() {
        Locale ukrainianLocale = new Locale("uk", "UA");
        LocalDateTime localDateTime = datePosted.toLocalDateTime();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", ukrainianLocale);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", ukrainianLocale);
        // Об'єднання дати та часу в потрібному форматі
        return localDateTime.format(dateFormatter) + " о " + localDateTime.format(timeFormatter);
    }
}
