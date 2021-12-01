DROP TABLE IF EXISTS line;
DROP TABLE IF EXISTS start_times;
DROP TABLE IF EXISTS segment;
DROP TABLE IF EXISTS passengers;
DROP TABLE IF EXISTS stop;

CREATE TABLE line(
name VARCHAR(50) NOT NULL,
first_stop VARCHAR(50) NOT NULL,
UNIQUE(name, first_stop)
);

CREATE TABLE start_times(
line_name VARCHAR(50) NOT NULL,
start_time INTEGER NOT NULL
);

CREATE TABLE segment(
orderID INTEGER NOT NULL,
line_name VARCHAR(50) NOT NULL,
time_diff INTEGER NOT NULL,
capacity INTEGER NOT NULL,
next_stop VARCHAR(50) NOT NULL,
PRIMARY KEY(orderID, line_name)
);

CREATE TABLE passengers(
line_name VARCHAR(50) NOT NULL,
orderID INTEGER NOT NULL,
time INTEGER NOT NULL,
passengers_num INTEGER NOT NULL,
PRIMARY KEY(line_name, orderID, time)
);

CREATE TABLE stop(
name VARCHAR(50) NOT NULL,
line_name VARCHAR(50) NOT NULL,
UNIQUE(name, line_name)
);

insert into stop values ('A1','1');
insert into stop values ('B12','1');
insert into stop values ('B12','2');
insert into stop values ('C12','1');
insert into stop values ('C12','2');
insert into stop values ('Q2','2');
insert into stop values ('A2','2');

--Line1
insert into passengers values ('1',0, 6, 5);
insert into passengers values ('1',0, 7, 6);
insert into passengers values ('1',1, 11, 6);
insert into passengers values ('1',1, 12, 7);

insert into segment values (0,'1',5, 7, 'B12');
insert into segment values (1,'1',5, 8, 'C12');

insert into line values ('1','A1');

insert into start_times values ('1',1);
insert into start_times values ('1',2);
--Line1-end

--Line2
insert into passengers values ('2',0, 4, 5);
insert into passengers values ('2',0, 6, 6);
insert into passengers values ('2',1, 5, 9);
insert into passengers values ('2',1, 7, 9);
insert into passengers values ('2',2, 7, 9);
insert into passengers values ('2',2, 9, 9);

insert into segment values (0,'2',1, 10, 'B12');
insert into segment values (1,'2',1, 10, 'Q2');
insert into segment values (2,'2',2, 10, 'C12');

insert into line values ('2','A2');

insert into start_times values ('2',3);
insert into start_times values ('2',5);
--Line2-end