SELECT COUNT(*) FROM accreditation INNER JOIN accreditation_type ON accreditation.accreditation_type_accreditation_type_id = accreditation_type.accreditation_type_id WHERE accreditation.event_event_id = 1000001 AND accreditation_type.accreditation_type_value = 'GRAND PUBLIC' 
SELECT COUNT(*) FROM CODE WHERE event_event_id = 1000001 AND code_used = TRUE AND code_entity_value = 'GRAND PUBLIC'

SELECT code_code_id FROM accreditation INNER JOIN accreditation_type ON accreditation.accreditation_type_accreditation_type_id = accreditation_type.accreditation_type_id WHERE accreditation.event_event_id = 1000001 AND accreditation_type.accreditation_type_value = 'GRAND PUBLIC' 


SELECT * FROM STATUS 

SELECT * FROM rel_accreditation__site
DELETE FROM rel_accreditation__site WHERE accreditation_accreditation_id IN (SELECT accreditation_id from accreditation WHERE event_event_id = 1000001 AND status_status_id != 1);
DELETE from accreditation WHERE event_event_id = 1000001 AND status_status_id != 1

SELECT * FROM accreditation WHERE event_event_id = 1000001

UPDATE accreditation SET code_code_id = NULL WHERE event_event_id = 1000001;
UPDATE code SET code_used = FALSE WHERE event_event_id = 1000001;

SELECT * FROM code WHERE code_used = false  AND event_event_id = 1000001;

SELECT * FROM accreditation WHERE event_event_id = 1000001

SELECT * FROM code WHERE code_used = TRUE AND event_event_id = 1000001


SELECT * FROM code WHERE code_value = 'qrs2vmjo6h2yf24d';



