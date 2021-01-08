INSERT INTO authorities (id, name) VALUES (1, "ROLE_READER");
INSERT INTO authorities (id, name) VALUES (2, "ROLE_WRITER");
INSERT INTO authorities (id, name) VALUES (3, "ROLE_LECTURER");
INSERT INTO authorities (id, name) VALUES (4, "ROLE_EDITOR");
INSERT INTO authorities (id, name) VALUES (5, "ROLE_CHIEF_EDITOR");
INSERT INTO authorities (id, name) VALUES (6, "ROLE_BOARD_MEMBER");


INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (1, "b.sulicenko+1@gmail.com", true, "John", "Doe", "2017-10-01 21:58:58", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "john.doe", "Writer", "Vranje, Serbia", false, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (2, "jane@doe.com", true, "Jane", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "jane.doe", "Lecturer", "Novi Sad, Serbia", false, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (3, "baby@doe.com", true, "Baby", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "baby.doe", "Reader", "Belgrade, Serbia", true, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (4, "b.sulicenko@gmail.com", true, "Pera", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "pera", "ChiefEditor", "Novi Sad, Serbia", false, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (5, "zika@doe.com", true, "Zika", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "zika", "Editor", "Novi Sad, Serbia", false, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (6, "mika@doe.com", true, "Mika", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "mika", "Editor", "Novi Sad, Serbia", false, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (7, "baby2@doe.com", true, "Baby", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "baby.doe2", "Reader", "Belgrade, Serbia", true, 0);

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);   # john - writer
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 3);   # jane - lecturer
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);   # baby - reader
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 5);   # pera - chief editor
INSERT INTO user_authority (user_id, authority_id) VALUES (5, 4);   # zika - editor
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 4);   # mika - editor
INSERT INTO user_authority (user_id, authority_id) VALUES (7, 1);   # baby2 - reader

INSERT INTO genres (id, name) VALUES (1, "Romance");
INSERT INTO genres (id, name) VALUES (2, "Comedy");
INSERT INTO genres (id, name) VALUES (3, "Drama");
INSERT INTO genres (id, name) VALUES (4, "Animation");
INSERT INTO genres (id, name) VALUES (5, "Fantasy");
INSERT INTO genres (id, name) VALUES (6, "Adventure");
INSERT INTO genres (id, name) VALUES (7, "Historical");
INSERT INTO genres (id, name) VALUES (8, "Horror");
INSERT INTO genres (id, name) VALUES (9, "Thriller");
INSERT INTO genres (id, name) VALUES (10, "Mystery");

INSERT INTO writer_genre (writer_id, genre_id) VALUES (1, 1);
INSERT INTO writer_genre (writer_id, genre_id) VALUES (1, 2);
INSERT INTO reader_genre (reader_id, genre_id) VALUES (3, 7);
