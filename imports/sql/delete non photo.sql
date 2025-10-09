DELETE FROM code WHERE code.code_id IN (SELECT code_code_id FROM accreditation WHERE accreditation.accreditation_photo IS NULL);


SELECT *  FROM accreditation WHERE accreditation.accreditation_photo IS NULL;
databasechangeloglock


DELETE FROM code WHERE code_id NOT IN (SELECT code_code_id FROM accreditation);

DELETE FROM organiz WHERE organiz.organiz_id NOT IN (SELECT organiz_organiz_id FROM accreditation);