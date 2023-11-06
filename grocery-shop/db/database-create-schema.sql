create or replace table deals
(
    deal_id   int auto_increment
        primary key,
    deal_enum varchar(255) not null,
    constraint deals_pk2
        unique (deal_enum)
);

create or replace table products
(
    product_id int auto_increment
        primary key,
    name       varchar(255) not null,
    price      int          not null,
    constraint products_pk2
        unique (name)
);

create or replace table deals_products
(
    deal_id    int null,
    product_id int null,
    constraint deals_products_deals_deal_id_fk
        foreign key (deal_id) references deals (deal_id),
    constraint deals_products_products_product_id_fk
        foreign key (product_id) references products (product_id)
);

create or replace table users
(
    user_id    int auto_increment
        primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    username   varchar(255) not null,
    constraint users_pk2
        unique (username)
);

create or replace table users_products
(
    user_id    int null,
    product_id int null,
    constraint users_products_products_product_id_fk
        foreign key (product_id) references products (product_id),
    constraint users_products_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

