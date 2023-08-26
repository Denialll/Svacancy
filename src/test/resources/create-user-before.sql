delete from user_role;
delete from usrs;

insert into usrs(id, active, password, username) values
(1, true, '$2a$08$d4nS51B7G6znjICKgdmZFuFzUSRps3TAWvsjK1PU41f2VRX0t5wfe', '1'),
(2, true, '$2a$08$d4nS51B7G6znjICKgdmZFuFzUSRps3TAWvsjK1PU41f2VRX0t5wfe', '2');

insert into user_role(user_id, roles) values
                                          (1, 'USER'), (1, 'ADMIN'),
                                          (2, 'USER');
