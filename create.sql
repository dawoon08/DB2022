CREATE DATABASE DB2022Team11;

USE DB2022Team11;

#지역 정보 (id, 구, 동) 테이블
CREATE TABLE DB2022_AREA (
    area_id char(6),
    area_gu varchar(5) not null,
    area_dong varchar(5) not null,
    primary key(area_id)
);

#집주인 정보 테이블 (집주인id, 이름, 전화번호)
CREATE TABLE DB2022_OWNER (
    owner_id char(6),
    owner_name varchar(20),
    owner_number char(13),
    primary key(owner_id)
);

#부동산 정보 테이블(부동산id, 이름, 주소, 전화번호, 지역id)
CREATE TABLE DB2022_AGENCY (
    agency_id char(6) not null,
    agency_name varchar(5),
    agency_address varchar(30),
    agency_number char(13),
    area_id char(6),
    primary key(agency_id),
    foreign key (area_id) references DB2022_AREA(area_id)
);

#건물 정보 테이블(건물id, 건물명, 건물유형, 지역id)
CREATE TABLE DB2022_BUILDING (
    building_id char(6),
    building_name varchar(10),
    building_type varchar(5) not null,
    area_id char(6),
    primary key(building_id),
    foreign key(area_id) references DB2022_AREA(area_id)
);

#매물 정보 테이블(매물id, 부동산id, 집주인id, 지역id, 매물 유형, 비용, 보증금, 나온 날짜, 빌딩id)
CREATE TABLE DB2022_SALE (
    Pid char(6) not null,
    agency_id char(6) not null,
    owner_id char(6) not null,
    area_id char(6) not null,
    rent_type char(2) not null,
    price int,
    deposit int,
    sale_date date,
    building_id char(6) not null,
    address varchar(50) not null,
    primary key(Pid),
    foreign key(area_id) references DB2022_AREA(area_id),
    foreign key(owner_id) references DB2022_OWNER(owner_id),
    foreign key(agency_id) references DB2022_AGENCY(agency_id),
    foreign key(building_id) references DB2022_BUILDING(building_id)
);

create index price_index on DB2022_SALE(price);
create index area_index on DB2022_AREA(area_id);
create index agency_index on DB2022_AGENCY(agency_id);
create index building_index on DB2022_BUILDING(building_id);

insert into DB2022_AREA values ('Lid001', '서대문구', '대현동'),
('Lid002','서대문구','신촌동'),
('Lid003','은평구','신사동'),
('Lid004','마포구','연남동');

insert into DB2022_OWNER values ('Oid001', 'Kang Yeonsoo', '010-1412-8014'),
('Oid002','Kim Serim', '010-3464-4677'),
('Oid003', 'Lee Minkyeong', '010-2275-3421'),
('Oid004','Kang Yeonsoo', '010-9036-7683');

insert into DB2022_AGENCY values ('Aid001', '이화최고', '서울시 서대문구 이화여대9길 12 1층','010-2333-2333', 'Lid001'), 
('Aid002', '벚꽃', '서울 서대문구 연세로7길 46 2층', '010-9292-9292', 'Lid002'),
('Aid003', '햇빛 쨍쨍', '서울특별시 은평구 갈현로1길 17','010-2482-8282', 'Lid003'), 
('Aid004', '건물주되기','서울특별시 마포구 동교로27길 44','010-1187-2257', 'Lid004');

insert into DB2022_BUILDING values ('Bid001','신촌앞','아파트', 'Lid001'), 
('Bid002','이화','빌라', 'Lid002'), 
('Bid003', NULL,'단독주택', 'Lid004'), 
('Bid004','어쩔디비','오피스텔', 'Lid003'), 
('Bid005', '푸르','아파트', 'Lid001'), 
('Bid006','지오', '오피스텔', 'Lid003'),
('Bid007', '파크', '빌라', 'Lid002');

insert into DB2022_SALE values ('Pid001', 'Aid002', 'Oid001', 'Lid002', '매매',80000, NULL, '2022-03-04', 'Bid002', '서울시 서대문구 신촌역로 10 901호'),
('Pid002', 'Aid001','Oid002','Lid001', '전세', 50000, NULL, '2022-01-15','Bid001','서울시 서대문구 대현동 144 104동 702호'),
('Pid003', 'Aid004', 'Oid003', 'Lid003', '월세', 55, 1000, '2021-12-28', 'Bid004','서울시 은평구 은평로 12-1 303호'),
('Pid004', 'Aid003', 'Oid004', 'Lid004', '월세', 70, 500, '2022-04-10','Bid003', '서울시 마포구 연남동 381-18'),
('Pid005', 'Aid002', 'Oid002', 'Lid002','월세', 100, 1000, '2022-03-22','Bid002','서울시 서대문구 신촌역로 10 201호'),
('Pid006', 'Aid001', 'Oid003', 'Lid001', '매매', 100000, NULL, '2022-02-13','Bid005', '서울시 서대문구 신촌로 189  302동 402호'),
('Pid007', 'Aid003', 'Oid004', 'Lid003', '전세', 70000, NULL, '2022-05-01', 'Bid006', '서울시 은평구 은평터널로 164 1013호'),
('Pid008', 'Aid002', 'Oid001', 'Lid002','월세', 150, 1000, '2022-01-25', 'Bid007', '서울시 서대문구 성산로24길 26 105호'),
('Pid009', 'Aid001', 'Oid003', 'Lid001', '매매', 110000, NULL, '2021-12-24', 'Bid005', '서울시 서대문구 신촌로 189 301동 1502호'),
('Pid010', 'Aid003', 'Oid004', 'Lid003', '전세', 30000, NULL, '2022-02-28', 'Bid004','서울시 은평구 은평로 12-1 203호'),
('Pid011', 'Aid002', 'Oid002', 'Lid002', '월세', 100, 2000, '2022-01-25', 'Bid007', '서울시 서대문구 성산로24길 26 306호');
