SELECT accreditation_first_name, accreditation_last_name, accreditation_cin_id, accreditation_type.accreditation_type_value, fonction.fonction_name AS 'occupation',     CONCAT(
        accreditation_first_name, '_', accreditation_last_name,
        CASE accreditation_photo_content_type
            WHEN 'image/jpeg' THEN '.jpg'
            WHEN 'image/png'  THEN '.png'
            WHEN 'image/gif'  THEN '.gif'
            ELSE ''
        END
    ) AS file_name
    FROM accreditation INNER JOIN organiz ON organiz.organiz_id = accreditation.organiz_organiz_id INNER JOIN accreditation_type ON accreditation.accreditation_type_accreditation_type_id = accreditation_type.accreditation_type_id INNER JOIN fonction ON fonction.fonction_id = accreditation.fonction_fonction_id
    WHERE accreditation.accreditation_photo IS NOT NULL AND accreditation.event_event_id = 1000000 AND organiz.organiz_name = 'MAROC TELECOM'