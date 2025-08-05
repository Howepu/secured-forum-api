create table users (
    id bigserial primary key,
    username text unique,
    password text
);

create table roles (
    id bigserial primary key,
    name text unique
);

create table posts (
    id bigserial primary key,
    title text,
    content text,
    user_id bigint,
    foreign key (user_id) references users (id) on delete cascade
);

create table comments (
    id bigserial primary key,
    title text,
    content text,
    user_id bigint,
    post_id bigint,
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (post_id) references posts (id) on delete cascade
);

create table users_roles (
    user_id bigint,
    role_id bigint,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (role_id) references roles (id) on delete cascade
);