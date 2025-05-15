create table quiz_results(
id serial primary key,
quiz_name varchar(250) not  null,
mark numeric,
user_id integer,
foreign key (user_id) references users(user_id)
)