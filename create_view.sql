
create view daehyun_dong  as 
select DB2022_area.area_id, area_dong, building_name, building_type, building_id
from DB2022_AREA, DB2022_BUILDING
where DB2022_AREA.area_id = 'Lid001' and DB2022_BUILDING.area_id = 'Lid001';

create view sinchon_dong  as 
select DB2022_area.area_id, area_dong, building_name, building_type, building_id
from DB2022_AREA, DB2022_BUILDING
where DB2022_AREA.area_id = 'Lid002' and DB2022_BUILDING.area_id = 'Lid002';

create view sinsa_dong  as 
select DB2022_area.area_id, area_dong, building_name, building_type, building_id
from DB2022_AREA, DB2022_BUILDING
where DB2022_AREA.area_id = 'Lid003' and DB2022_BUILDING.area_id = 'Lid003';

create view yeonnam_dong  as 
select DB2022_area.area_id, area_dong, building_name, building_type, building_id
from DB2022_AREA, DB2022_BUILDING
where DB2022_AREA.area_id = 'Lid004' and DB2022_BUILDING.area_id = 'Lid004';

