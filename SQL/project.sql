drop table writes;
drop table publishes;
drop table orders;
drop table bought;
drop table shop_orders;
drop table added;
drop table books;
drop table customers;
drop table publishers;
drop table authors;

create table authors(
 	author_name	varchar(40),
 	primary key (author_name)
);

create table publishers(
 	publisher_id	char(20),
	publisher_name  varchar(60),
	address			varchar(60),
	email			varchar(60),
	phone			char(40),
	bank_account	char(32),
	primary key	(publisher_id)
);

create table customers(
	customer_id		char(20),
	customer_name	varchar(60),
	address			varchar(60),
	primary key	(customer_id)
);

create table books(
 	book_id         char(20),
 	title			varchar(40),
 	author_name		varchar(60),
 	co_author_name 	varchar(40),
 	genre			varchar(40),
 	num_pages		int,
 	publisher_id	char(20),
 	price			int,
 	quantity		int,
 	primary key (book_id),
 	foreign key (author_name) references authors(author_name) on delete cascade,
	foreign key (co_author_name) references authors(author_name) on delete cascade,
	foreign key (publisher_id) references publishers(publisher_id) on delete cascade
);

create table shop_orders(
	order_number	char(20),
	curr_location	varchar(40),
	primary key	(order_number)
);

create table writes(
	book_id			char(20),
	author_name		varchar(60),
	publisher		char(20),
	primary key (book_id, author_name),
	foreign key (book_id) references books(book_id) on delete cascade,
	foreign key (author_name) references authors(author_name) on delete cascade,
	foreign key (publisher) references publishers(publisher_id) on delete cascade
);

create table publishes(
	publisher_id	char(20),
	book_id			char(20),
	primary key (publisher_id, book_id),
	foreign key (publisher_id) references publishers(publisher_id) on delete cascade,
	foreign key (book_id) references books(book_id) on delete cascade
);

create table added(
	customer_id		char(20),
	book_id			char(20),
	primary key (customer_id, book_id),
	foreign key (customer_id) references customers(customer_id) on delete cascade,
	foreign key (book_id) references books(book_id) on delete cascade
);

create table bought(
	customer_id		char(20),
	book_id			char(20),
	primary key (customer_id, book_id),
	foreign key (customer_id) references customers(customer_id) on delete cascade,
	foreign key (book_id) references books(book_id) on delete cascade
);

create table orders(
	order_number	char(20),
	customer_id		char(20),
	primary key(order_number),
	foreign key (order_number) references shop_orders(order_number) on delete cascade,
	foreign key (customer_id) references customers(customer_id) on delete cascade
);
 