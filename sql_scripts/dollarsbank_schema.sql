create database dollarsbank;

use dollarsbank;

create table customer (
	id INT not null auto_increment,
    username varchar(20) not null unique,
    name varchar(50) not null,
    password varchar(20) not null,
    address varchar(60),
    phone varchar(12),
    primary key (id)
);

create table account(
	id int not null auto_increment,
	balance double,
    date_created datetime(6),
    type varchar(12),
    cust_id int not null,
    primary key (id),
    foreign key (cust_id)
		references customer (id)
);

create table transactions(
	cust_id int not null,
    description varchar(255),
    trans_balance double,
    timestamp datetime(6),
    account_id int not null,
    foreign key (cust_id)
		references customer (id),
	foreign key (account_id)
		references account (id)
);