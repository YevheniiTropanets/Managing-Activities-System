-- to do constraints


DROP DATABASE ticket_db;
DROP TABLE
CREATE TABLE Users
(
    id       BIGSERIAL PRIMARY KEY,
    email    varchar(128) NOT NULL,
    password varchar(128) NOT NULL
);

CREATE TABLE Roles
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(32) NOT NULL
);

CREATE TABLE user_role
(
    user_id BIGSERIAL REFERENCES Users (id) NOT NULL,
    role_id BIGSERIAL REFERENCES Roles (id) NOT NULL
);


CREATE TABLE Activity
(
    id           BIGSERIAL PRIMARY KEY,
    title        varchar(128)                    NOT NULL,
    description  text                            NOT NULL,
    amountSeats  int                             NOT NULL,
    organisation varchar(128)                    NOT NULL,
    genre        varchar(30)                     NOT NULL,
    activityType varchar                         NOT NULL,
    userID       BIGSERIAL REFERENCES users (id) NOT NULL
);


CREATE TABLE City
(
    id       BIGSERIAL PRIMARY KEY,
    cityName VARCHAR(128)
);

CREATE TABLE Street
(
    id         BIGSERIAL PRIMARY KEY,
    streetName VARCHAR(128)                   NOT NULL,
    cityID     BIGSERIAL REFERENCES City (id) NOT NULL
);

CREATE TABLE Venue
(
    id           BIGSERIAL PRIMARY KEY,
    streetid       BIGSERIAL REFERENCES Street (id)  NOT NULL,
    userID       BIGSERIAL REFERENCES users (id) NOT NULL,
    title        VARCHAR(128)                    NOT NULL,
    maximumSeats int                             NOT NULL,
    rentPrice    int                             NOT NULL,
    description  text                            NOT NULL,
    adresIndex   varchar(128)                    NOT NULL
);
DROP TABLE Venue;
CREATE TABLE applicationtogetvenue
(
    id         BIGSERIAL PRIMARY KEY,
    activityID BIGSERIAL REFERENCES Activity (id) NOT NULL,
    startDate  timestamp                          NOT NULL,
    endDate    timestamp                          NOT NULL,
    status     varchar(15)                        NOT NULL DEFAULT 'EXPECT'
);
CREATE TABLE Edge
(
    id          BIGSERIAL PRIMARY KEY,
    venueID     BIGSERIAL REFERENCES venue (id)                 NOT NULL,
    applVenueID BIGSERIAL REFERENCES ApplicationToGetVenue (id) NOT NULL,
    startDate   timestamp                                       NOT NULL,
    isMatching  bool        DEFAULT 'false'                     NOT NULL,
    status      varchar(32) DEFAULT 'EXPECT',
    UNIQUE (venueID, applVenueID, startDate)
);

CREATE TABLE Profile
(
    id           BIGSERIAL PRIMARY KEY,
    userid       BIGSERIAL REFERENCES Users (id) NOT NULL,
    organisation varchar(50)                     NOT NULL,
    firstName    varchar(50)                     NOT NULL,
    surname      varchar(50)                     NOT NULL,
    phone        varchar(15)                     NOT NULL
);

CREATE TABLE BanerPage
(
    id          BIGSERIAL PRIMARY KEY,
    activityId  BIGSERIAL REFERENCES Activity (id) NOT NULL,
    description text                               NOT NULL,
    image       bytea                              NOT NULL
);
CREATE TABLE announcement
(
    id BIGSERIAL PRIMARY KEY,
    bannerId BIGSERIAL references  banerpage(id) NOT NULL ,
    description text NOT NULL,
    dateposted timestamp NOT NULL
);

CREATE TABLE PlannedActivities
(
    id         BIGSERIAL PRIMARY KEY,
    activityId BIGSERIAL REFERENCES Activity (id) NOT NULL,
    venueId    BIGSERIAL REFERENCES Venue (id)    NOT NULL,
    endDate    TIMESTAMP                          NOT NULL,
    startDate  timestamp                          NOT NULL,
    status     varchar(15)                        NOT NULL DEFAULT 'PLANNED'
);



INSERT INTO City (cityName)
VALUES ('Вінниця'),          -- Вінницька область
       ('Луцьк'),            -- Волинська область
       ('Дніпро'),           -- Дніпропетровська область
       ('Донецьк'),          -- Донецька область
       ('Житомир'),          -- Житомирська область
       ('Ужгород'),          -- Закарпатська область
       ('Запоріжжя'),        -- Запорізька область
       ('Івано-Франківськ'), -- Івано-Франківська область
       ('Київ'),             -- Київська область
       ('Кропивницький'),    -- Кіровоградська область
       ('Луганськ'),         -- Луганська область
       ('Львів'),            -- Львівська область
       ('Миколаїв'),         -- Миколаївська область
       ('Одеса'),            -- Одеська область
       ('Полтава'),          -- Полтавська область
       ('Рівне'),            -- Рівненська область
       ('Суми'),             -- Сумська область
       ('Тернопіль'),        -- Тернопільська область
       ('Харків'),           -- Харківська область
       ('Херсон'),           -- Херсонська область
       ('Хмельницький'),     -- Хмельницька область
       ('Черкаси'),          -- Черкаська область
       ('Чернівці'),         -- Чернівецька область
       ('Чернігів'); -- Чернігівська область

INSERT into roles (name)
values ('ADMIN');
INSERT into roles (name)
values ('MODER');
INSERT into roles (name)
values ('USER');