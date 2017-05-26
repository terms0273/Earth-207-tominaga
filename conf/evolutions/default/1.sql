# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admins (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_admins primary key (id))
;

create table user (
  id                        bigint not null,
  userid                    varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  admin                     boolean,
  dltflg                    boolean,
  constraint pk_user primary key (id))
;

create sequence admins_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admins;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admins_seq;

drop sequence if exists user_seq;

