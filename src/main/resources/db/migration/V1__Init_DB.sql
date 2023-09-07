create table ChatMessage (id bigserial not null, content varchar(255) not null, date timestamp(6), messageType smallint, recipient varchar(255), sender varchar(255) not null, primary key (id));
create table ChatRoom (id bigserial not null, chatId varchar(255) not null, primary key (id));
create table ChatRoom_ChatMessage (ChatRoom_id bigint not null, chatMessages_id bigint not null);
create table Company (id bigserial not null, INN varchar(255), active boolean, dateOfCreate timestamp(6), description varchar(255), name varchar(255), creator_id bigint, primary key (id));
create table message_likes (message_id bigint not null, user_id bigint not null, primary key (message_id, user_id));
create table user_role (user_id bigint not null, roles varchar(255));
create table user_subscriptions (subscriber_id bigint not null, channel_id bigint not null, primary key (channel_id, subscriber_id));
create table usrs (id bigserial not null, activationCode varchar(255), active boolean not null, email varchar(255), password varchar(255), username varchar(255), company_id bigint, primary key (id));
create table usrs_ChatRoom (usrs_id bigint not null, chatRooms_id bigint not null);
create table vacancy (id bigserial not null, description varchar(10240), filename varchar(255), salary varchar(255), tag varchar(50), title varchar(2048), user_id bigint, company_id bigint, primary key (id));
create table vacancy_usrs (vacancy_id bigint not null, numberOfResponded_id bigint not null, primary key (vacancy_id, numberOfResponded_id));
alter table if exists ChatRoom_ChatMessage add constraint UK_i4wljg8iblhjcabb4xm4xml1m unique (chatMessages_id);
alter table if exists usrs_ChatRoom add constraint UK_cpy6hnsxq9l7r1c43s2qsru8d unique (chatRooms_id);
alter table if exists vacancy_usrs add constraint UK_99hbrow7nmuxk3ugi68u915xx unique (numberOfResponded_id);
alter table if exists ChatRoom_ChatMessage add constraint FKqaitdlhnpk0c9tnjkrwsm2ktw foreign key (chatMessages_id) references ChatMessage;
alter table if exists ChatRoom_ChatMessage add constraint FKnc3cuqbm98et6jjn0199cs7g foreign key (ChatRoom_id) references ChatRoom;
alter table if exists Company add constraint FKmfhg21cf4v0vm9fnaiat24k1l foreign key (creator_id) references usrs;
alter table if exists message_likes add constraint FKln7xpsmeamu796o4t4a0cg8bs foreign key (user_id) references usrs;
alter table if exists message_likes add constraint FKfl1r4g3l24oevq72t9kb472ii foreign key (message_id) references vacancy;
alter table if exists user_role add constraint FKj6q10q8158i3jfoeilrjlee6k foreign key (user_id) references usrs;
alter table if exists user_subscriptions add constraint FKmptsm3lpxgkh9ai5pjq8tty59 foreign key (channel_id) references usrs;
alter table if exists user_subscriptions add constraint FKpxnw5lbtt0mkor1v181aj89ds foreign key (subscriber_id) references usrs;
alter table if exists usrs add constraint FK57wwi7c0114tuvanfmwj0w9vj foreign key (company_id) references Company;
alter table if exists usrs_ChatRoom add constraint FKmitxvbx36jyd3wdnwem2n71g9 foreign key (chatRooms_id) references ChatRoom;
alter table if exists usrs_ChatRoom add constraint FKql2j8kcjsll7pwuud2ev00xnu foreign key (usrs_id) references usrs;
alter table if exists vacancy add constraint FKrcceb69v07kjeeg4mvy4ca6ju foreign key (user_id) references usrs;
alter table if exists vacancy add constraint FKk45dom1i5osttxuywn0sjbyhg foreign key (company_id) references Company;
alter table if exists vacancy_usrs add constraint FK5dje5gv0neeeup8orp9lcnvdj foreign key (numberOfResponded_id) references usrs;
alter table if exists vacancy_usrs add constraint FKsnux4eh2msr84payr8ufj2m0l foreign key (vacancy_id) references vacancy;

insert into usrs (active, email, password, username)
values (true, 'den4ic369@gmail.com', '$2a$12$rT5sQfZnE8a/T.9r58Oxzu5lFx0pT9GaWLsdwK/TvPrwjg1hdt2z6', '1'),
       (true, 'den@gmail.com', '$2a$12$rT5sQfZnE8a/T.9r58Oxzu5lFx0pT9GaWLsdwK/TvPrwjg1hdt2z6', '2'),
       (true, 'den4ic3@gmail.com', '$2a$12$rT5sQfZnE8a/T.9r58Oxzu5lFx0pT9GaWLsdwK/TvPrwjg1hdt2z6', '3'),
       (true, 'asfasf@mail.com', '$2a$12$rT5sQfZnE8a/T.9r58Oxzu5lFx0pT9GaWLsdwK/TvPrwjg1hdt2z6', '4');

insert into user_role (user_id, roles)
values (1, 'USER'),
       (2, 'HR'),
       (3, 'COMPANYCREATOR'),
       (4, 'ADMIN');

insert into Company (INN, active, dateOfCreate, description, name, creator_id)
values ('12412', true, '2023-08-31 20:48:45.871', 'safas', 'Tin', '3');

update usrs
set company_id = 1
where id = 2
   or id = 3

-- update usrs set company_id = '1';