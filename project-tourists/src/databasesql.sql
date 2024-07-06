use tourists;

DROP TABLE IF EXISTS question_table;
DROP TABLE IF EXISTS review_table;
DROP TABLE IF EXISTS friend_table;
DROP TABLE IF EXISTS ban_table;
DROP TABLE IF EXISTS achievement_table;
DROP TABLE IF EXISTS mail_table;
DROP TABLE IF EXISTS post_table;
DROP TABLE IF EXISTS quiz_table;
DROP TABLE IF EXISTS user_table;
DROP TABLE IF EXISTS login_table;

CREATE TABLE login_table (
         user_id INT AUTO_INCREMENT PRIMARY KEY,
         username VARCHAR(50) NOT NULL UNIQUE,
         password VARCHAR(255) NOT NULL
);

CREATE TABLE user_table (
        user_id INT,
        username VARCHAR(50) NOT NULL,
        is_admin BOOLEAN NOT NULL DEFAULT FALSE,
        practiced BOOLEAN DEFAULT FALSE,
        created_Quizzes INT DEFAULT 0,
        scored_Highest BOOLEAN DEFAULT FALSE,
        profilePhoto VARCHAR(500) DEFAULT 'https://i.pinimg.com/736x/64/5f/d9/645fd98adba55582c6851985779fcb0e.jpg',
        PRIMARY KEY (user_id),
        FOREIGN KEY (user_id) REFERENCES login_table(user_id) ON DELETE CASCADE
);

CREATE TABLE ban_table (
       ban_id INT AUTO_INCREMENT PRIMARY KEY,
       user_id INT,
       expire_date DATE,
       FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE
);

CREATE TABLE achievement_table (
       achievement_id INT AUTO_INCREMENT PRIMARY KEY,
       achievement VARCHAR(255),
       num_created INT DEFAULT 0,
       num_taken INT DEFAULT 0,
       had_highest_score boolean DEFAULT false,
       practiced boolean DEFAULT false
);

CREATE TABLE friend_table (
      friend_id INT AUTO_INCREMENT PRIMARY KEY,
      user_id_1 INT not null,
      user_id_2 INT not null,
      FOREIGN KEY (user_id_1) REFERENCES user_table(user_id) ON DELETE CASCADE,
      FOREIGN KEY (user_id_2) REFERENCES user_table(user_id) ON DELETE CASCADE
);

CREATE TABLE mail_table (
        mail_id INT AUTO_INCREMENT PRIMARY KEY,
        from_id INT not null,
        to_id INT not null,
        type VARCHAR(50),
        message TEXT,
        date DATETIME,
        FOREIGN KEY (from_id) REFERENCES user_table(user_id) ON DELETE CASCADE,
        FOREIGN KEY (to_id) REFERENCES user_table(user_id) ON DELETE CASCADE
);

CREATE TABLE post_table (
        post_id INT AUTO_INCREMENT PRIMARY KEY,
        post_text TEXT,
        user_id INT,
        date DATETIME,
        FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE
);

CREATE TABLE quiz_table(
        quiz_id INT AUTO_INCREMENT PRIMARY KEY,
        quiz_name TEXT,
        quiz_description TEXT,
        quiz_tag TEXT,
        difficulty TEXT,
        creator_id INT not null,
        date_created DATE,
        multiple_pages BOOLEAN DEFAULT FALSE,
        practice_mode BOOLEAN DEFAULT FALSE,
        gradable BOOLEAN DEFAULT FALSE,
        immediate_correction BOOLEAN DEFAULT FALSE,
        random_questions BOOLEAN DEFAULT FALSE,
        timed BOOLEAN DEFAULT FALSE,
        duration_time INT default -1,
        FOREIGN KEY (creator_id) REFERENCES user_table(user_id) ON DELETE CASCADE
);

CREATE TABLE review_table (
          review_id INT AUTO_INCREMENT PRIMARY KEY,
          user_id INT NOT NULL,
          quiz_id INT not null,
          quiz_name TEXT,
          score INT default 0,
          date date not null,
          rating int default 0,
          review_text TEXT,
          FOREIGN KEY (quiz_id) REFERENCES quiz_table(quiz_id) ON DELETE CASCADE,
          FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE
);

CREATE TABLE question_table (
        question_id INT AUTO_INCREMENT PRIMARY KEY,
        question TEXT not null,
        possible_answers TEXT not null,
        answer TEXT not null,
        quiz_id INT not null,
        question_type INT not null,
        imageURL TEXT not null,
        FOREIGN KEY (quiz_id) REFERENCES quiz_table(quiz_id) ON DELETE CASCADE
);


-- show tables;


-- # INSERT INTO login_table (username, password) VALUES ("nick", "rume123");
-- # INSERT INTO login_table (username, password) VALUES ("john", "rume123");
-- # INSERT INTO login_table (username, password) VALUES ("bonk", "rume123");
-- # INSERT INTO user_table (user_id, username, is_admin) VALUES (1, 'nick', FALSE);
-- # INSERT INTO user_table (user_id, username, is_admin) VALUES (2, 'john', FALSE);
-- # INSERT INTO user_table (user_id, username, is_admin) VALUES (3, 'bonk', FALSE);

