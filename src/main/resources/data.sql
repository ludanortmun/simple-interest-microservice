CREATE TABLE IF NOT EXISTS credits (
    id VARCHAR(36) NOT NULL,
    amount DECIMAL NOT NULL,
    terms INT NOT NULL,
    rate DECIMAL NOT NULL,
    creation_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS payments (
    parent_id VARCHAR(36) NOT NULL,
    amount DECIMAL NOT NULL,
    due_date DATE NOT NULL
);