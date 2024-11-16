CREATE TABLE IF NOT EXISTS grades (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      student_name TEXT NOT NULL,
                                      course_name TEXT NOT NULL,
                                      exam_name TEXT NOT NULL,
                                      score REAL NOT NULL,
                                      full_score REAL NOT NULL,
                                      time_spent REAL NOT NULL
);
CREATE TABLE IF NOT EXISTS managers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
                                       course_code TEXT PRIMARY KEY NOT NULL,
                                       name TEXT NOT NULL,
                                       department TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS members (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    name TEXT NOT NULL,
    gender TEXT NOT NULL,
    age TEXT NOT NULL,
    department TEXT NOT NULL,
    position TEXT CHECK(POSITION IN ('Professor', 'Associate Professor', 'Assistant Professor', 'Lecturer I', 'Lecturer II', 'Adjunct Professor', 'Teaching Assistant', 'Research Assistant', 'Graduate Assistant Lecturer', 'Instructional Assistant')),
    type TEXT NOT NULL CHECK (type IN ('Teacher', 'Student'))
);

CREATE TABLE IF NOT EXISTS student_examinations (
    student_id INTEGER NOT NULL,
    examination_id INTEGER NOT NULL,
    score, REAL NOT NULL,
    PRIMARY KEY (student_id, examination_id),
    FOREIGN KEY (student_id) REFERENCES member(id),
    FOREIGN KEY (examination_id) REFERENCES examination(id)
);

CREATE TABLE IF NOT EXISTS questions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    question TEXT NOT NULL,
    optionA TEXT NOT NULL,
    optionB TEXT NOT NULL,
    optionC TEXT NOT NULL,
    optionD TEXT NOT NULL,
    answer TEXT NOT NULL,
    type TEXT NOT NULL,
    score REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS examinations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    courseID TEXT NOT NULL,
    examTime REAL NOT NULL,
    examName TEXT NOT NULL,
    publish TEXT NOT NULL
);