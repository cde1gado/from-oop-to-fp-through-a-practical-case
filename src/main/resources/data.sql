
create table users (
  id varchar(8) not null primary key,
  email varchar(30) not null
);

create table books (
  id varchar(8) not null primary key,
  title varchar(40) not null,
  user_id varchar(8),
  foreign key (user_id) references users (id)
);

insert into users (id, email) values ('1', 'user@mail.com');

insert into books (id, title, user_id) values ('1', 'book_1_1', '1');
