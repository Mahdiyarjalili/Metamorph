INSERT INTO files (id,file_name, file_type, file_extention, created_date, modified_date, deleted_date, user_id)
VALUES (1,'example_file', 'image/jpeg', 'jpg', '2023-01-01', '2023-01-03', '2023-01-05', 'user123');
  ALTER TABLE files
    ALTER COLUMN id RESTART WITH 2;

