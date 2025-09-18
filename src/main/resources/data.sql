INSERT INTO users (id, username, email, password)
VALUES (1, 'Ana', 'ana@gmail.com', 'demo123');

INSERT INTO medications (id, name, dose, start_date, end_date, user_id)
VALUES
(1, 'Ibuprofeno', '600mg', '2025-09-18', '2025-09-20', 1),
(2, 'Paracetamol', '500mg', '2025-09-18', '2025-09-19', 1);

INSERT INTO intakes (intake_date, slot, status, medication_id)
VALUES
('2025-09-18', 'BREAKFAST', 'PENDING', 1),
('2025-09-18', 'LUNCH', 'PENDING', 1),
('2025-09-18', 'DINNER', 'PENDING', 1),
('2025-09-19', 'BREAKFAST', 'PENDING', 1),
('2025-09-19', 'LUNCH', 'PENDING', 1),
('2025-09-19', 'DINNER', 'PENDING', 1),
('2025-09-20', 'BREAKFAST', 'PENDING', 1),
('2025-09-20', 'LUNCH', 'PENDING', 1),
('2025-09-20', 'DINNER', 'PENDING', 1);

INSERT INTO intakes (intake_date, slot, status, medication_id)
VALUES
('2025-09-18', 'BREAKFAST', 'PENDING', 2),
('2025-09-18', 'DINNER', 'PENDING', 2),
('2025-09-19', 'BREAKFAST', 'PENDING', 2),
('2025-09-19', 'DINNER', 'PENDING', 2);