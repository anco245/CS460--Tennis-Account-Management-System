-- INSERT INTO waiting (firstName, lastName, age, address, phone, email, username, pword, shown, owe)VALUES ("j", "yep", 32, "378t", "1234567898", "7586@gmail.com", "jzee", "pass", true, 0);

-- For making reservations
-- UPDATE court1 SET username = "jsmith", occupied = 1 where dayAndTime = "2023-04-25 09:00:00"
-- UPDATE court8 SET username = "jsmith", occupied = 1 where dayAndTime = "2023-04-28 13:00:00"

SELECT * from directory
-- to show reservations in court tables
-- select * from court1 where username = "jsmith";
-- select * from court8 where username = "jsmith";

-- to show waitlist when 
-- SELECT * FROM waiting LIMIT 0,1
-- select * from directory

-- select * from court6 where username = "jcifone"

-- select * from directory