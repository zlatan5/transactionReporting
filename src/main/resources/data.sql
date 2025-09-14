-- Initialize operation types
INSERT INTO operation_types (operation_type_id, description) VALUES (1, 'CASH PURCHASE');
INSERT INTO operation_types (operation_type_id, description) VALUES (2, 'INSTALLMENT PURCHASE');
INSERT INTO operation_types (operation_type_id, description) VALUES (3, 'WITHDRAWAL');
INSERT INTO operation_types (operation_type_id, description) VALUES (4, 'PAYMENT');

-- Sample data for testing (optional)
INSERT INTO accounts (document_number) VALUES ('12345678900');
INSERT INTO transactions (account_id, operation_type_id, amount, event_date) VALUES (1, 1, -50.0, '2020-01-01T10:32:07.719922');
INSERT INTO transactions (account_id, operation_type_id, amount, event_date) VALUES (1, 1, -23.5, '2020-01-01T10:48:12.213587');
INSERT INTO transactions (account_id, operation_type_id, amount, event_date) VALUES (1, 1, -18.7, '2020-01-02T19:01:23.145854');
INSERT INTO transactions (account_id, operation_type_id, amount, event_date) VALUES (1, 4, 60.0, '2020-01-05T09:34:18.589322');
