

DELETE FROM trade;
-- For update Test
INSERT INTO poseidon.trade (trade_id, account, benchmark, book, buy_price, buy_quantity, creation_date, creation_name, deal_name, deal_type, revision_date, revision_name, security, sell_price, sell_quantity, side, source_list_id, status, trade_date, trader, type)
VALUES (10, 'ACCOUNT TO UPDATE!!', null, null, null, 10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'TYPE');
-- For Delete Test
INSERT INTO poseidon.trade (trade_id, account, benchmark, book, buy_price, buy_quantity, creation_date, creation_name, deal_name, deal_type, revision_date, revision_name, security, sell_price, sell_quantity, side, source_list_id, status, trade_date, trader, type)
VALUES (11, 'ACCOUNT TO DELETE!!', null, null, null, 10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'TYPE');
