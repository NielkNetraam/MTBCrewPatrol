drop table athlete;

create table athlete 
( id integer not null auto_increment
, resource_state integer
, firstname varchar(255)
, lastname varchar(255)
, city varchar(255)
, country varchar(255)
, profile varchar(255)
, profile_medium varchar(255)
, athlete_type integer
, badge_type_id integer
, created_at tinyblob
, date_preference varchar(255)
, email varchar(255)
, follower integer
, follower_count integer
, friend integer
, friend_count integer
, ftp integer
, gender integer
, measurement_preference integer
, mutual_friend_count integer
, premium bit
, state varchar(255)
, updated_at tinyblob
, username varchar(255)
, weight float
, primary key (id)
)
create table athletex
( id integer not null auto_increment
, resource_state integer
, firstname varchar(255)
, lastname varchar(255)
, city varchar(255)
, country varchar(255)
, profile varchar(255)
, profile_medium varchar(255)
, athlete_type integer
, badge_type_id integer
, created_at tinyblob
, date_preference varchar(255)
, email varchar(255)
, follower integer
, follower_count integer
, friend integer
, friend_count integer
, ftp integer
, gender integer
, measurement_preference integer
, mutual_friend_count integer
, premium bit
, state varchar(255)
, updated_at tinyblob
, username varchar(255)
, weight float
, primary key (id)
)
