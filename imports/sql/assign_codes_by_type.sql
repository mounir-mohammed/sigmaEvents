DELIMITER $$

CREATE PROCEDURE assign_codes_by_type(IN p_event_id BIGINT)
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_accreditation_id BIGINT;
    DECLARE v_type_id BIGINT;
    DECLARE v_type_value VARCHAR(50);
    DECLARE v_code_id BIGINT;

    -- curseur : toutes les accréditations sans code pour un event
    DECLARE cur CURSOR FOR
        SELECT a.event_event_id, a.accreditation_type_accreditation_type_id
        FROM accreditation a
        WHERE a.code_code_id IS NULL
          AND a.event_event_id = p_event_id;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO v_accreditation_id, v_type_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Récupérer la valeur du type
        SELECT accreditation_type_value 
        INTO v_type_value
        FROM accreditation_type
        WHERE accreditation_type_id = v_type_id
          AND event_event_id = p_event_id;

        -- Récupérer un code libre correspondant à ce type
        SELECT code_id
        INTO v_code_id
        FROM code
        WHERE code_used = 0
          AND code_entity_value = v_type_value
          AND event_event_id = p_event_id
        LIMIT 1;

        -- Si un code existe, assigner
        IF v_code_id IS NOT NULL THEN
            UPDATE accreditation 
            SET code_code_id = v_code_id
            WHERE event_event_id = p_event_id
              AND accreditation_type_accreditation_type_id = v_type_id
              AND code_code_id IS NULL
            LIMIT 1;

            UPDATE code
            SET code_used = 1
            WHERE code_id = v_code_id;
        END IF;

    END LOOP;

    CLOSE cur;
END$$

DELIMITER ;


CALL assign_codes_by_type(1000001);