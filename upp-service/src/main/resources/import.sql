INSERT INTO authorities (id, name) VALUES (1, "ROLE_READER");
INSERT INTO authorities (id, name) VALUES (2, "ROLE_WRITER");
INSERT INTO authorities (id, name) VALUES (3, "ROLE_LECTURER");
INSERT INTO authorities (id, name) VALUES (4, "ROLE_EDITOR");
INSERT INTO authorities (id, name) VALUES (5, "ROLE_CHIEF_EDITOR");
INSERT INTO authorities (id, name) VALUES (6, "ROLE_BOARD_MEMBER");


INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (1, "john@doe.com", true, "John", "Doe", "2017-10-01 21:58:58", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "john.doe", 1, "Vranje, Serbia", false, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (2, "jane@doe.com", true, "Jane", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "jane.doe", 2, "Novi Sad, Serbia", false, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points) VALUES (3, "baby@doe.com", true, "Baby", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "baby.doe", 1, "Belgrade, Serbia", true, 0);


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);   # john - writer
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 3);   # jane - lecturer
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);   # baby - reader

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
