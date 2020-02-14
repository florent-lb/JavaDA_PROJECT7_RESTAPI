

DELETE FROM bidlist;
INSERT INTO poseidon.bidlist (bid_list_id, account, ask, ask_quantity, benchmark, bid, bid_list_date, bid_quantity, book, commentary, creation_date, creation_name, deal_name, deal_type, revision_date, revision_name, security, side, source_list_id, status, trader, type)
                      VALUES (10, 'ACCOUNT', null, null, null, null, null, 20.5, null, null, null, null, null, null, null, null, null, null, null, null, null, 'TYPE');
INSERT INTO poseidon.bidlist (bid_list_id, account, ask, ask_quantity, benchmark, bid, bid_list_date, bid_quantity, book, commentary, creation_date, creation_name, deal_name, deal_type, revision_date, revision_name, security, side, source_list_id, status, trader, type)
                      VALUES (11, 'ACCOUNT', null, null, null, null, null, 20.5, null, null, null, null, null, null, null, null, null, null, null, null, null, 'TYPE');