-- INSERT INTO quiz_table (quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES
-- ('Basic Math Quiz', 'math;3grade', 'Easy', 1, NOW(), FALSE, TRUE, TRUE);

-- INSERT INTO quiz_table (quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES
-- ('english Quiz', 'english;3grade', 'Medium', 2, NOW(), FALSE, TRUE, TRUE);

-- INSERT INTO quiz_table (quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES
-- ('fizika qvizi', 'math;pop;english', 'Easy', 1, NOW(), FALSE, TRUE, TRUE);

-- INSERT INTO quiz_table (quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES
-- ('rame Quiz', 'english;3grade', 'Medium', 3, NOW(), FALSE, TRUE, TRUE);

-- INSERT INTO quiz_table (quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES
-- ('magari Quiz', 'super;5grade', 'Hard', 3, '2024-08-01', FALSE, TRUE, TRUE);

-- INSERT INTO quiz_table (quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES
-- ('axali Quiz', 'pop;fast', 'Easy', 3, '2025-02-01', FALSE, TRUE, TRUE);


-- INSERT INTO review_table (user_id, quiz_id, score, date, rating, review_text) VALUES
-- (1, 3, 70, '2024-02-03', 4, 'norm quiz, really enjoyed it!');

-- INSERT INTO review_table (user_id, quiz_id, score, date, rating, review_text) VALUES
-- 						 (1, 2, 80, '2024-02-03', 4, 'norm quiz, really enjoyed it!');

-- INSERT INTO review_table (user_id, quiz_id, score, date, rating, review_text) VALUES
-- 						 (3, 1, 95, '2024-03-04', 3, 'good!');



-- bendoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
INSERT INTO login_table VALUES (1,'bendo','1234');
INSERT INTO login_table VALUES (2,'Quaggy','1234');
INSERT INTO login_table VALUES (3,'Joe','1234');
INSERT INTO achievement_table(achievement_id, achievement, num_created) VALUES (1,'supreme',10);
INSERT INTO user_table VALUES (1,'bendo',false,false,30,true,'https://i.pinimg.com/736x/64/5f/d9/645fd98adba55582c6851985779fcb0e.jpg');
INSERT INTO user_table VALUES (2,'Quaggy',false,false,20,true,'https://www.watchmojo.com/uploads/thumbs720/Fi-T-Top10-Family-Guy-Characters_I2B8Z1-720p30-1.jpg');
INSERT INTO user_table VALUES (3,'Joe',false,false,14,true,'https://media.entertainmentearth.com/assets/images/fe9f5fc5d21e4c338652f08b5f86b0caxl.jpg');
Insert into achievement_table(achievement_id,achievement,num_created) Values (2,'wowzers',20);
INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (1,'ez','history','easy',1);
INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (2,'mid','english;history','medium',1);
INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (3,'hard','math','hard',1);

INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,1,'ez',10,NOW(),'');
INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,2,'mid',8,NOW(),'');
INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,3,'hard',5,NOW(),'');

INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (2,1,'ez',9,NOW(),'');
INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (2,2,'mid',7,NOW(),'');
INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (2,3,'hard',4,NOW(),'');

INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (3,1,'ez',8,NOW(),'');
INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (3,2,'mid',7,NOW(),'');
INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (3,3,'hard',5,NOW(),'');
INSERT INTO friend_table(user_id_1, user_id_2) VALUES (1,2);
INSERT INTO friend_table(user_id_1, user_id_2) VALUES (1,3);
INSERT INTO friend_table(user_id_1, user_id_2) VALUES (2,3);
-- bendooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo


-- select * from quiz_table;
-- select * from review_table;
-- select * from user_table;
-- select * from login_table;


-- INSERT INTO login_table VALUES (1,'bendo','1234');
-- INSERT INTO login_table VALUES (2,'Quaggy','1234');
-- INSERT INTO login_table VALUES (3,'Joe','1234');
-- INSERT INTO achievement_table(achievement_id, achievement, num_created) VALUES (1,'supreme',10);
-- INSERT INTO user_table VALUES (1,'bendo',false,false,30,true,'https://i.pinimg.com/736x/64/5f/d9/645fd98adba55582c6851985779fcb0e.jpg');
-- INSERT INTO user_table VALUES (2,'Quaggy',false,false,20,true,'https://www.watchmojo.com/uploads/thumbs720/Fi-T-Top10-Family-Guy-Characters_I2B8Z1-720p30-1.jpg');
-- INSERT INTO user_table VALUES (3,'Joe',false,false,14,true,'https://media.entertainmentearth.com/assets/images/fe9f5fc5d21e4c338652f08b5f86b0caxl.jpg');
-- Insert into achievement_table(achievement_id,achievement,num_created) Values (2,'wowzers',20);
-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (1,'ez','history','easy',1);
-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (2,'mid','english;history','medium',1);
-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (3,'hard','math','hard',1);

-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,1,'ez',10,NOW(),'');
-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,2,'mid',8,NOW(),'');
-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,3,'hard',5,NOW(),'');

