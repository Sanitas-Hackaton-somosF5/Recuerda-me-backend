INSERT INTO users (id, username, email, password)
VALUES (1, 'Ana', 'ana@gmail.com', 'demo123');

INSERT INTO medications (name, description, dose, start_date, end_date)
VALUES
('Ibuprofeno', 'Tomar después de comer', '200 mg', '2025-09-18', '2025-09-25'),
('Paracetamol', 'Aliviar dolor y fiebre', '500 mg', '2025-09-18', '2025-09-25'),
('Amoxicilina', 'Antibiótico de amplio espectro', '875 mg', '2025-09-18', '2025-09-25'),
('Prasugrel', 'Inhibe la formación plaquetaria', '10 mg', '2025-09-18', '2025-09-25');

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