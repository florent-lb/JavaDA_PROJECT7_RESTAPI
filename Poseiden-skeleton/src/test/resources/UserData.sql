

DELETE FROM users;
-- For update Test
INSERT INTO poseidon.users (id, fullname, password, role, username)
 VALUES (10, 'to_update', '$2a$10$bggMEQNNsFldT062ZdF77uKw.NmETYGUW/1pcuV7DtO/5B7xHPVUe', 'USER', 'to_update');
-- For Delete Test
INSERT INTO poseidon.users (id, fullname, password, role, username)
VALUES (11, 'to_delete', '$2a$10$bggMEQNNsFldT062ZdF77uKw.NmETYGUW/1pcuV7DtO/5B7xHPVUe', 'ADMIN', 'to_delete');
