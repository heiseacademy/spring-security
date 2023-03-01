-- users
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
('admin',	'{bcrypt}$2a$10$Q7acD5ttN9uQJIkCwSQaFeeCtIK4K79FIxacS8sSuu0hcojtkOkeO',	1),
('user',	'{bcrypt}$2a$10$rUHSIRfuPZmqyZz90Rn3suCccHZ8KKATahS2u/pyrTKsfNv32iPMe',	1);

-- authorities
INSERT INTO `authorities` (`username`, `authority`) VALUES
('admin',	'ROLE_ADMIN'),
('admin',	'ROLE_USER'),
('user',	'ROLE_DEMO'),
('user',	'ROLE_USER');
