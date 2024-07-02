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
    num_created INT,
    num_taken INT,
    had_highest_score boolean,
    practiced boolean
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
    quit_name TEXT,
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

