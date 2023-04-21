INSERT INTO directory (firstName, lastName, age, address, phone, email, username, pword, verified, shown, late, penalized, owe, guests, keepAccount, keepConfirm) 
VALUES ("John", "Doe", 21, "1 Main Street,\nWest Hartford, CT, 06117", "4353234432", "john@tennis.com", "jdoe", "pass", true, false, false, false, 0, 0, true, true);

INSERT INTO directory (firstName, lastName, age, address, phone, email, username, pword, verified, shown, late, penalized, owe, guests, keepAccount, keepConfirm) 
VALUES ("Jane", "Smith", 67, "123 Main Street, \nNew Britain, CT, 06050", "9758495847", "jane@gmail.com", "jsmith", "pass", true, true, true, false, 1000, 0, true, true);

INSERT INTO directory (firstName, lastName, age, address, phone, email, username, pword, verified, shown, late, penalized, owe, guests, keepAccount, keepConfirm) 
VALUES ("Adam", "Fincher", 33, "123 Second Street, \nEast Hartford, CT, 06108", "7598495847", "admin9999@admin.com", "admin", "pass", true, false, false, false, 0, 0, true, true);

INSERT INTO bank (username, bankName, accountNum, ssn, accountType) 
values ("jsmith", "citizens bank", "123456789", "123456789", "Checking")