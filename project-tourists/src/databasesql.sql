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
    FOREIGN KEY (user_id) REFERENCES login_table(user_id)
);

CREATE TABLE ban_table (
    ban_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    expire_date DATE,
    FOREIGN KEY (user_id) REFERENCES user_table(user_id)
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
    FOREIGN KEY (user_id_1) REFERENCES user_table(user_id),
    FOREIGN KEY (user_id_2) REFERENCES user_table(user_id)
);

CREATE TABLE mail_table (
    mail_id INT AUTO_INCREMENT PRIMARY KEY,
    from_id INT not null,
    to_id INT not null,
    type VARCHAR(50),
    message TEXT,
    date DATETIME,
    FOREIGN KEY (from_id) REFERENCES user_table(user_id),
    FOREIGN KEY (to_id) REFERENCES user_table(user_id)
);

CREATE TABLE post_table (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    post_text TEXT,
    user_id INT,
    date DATETIME,
    FOREIGN KEY (user_id) REFERENCES user_table(user_id)
);

CREATE TABLE quiz_table (
    quiz_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_name TEXT,
    quiz_tag TEXT,
    difficulty TEXT,
    creator_id INT not null,
    multiple_pages BOOLEAN DEFAULT FALSE,
    practice_mode BOOLEAN DEFAULT FALSE,
    gradable BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (creator_id) REFERENCES user_table(user_id)
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
    FOREIGN KEY (quiz_id) REFERENCES quiz_table(quiz_id),
    FOREIGN KEY (user_id) REFERENCES user_table(user_id)
);

CREATE TABLE question_table (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT not null,
    possible_answers TEXT not null,
    answer TEXT not null,
    quiz_id INT not null,
    question_type INT not null,
    FOREIGN KEY (quiz_id) REFERENCES quiz_table(quiz_id)
);

-- show tables;

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

