INSERT INTO user_file (id,file_name, file_category,file_type, file_extension, created_date, modified_date, deleted_date, is_active,data,user_id)
VALUES (1,'example_file', 'image/jpeg', 'IMPORTED','jpg', '2023-01-01', '2023-01-03', '2023-01-05', true, X'89504E470D0A1A0A0000000D49484452','user123');
  ALTER TABLE user_file
    ALTER COLUMN id RESTART WITH 2;

