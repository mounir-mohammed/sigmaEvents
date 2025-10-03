INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Field of Play / Terrain',	1	,'Field of Play / Terrain',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Dressing Rooms / Vestiaires',	2	,'Dressing Rooms / Vestiaires',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Public Stands / Tribunes',	3	,'Public Stands / Tribunes',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Operation Areas / Zones Opérationnelles',	4	,'Operation Areas / Zones Opérationnelles',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('VIP Area / Zone VIP', 1000000	,'VIP Area / Zone VIP',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Media Tribune / Tribune Média',	6	,'Media Tribune / Tribune Média',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Media Centre / Centre Média',	7	,'Media Centre / Centre Média',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Broadcast Area / Zone TV',	8	,'Broadcast Area / Zone TV',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Hospitality Area / Hospitalité',	9	,'Hospitality Area / Hospitalité',	b'1'	, 1000000	);
INSERT INTO sigmaevents.area (area_name, area_abreviation, area_description, area_stat, event_event_id) VALUES('Official Hotels'	,	10	,'Official Hotels',	b'1'	, 1000000	);

UPDATE sigmaevents.area SET area_color='#ffffff' WHERE event_event_id = '1000000'