-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (2,1,'ez',9,NOW(),'');
-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (2,2,'mid',7,NOW(),'');
-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (2,3,'hard',4,NOW(),'');

-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (3,1,'ez',8,NOW(),'');
-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (3,2,'mid',7,NOW(),'');
-- INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (3,3,'hard',5,NOW(),'');
-- INSERT INTO friend_table(user_id_1, user_id_2) VALUES (1,2);
-- INSERT INTO friend_table(user_id_1, user_id_2) VALUES (1,3);
-- INSERT INTO friend_table(user_id_1, user_id_2) VALUES (2,3);


-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (1,'question1','a;b;c;d','a', 3, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (2,'question2','a;b;c;d','b', 3, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (3,'question3','a;b;c;d','c', 3, 1);


-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id, multiple_pages, practice_mode) VALUES (4,'hard','math','hard',1,false,true);

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (4,'question1','a;b;c;d','a', 4, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (5,'question2','a;b;c;d','b', 4, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (6,'question3','a;b;c;d','c', 4, 1);

-- SELECT * FROM question_table;


-- INSERT INTO user_table(user_id, username) VALUES (10, 'saxelii');
-- INSERT INTO user_table(user_id, username) VALUES (15, 'saxeli');


-- INSERT INTO login_table(user_id, username, password) VALUES (15, 'saxeli', 'paroli');
-- INSERT INTO login_table(user_id, username, password) VALUES (10, 'saxelii', 'parolii');

-- INSERT INTO friend_table(friend_id, user_id_1, user_id_2) VALUES (1, 15, 10);


-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id, multiple_pages, practice_mode) VALUES (10,'hard','math','hard',1,false,true);

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (9,'question2','a;b;c;d','a;b', 10, 2);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (10,'question3','a;b;c;d','c;d', 10, 2);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (11,'question1','a;b;c;d','a', 10, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (12,'question2','a;b;c;d','b', 10, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (13,'question3','a;b;c;d','c', 10, 1);


-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id, multiple_pages, practice_mode) VALUES (11,'hard','math','hard',1,false,true);

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (14,'question2','a;b;c;d','b', 11, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (15,'question3','a;b;c;d','c', 11, 1);


-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id, multiple_pages, practice_mode) VALUES (12,'hard','math','hard',1,false,true);


-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (18,'question2','a;b;c;d','b', 12, 2);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (19,'question3','a;b;c;d','c', 12, 2);

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (20,'question2', '','mushaobs', 12, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (21,'question3', '','fuck', 12, 1);


-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (22,'question4', '','mushaobs', 12, 1);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (23,'question5', '','fuck', 12, 1);

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (26,'question6 _____', '','seven', 12, 4);
-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (27,'question7 ____', '','fuck', 12, 4);

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type) VALUES (28,'question8', 'https://miro.medium.com/v2/resize:fit:720/format:webp/1*Qr2z3ZS7--o8xO6jJi77Ig.jpeg','fuck', 12, 3);

-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id, multiple_pages, practice_mode) VALUES (13,'hard','math','hard',15,true,true);


-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type, imageURL) VALUES (30,'question2','a;b;c;d','b', 13, 2, '');


-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type, imageURL) VALUES (31,'question3', '','fuck', 13, 1, '');

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type, imageURL) VALUES (32,'question7 ____', '','fuck', 13, 4, '');

-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type, imageURL) VALUES (33,'question8', '','fuck', 13, 3, 'https://miro.medium.com/v2/resize:fit:720/format:webp/1*Qr2z3ZS7--o8xO6jJi77Ig.jpeg');


-- INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id, multiple_pages, practice_mode) VALUES (14,'hard','math','hard',15,true,false);

-- SELECT * FROM mail_table;



-- INSERT INTO review_table(review_id, user_id, quiz_id, quiz_name, score, date, rating, review_text) VALUES (10, 10, 13, 'math', 3, sysdate(), 4, 'wava ra');

-- SELECT * FROM user_table WHERE user_id = 15;


-- INSERT INTO question_table(question_id, question, possible_answers, answer, quiz_id, question_type, imageURL) VALUES (34,'question10', '','fuck', 13, 3, 'https://miro.medium.com/v2/resize:fit:720/format:webp/1*Qr2z3ZS7--o8xO6jJi77Ig.jpeg');
-- # Insertions for lsurm 22
-- #INSERT into login_table(user_id, username,password) VALUES(1,"luka", "luka");

-- #INSERT into user_table(user_id, username, is_admin, practiced, created_Quizzes, scored_Highest, profilePhoto)
--  #           VALUES(1, "luka", false, false, false, false, "photo");

-- # Insert into login_table (user_id,username,password) values(1, "luka", "luka");
-- # Insert into user_table (user_id, username) values(1,"luka");
-- select * from quiz_table;
-- select * from review_table;

-- * from mail_table;
-- select * from friend_table;
-- select * from user_table;
-- select * from login_table;
