

DELETE FROM rulename;
-- For update Test
INSERT INTO poseidon.rulename (id, description, json, name, sql_part, sql_str, template) VALUES (10, 'RULE DESCRIPTION TO UPDATE', '{"field":"My JSON"}', 'NAME RULE', '25', 'SELECT * FROM ruleName WHERE id= ?', 'RULE TEMPLATE');
-- For Delete Test
INSERT INTO poseidon.rulename (id, description, json, name, sql_part, sql_str, template) VALUES (11, 'RULE DESCRIPTION TO DELETE', '{"field":"My JSON"}', 'NAME RULE', '25', 'SELECT * FROM ruleName WHERE id= ?', 'RULE TEMPLATE');