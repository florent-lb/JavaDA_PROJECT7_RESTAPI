

DELETE FROM curvepoint;
-- For update Test
INSERT INTO poseidon.curvepoint (id, as_of_date, creation_date, curve_id, term, value)
     VALUES (10, '2020-02-18 22:29:45.000000', '2019-02-18 22:29:48.000000', 10, 2.5, 3.5);
-- For Delete Test
     INSERT INTO poseidon.curvepoint (id, as_of_date, creation_date, curve_id, term, value)
     VALUES (11, '2020-02-18 22:29:45.000000', '2019-02-18 22:29:48.000000', 11, 2.5, 3.5);