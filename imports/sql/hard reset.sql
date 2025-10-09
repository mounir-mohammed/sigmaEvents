SELECT COUNT(*) FROM accreditation WHERE event_event_id != 1000000
SELECT * FROM 
SELECT * FROM rel_accreditation__site WHERE rel_accreditation__site.accreditation_accreditation_id NOT IN (SELECT accreditation_id FROM accreditation WHERE event_event_id != 1000000)



DELETE FROM rel_accreditation__site WHERE rel_accreditation__site.site_site_id NOT IN (SELECT site_id FROM site WHERE event_event_id != 1000000)
DELETE FROM rel_fonction__area WHERE rel_fonction__area.fonction_fonction_id NOT IN (SELECT fonction_id FROM fonction WHERE event_event_id != 1000000)

DELETE FROM rel_fonction__area WHERE rel_fonction__area.fonction_fonction_id NOT IN (SELECT fonction_id FROM fonction WHERE event_event_id != 1000000)


DELETE FROM site WHERE event_event_id != 1000000
DELETE FROM area WHERE event_event_id != 1000000
DELETE FROM category WHERE event_event_id != 1000000
DELETE FROM fonction WHERE event_event_id != 1000000





DELETE ras
FROM rel_accreditation__site ras
JOIN site s ON ras.site_site_id = s.site_id
WHERE s.event_event_id != 1000000;

DELETE FROM site WHERE event_event_id != 1000000;

DELETE ras
FROM rel_fonction__area ras
JOIN fonction s ON ras.fonction_fonction_id = s.fonction_id
WHERE s.event_event_id != 1000000;

DELETE FROM site WHERE event_event_id != 1000000;


SELECT * 
FROM rel_fonction__area ras
JOIN area s ON ras.area_area_id = s.area_id
WHERE s.event_event_id = 1000000;

DELETE FROM area WHERE event_event_id != 1000000;

SELECT * FROM area


DELETE FROM site WHERE event_event_id != 1000000;
DELETE FROM area WHERE event_event_id != 1000000;
DELETE FROM accreditation WHERE event_event_id != 1000000
DELETE FROM category WHERE event_event_id != 1000000
DELETE FROM fonction WHERE event_event_id != 1000000
DELETE FROM organiz WHERE event_event_id != 1000000
DELETE FROM accreditation_type WHERE event_event_id != 1000000
DELETE FROM printing_centre where event_event_id = 1000001
DELETE FROM EVENT WHERE event_id = 1000001

SELECT * FROM 



