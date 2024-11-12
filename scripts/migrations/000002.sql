CREATE TABLE IF NOT EXISTS member (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    name TEXT NOT NULL,
    gender TEXT NOT NULL,
    age TEXT NOT NULL,
    department TEXT NOT NULL
);