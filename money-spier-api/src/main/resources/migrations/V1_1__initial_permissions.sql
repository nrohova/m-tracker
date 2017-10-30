INSERT INTO permission
(`name`, `expense_read`, `expense_write`, `income_read`,`income_write`)
VALUES
("expenseAll", 1, 1, 0, 0),
("incomeAll", 0, 0, 1, 1),
("readAll", 1, 0, 1, 0),
("writeAll", 0, 1, 0, 1),
("all", 1, 1, 1, 1);