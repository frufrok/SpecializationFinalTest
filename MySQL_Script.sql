DROP DATABASE IF EXISTS HumanFriends;
CREATE DATABASE HumanFriends;
USE HumanFriends;

CREATE TABLE Animal(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50),
    Kind VARCHAR(50),
    BirthDate DATE,
    OwnerID INTEGER
);

CREATE TABLE Pet(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	AnimalID INT,
    FOREIGN KEY
	(AnimalID)
	REFERENCES Animal (ID)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);
    
CREATE TABLE Toy(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    PetID INT,
    Name VARCHAR(50),
    FOREIGN KEY
    (PetID)
    REFERENCES Pet (ID)
	ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE PackAnimal(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    AnimalID INT,
	Stamina double,
    FOREIGN KEY
	(AnimalID)
	REFERENCES Animal (ID)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE Task(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50),
    Description VARCHAR(250)
);

CREATE TABLE TaskMap(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    TaskID INT,
    PackID INT,
	FOREIGN KEY
    (TaskID)
    REFERENCES Task (ID)
	ON DELETE SET NULL
    ON UPDATE CASCADE,
	FOREIGN KEY
    (PackID)
    REFERENCES PackAnimal (ID)
	ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE Command(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50)
);

CREATE TABLE CommandMap(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    AnimalID INT,
    CommandID INT,
	FOREIGN KEY
    (CommandID)
    REFERENCES Command (ID)
	ON DELETE CASCADE
    ON UPDATE CASCADE,
	FOREIGN KEY
    (AnimalID)
    REFERENCES Animal (ID)
	ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE VIEW BabyAnimal AS 
	SELECT * FROM
		(SELECT ID, Name, Kind, BirthDate, OwnerID,
		TIMESTAMPDIFF(MONTH, BirthDate, CURDATE()) AS AgeMonths
		FROM Animal) as AnimalAge
		WHERE AgeMonths >= 12 AND AgeMonths <= 36;

CREATE VIEW AnimalCommands AS
	SELECT AnimalID, GROUP_CONCAT(Name SEPARATOR ', ') as Commands
		FROM 
			(CommandMap 
			LEFT JOIN 
			Command 
			ON CommandID = Command.ID)
		GROUP BY AnimalID;

CREATE VIEW AnimalToys AS    
	SELECT AnimalID, GROUP_CONCAT(Name SEPARATOR ', ') as Toys
		FROM 
			(Toy LEFT JOIN Pet ON PetID = Pet.ID)
		GROUP BY PetID;
        
CREATE VIEW AnimalTasks AS
	SELECT AnimalID, Tasks FROM
		((SELECT PackID, GROUP_CONCAT(Name SEPARATOR ', ') as Tasks  FROM
		(Taskmap LEFT JOIN Task ON TaskID = Task.ID)
		GROUP BY PackID) as PackTasks
		LEFT JOIN PackAnimal on PackID = PackAnimal.ID);

CREATE VIEW AnimalMain As
	SELECT * FROM
	(SELECT ID, Name, Kind, BirthDate, AgeMonths, OwnerID, IsPet, PetID, IF(PackID IS NOT NULL, TRUE, FALSE) as IsPack, PackID FROM
		(SELECT ID, Name, Kind, BirthDate, AgeMonths, OwnerID, IF(PetID IS NOT NULL, TRUE, FALSE) as IsPet, PetID FROM
			(
				(SELECT ID, Name, Kind, BirthDate, OwnerID,
				TIMESTAMPDIFF(MONTH, BirthDate, CURDATE()) AS AgeMonths
				FROM Animal) as Animal
			LEFT JOIN 
				(SELECT ID as PetID, AnimalID
				FROM Pet) as Pet
			ON Animal.ID = Pet.AnimalID)) as Animal
		LEFT JOIN 
			(SELECT ID as PackID, AnimalID
			FROM PackAnimal) AS PackAnimal
		ON Animal.ID = PackAnimal.AnimalID) as Animal;   

CREATE VIEW AnimalLists AS
	SELECT ID, Tasks, Toys, Commands FROM
		((SELECT ID, Tasks, Toys FROM
			((SELECT ID, Tasks FROM
				((SELECT ID FROM Animal) AS Animal
				LEFT JOIN AnimalTasks on ID = AnimalID)) AS Animal
			LEFT JOIN AnimalToys on ID = AnimalID)) As Animal
		LEFT JOIN AnimalCommands on ID = AnimalID);

CREATE VIEW AnimalSummary AS
	SELECT ID, Name, Kind, BirthDate, AgeMonths, OwnerID, IsPet, PetID, Toys, IsPack, PackID, Tasks, Commands FROM
		(AnimalMain LEFT JOIN 
			(SELECT ID as AnimalID, Tasks, Toys, Commands FROM AnimalLists) As AnimalLists
		on ID = AnimalID);

INSERT INTO Animal
	(Name, Kind, BirthDate, OwnerID)
    VALUES
    ('Fido', 'Dog', '2020-01-01', 5),
	('Whiskers', 'Cat', '2019-05-15', 5),
	('Hammy', 'Hamster', '2021-03-10', 5),
	('Buddy', 'Dog', '2018-12-10', 2),
	('Smudge', 'Cat', '2020-02-20', 3),
	('Peanut', 'Hamster', '2021-08-01', 1),
	('Bella', 'Dog', '2019-11-11', 2),
	('Oliver', 'Cat', '2020-06-30', 4), 
	('Thunder', 'Horse', '2015-07-21', 2),
	('Sandy', 'Camel', '2016-11-03', 1),
	('Eeyore', 'Donkey', '2017-09-18', 2),
	('Storm', 'Horse', '2014-05-05', 1),
	('Dune', 'Camel', '2018-12-12', 2),
	('Burro', 'Donkey', '2019-01-23', 4),
	('Blaze', 'Horse', '2016-02-29', 3),
	('Sahara', 'Camel', '2015-08-14', 2);

INSERT INTO Pet
	(AnimalID)    
    SELECT ID FROM Animal WHERE ID <= 8;

INSERT INTO PackAnimal
	(AnimalID)
    SELECT ID FROM Animal WHERE ID > 8;
    
UPDATE PackAnimal
		SET Stamina = round(RAND(), 3);
    
INSERT INTO Command
	(Name)
    VALUES
    ('Sit'),
	('Roll'),
	('Meow'),
	('Trot'),
	('Walk'),
	('Stay'),
	('Pounce'),
	('Hide'),
	('Paw'),
	('Spin'),
	('Scratch'),
	('Canter'),
	('Carry Load'),
	('Bray'),
	('Jump'),
	('Run'),
	('Fetch'),
	('Bark'),
	('Gallop'),
	('Kick');

INSERT INTO CommandMap(AnimalID, CommandID)
SELECT * FROM
	(SELECT ID AS AnimalID FROM Animal WHERE Kind = 'Dog') as Animal
	CROSS JOIN
	(SELECT ID AS CommandID FROM Command WHERE Name in ('Sit', 'Stay','Fetch','Roll')) as Command;
    
INSERT INTO CommandMap(AnimalID, CommandID)
SELECT * FROM
	(SELECT ID AS AnimalID FROM Animal WHERE Kind = 'Cat') as Animal
	CROSS JOIN
	(SELECT ID AS CommandID FROM Command WHERE Name in ('Sit', 'Pounce','Scratch','Jump')) as Command;
    
INSERT INTO CommandMap(AnimalID, CommandID)
SELECT * FROM
	(SELECT ID AS AnimalID FROM Animal WHERE Kind = 'Hamster') as Animal
	CROSS JOIN
	(SELECT ID AS CommandID FROM Command WHERE Name in ('Roll', 'Spin','Hide')) as Command;
    
INSERT INTO CommandMap(AnimalID, CommandID)
SELECT * FROM
	(SELECT ID AS AnimalID FROM Animal WHERE Kind = 'Horse') as Animal
	CROSS JOIN
	(SELECT ID AS CommandID FROM Command WHERE Name in ('Trot', 'Canter','Gallop', 'Jump')) as Command;
    
INSERT INTO CommandMap(AnimalID, CommandID)
SELECT * FROM
	(SELECT ID AS AnimalID FROM Animal WHERE Kind = 'Camel') as Animal
	CROSS JOIN
	(SELECT ID AS CommandID FROM Command WHERE Name in ('Walk', 'Carry Load', 'Sit')) as Command;
    
INSERT INTO CommandMap(AnimalID, CommandID)
SELECT * FROM
	(SELECT ID AS AnimalID FROM Animal WHERE Kind = 'Donkey') as Animal
	CROSS JOIN
	(SELECT ID AS CommandID FROM Command WHERE Name in ('Walk', 'Bray','Kick')) as Command;

INSERT INTO Toy
	(PetID, Name)
	VALUES
    (1, 'Bone'),
    (1, 'Frisbee'),
    (2, 'Mouse Toy'),
    (3, 'Wheel');

INSERT INTO Task
	(Name, Description)
    VALUES
	('Пашня', 'Вспахать поле №9'),
    ('Верховая езда', 'Тренировка по верховой езде с дочкой хозяина'),
    ('Поход', 'Отправиться в поход с туристами');
    
INSERT INTO TaskMap
	(TaskID, PackID)
    VALUES
    (1, 1),
    (2, 1),
    (3, 3);

DELETE FROM Animal
	WHERE Kind = 'Camel';

# Редаем случайное сокращение изученных животными команд для того,
# чтобы животные одного и того же вида отличались в итоговой выборке.
DELETE FROM CommandMap
	WHERE ID IN
		(Select ID FROM
			(SELECT ID, RAND() AS Rnd FROM CommandMap) AS RandomAssignment
        WHERE Rnd < 0.3);
        
SELECT * FROM AnimalSummary;