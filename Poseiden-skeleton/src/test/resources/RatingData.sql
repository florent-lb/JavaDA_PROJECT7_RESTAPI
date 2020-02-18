

DELETE FROM rating;
-- For update Test
INSERT INTO poseidon.rating (id, fitch_rating, moodys_rating, order_number, sandprating) VALUES (10, 'FOOFOO', 'BAD', '5', 'FOO');
-- For Delete Test
INSERT INTO poseidon.rating (id, fitch_rating, moodys_rating, order_number, sandprating) VALUES (11, 'TO DELETE', 'BAD', '25', 'OO');