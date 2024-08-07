create table if not exists Taco_Order (
    id identity,
    name varchar(50) not null,
    street varchar(50) not null,
    city varchar(50) not null,
    state varchar(50) not null,
    zip varchar(10) not null,
    cc_Number varchar(16) not null,
    cc_Expiration varchar(5) not null,
    cc_Cvv varchar(3) not null,
    created_At timestamp
);
create table if not exists Taco (
    id identity,
    name varchar(50) not null,
    taco_order bigint,
--     taco_order_key bigint,
    created_at timestamp not null
);
create table if not exists Ingredient_Ref (
    taco bigint not null,
    ingredient varchar(4) not null
--     taco_key bigint not null
);
create table if not exists Ingredient (
    id varchar(4) not null,
    name varchar(25) not null,
    type varchar(10)
);
alter table Taco add foreign key (taco_order) references Taco_Order(id);
alter table Ingredient_Ref add foreign key (taco) references Taco(id);
alter table Ingredient_Ref add foreign key (ingredient) references Ingredient(id);