CREATE TABLE IF NOT EXISTS grades (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      student_name TEXT NOT NULL,
                                      course_name TEXT NOT NULL,
                                      exam_name TEXT NOT NULL,
                                      score REAL NOT NULL,
                                      full_score REAL NOT NULL,
                                      time_spent REAL NOT NULL
);
CREATE TABLE IF NOT EXISTS member (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    name TEXT NOT NULL,
    gender TEXT NOT NULL,
    age TEXT NOT NULL,
    department TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS manager (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);