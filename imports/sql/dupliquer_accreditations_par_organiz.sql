DROP PROCEDURE IF EXISTS dupliquer_accreditations_par_organiz;
DELIMITER $$

CREATE PROCEDURE dupliquer_accreditations_par_organiz(
    IN acc_organiz_name VARCHAR(255),
    IN acc_old_event_id INT,
    IN acc_new_event_id INT
)
BEGIN
    SET @sql = CONCAT(
        'INSERT INTO accreditation (
            accreditation_first_name,
            accreditation_last_name,
            accreditation_birth_day,
            accreditation_cin_id,
            accreditation_occupation,
            accreditation_photo,
            accreditation_photo_content_type,
            accreditation_stat,
            accreditation_creation_date,
            accreditation_update_date,
            accreditation_date_start,
            accreditation_date_end,
            accreditation_activated,
            event_event_id
        ) ',
        'SELECT
            a.accreditation_first_name,
            a.accreditation_last_name,
            a.accreditation_birth_day,
            a.accreditation_cin_id,
            a.accreditation_occupation,
            a.accreditation_photo,
            a.accreditation_photo_content_type,
            a.accreditation_stat,
            NOW(),
            NOW(),
            a.accreditation_date_start,
            a.accreditation_date_end,
            true,
            ', acc_new_event_id, '
        FROM accreditation a
        INNER JOIN organiz o ON a.organiz_organiz_id = o.organiz_id
        WHERE o.organiz_name = ''', acc_organiz_name, ''' ',
        'AND a.event_event_id = ', acc_old_event_id, ' ',
        'AND a.accreditation_photo IS NOT NULL'
    );

    -- Debug: check the final SQL string
    SELECT @sql;

    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END $$

DELIMITER ;


CALL dupliquer_accreditations_par_organiz('OLEVENTS', 1000000, 1000001);
