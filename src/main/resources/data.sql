DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  userId INT AUTO_INCREMENT  PRIMARY KEY,
  userName VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  contact VARCHAR(250),
  password VARCHAR(250)
);

INSERT INTO USER (userName, email, contact,password) VALUES
  ('sangram', 'san@gmail.com', '+91630019','san@1995'),
  ('ravi', 'ravi@gmail.com', '+911242','ravi123'),
  ('sham', 'sham@gmail.com', '+911245','123456');
