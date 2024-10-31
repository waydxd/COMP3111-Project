CREATE TABLE IF NOT EXISTS grades (
                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      student_name TEXT NOT NULL,
                                      course_name TEXT NOT NULL,
                                      exam_name TEXT NOT NULL,
                                      score REAL NOT NULL,
                                      full_score REAL NOT NULL,
                                      time_spent REAL NOT NULL
);
