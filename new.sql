create database bank;
use bank;
create table accdet (accno bigint,name text,mob bigint,curbal bigint);
select * from accdet;
insert into accdet  values(987654321,"raj",123456789,20000);