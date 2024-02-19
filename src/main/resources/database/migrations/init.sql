create table if not exists Posts (
                                     id serial primary key,
                                     title text not null,
                                     description text not null,
                                     likes int default 0,
                                     account_id int references accounts (id)
    );

create table if not exists Comments (
                                        id serial primary key,
                                        account_id int references accounts (id),
    post_id int references posts (id)
    );

create table if not exists Comment (
                                       id serial primary key,
                                       description text not null,
                                       dt timestamp default now(),
    likes int default 0,
    account_id int references accounts (id)
    );

create table if not exists Liked (
    id serial primary key,
    account_id int references accounts (id),
    post_comment_id int,
    type text not null
    );

create table if not exists Reports (
    account_id int references accounts (id),
    post_comment_id int,
    type text not null
);