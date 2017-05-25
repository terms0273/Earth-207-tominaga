# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admins (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_admins primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  userid                    varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  admin                     tinyint(1) default 0,
  dltflg                    tinyint(1) default 0,
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table admins;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

