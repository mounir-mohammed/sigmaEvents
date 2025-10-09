SELECT DISTINCT *
FROM sigmaevents.fonction t1
WHERE EXISTS (
              SELECT *
              FROM sigmaevents.fonction t2
              WHERE t1.fonction_id <> t2.fonction_id
              AND   t1.fonction_name = t2.fonction_name
              AND   t1.event_event_id = t2.event_event_id
              AND   t1.event_event_id = 3 )