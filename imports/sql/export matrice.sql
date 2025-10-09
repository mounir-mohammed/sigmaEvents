SELECT 
    c.category_name,
    f.fonction_name,
    GROUP_CONCAT(a.area_abreviation ORDER BY a.area_abreviation SEPARATOR ', ') AS zones
FROM category c
INNER JOIN fonction f 
    ON f.category_category_id = c.category_id
INNER JOIN rel_fonction__area rfa 
    ON f.fonction_id = rfa.fonction_fonction_id
INNER JOIN area a 
    ON a.area_id = rfa.area_area_id
WHERE c.event_event_id = 1000000
GROUP BY c.category_name, f.fonction_name
ORDER BY c.category_name, f.fonction_name
INTO OUTFILE 'C:\Users\devmo\Desktop\Matrice_de_zonning.csv'
FIELDS TERMINATED BY ';'
ENCLOSED BY '"'
LINES TERMINATED BY '\n';


