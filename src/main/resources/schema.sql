CREATE TABLE mc_server (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    work_file_path TEXT NOT NULL,
    file_name TEXT NOT NULL,
    stop_command TEXT NOT NULL
);