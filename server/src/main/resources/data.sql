DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS cards;

CREATE TABLE clients (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  firstName VARCHAR(255),
  lastName VARCHAR(255),
);

CREATE TABLE accounts (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  accountNum VARCHAR(25),
  balance int DEFAULT 0,
  isoCode VARCHAR(3),

  client_id  INT NOT NULL,
    foreign key (client_id) references clients(id)
);

CREATE TABLE cards (
  id INT AUTO_INCREMENT  PRIMARY KEY,

  pinCode INT,
  cardNum VARCHAR(16),
  expireDate DATE,
  cvcCode INT,

  account_id  INT NOT NULL,
    foreign key (account_id) references accounts(id)
);

INSERT INTO clients (firstName, lastName) VALUES
  ('NIKOLAY', 'SAVELIEV'),
  ('STEPAN', 'KOROTKOV');

INSERT INTO accounts (accountNum, balance, isoCode, client_id) VALUES
  ('12345678901234567890', 865, 'RUR', 1),
  ('98765432198765432111', 495, 'RUR', 1),
  ('65463186463546445466', 564, 'RUR', 2);

INSERT INTO cards (pinCode, cardNum, expireDate, cvcCode, account_id) VALUES
  (1234, '1234567812345678', '2023-01-01', 123, 1),
  (1478, '2222222222222222', '2020-04-02', 222, 2),
  (1111, '4444444444444444', '2023-02-03', 333, 3);

