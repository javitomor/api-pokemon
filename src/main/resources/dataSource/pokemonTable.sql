drop table pokemon if exists;
create table pokemon
(id bigint generated by default as identity,
attack integer,
defense integer,
generation integer,
hp integer,
legendary boolean,
name varchar(255),
sp_atk integer,
sp_def integer,
speed integer,
total integer,
type1 varchar(255),
type2 varchar(255),
primary key (id))