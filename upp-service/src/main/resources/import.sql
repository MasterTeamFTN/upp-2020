INSERT INTO authorities (id, name) VALUES (1, "ROLE_READER");
INSERT INTO authorities (id, name) VALUES (2, "ROLE_WRITER");
INSERT INTO authorities (id, name) VALUES (3, "ROLE_LECTURER");
INSERT INTO authorities (id, name) VALUES (4, "ROLE_EDITOR");
INSERT INTO authorities (id, name) VALUES (5, "ROLE_CHIEF_EDITOR");
INSERT INTO authorities (id, name) VALUES (6, "ROLE_BOARD_MEMBER");

INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (1, "john@gmail.com", true, "John", "Doe", "2017-10-01 21:58:58", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "john.doe", "Writer", "Vranje, Serbia", false, 0, 1);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (2, "jane@doe.com", true, "Jane", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "jane.doe", "Lecturer", "Novi Sad, Serbia", false, 0, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (3, "jankobree@hotmail.com", true, "Baby", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "baby.doe", "Reader", "Belgrade, Serbia", true, 0, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (4, "dummyfoo77@gmail.com", true, "Pera", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "pera", "ChiefEditor", "Novi Sad, Serbia", false, 0, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (5, "zika@doe.com", true, "Zika", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "zika", "Editor", "Novi Sad, Serbia", false, 0, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (6, "mika@doe.com", true, "Mika", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "mika", "Editor", "Novi Sad, Serbia", false, 0, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (7, "baby2@doe.com", true, "Baby", "Doe", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "baby.doe2", "Reader", "Belgrade, Serbia", true, 0, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (8, "jankobree@hotmail.com", true, "Board", "Memberovic", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "board.member", "BoardMember", "Belgrade, Serbia", false, 0, 0);
INSERT INTO users (id, email, enabled, first_name, last_name, last_password_reset_date, password, username, dtype, city_country, is_beta_reader, penalty_points, is_member) VALUES (9, "dummyfoo77@gmail.com", true, "member", "Boardovic", "2017-09-01 22:40:00", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "member.board", "BoardMember", "Belgrade, Serbia", false, 0, 0);

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);   # john - writer
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 3);   # jane - lecturer
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);   # baby - reader
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 5);   # pera - chief editor
INSERT INTO user_authority (user_id, authority_id) VALUES (5, 4);   # zika - editor
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 4);   # mika - editor
INSERT INTO user_authority (user_id, authority_id) VALUES (7, 1);   # baby2 - reader
INSERT INTO user_authority (user_id, authority_id) VALUES (8, 6);   # board - BoardMember
INSERT INTO user_authority (user_id, authority_id) VALUES (9, 6);   # member - BoardMember

INSERT INTO genres (name) VALUES ("Romance");
INSERT INTO genres (name) VALUES ("Comedy");
INSERT INTO genres (name) VALUES ("Drama");
INSERT INTO genres (name) VALUES ("Animation");
INSERT INTO genres (name) VALUES ("Fantasy");
INSERT INTO genres (name) VALUES ("Adventure");
INSERT INTO genres (name) VALUES ("Historical");
INSERT INTO genres (name) VALUES ("Horror");
INSERT INTO genres (name) VALUES ("Thriller");
INSERT INTO genres (name) VALUES ("Mystery");

INSERT INTO writer_genre (writer_id, genre_id) VALUES (1, 1);
INSERT INTO writer_genre (writer_id, genre_id) VALUES (1, 2);
INSERT INTO reader_genre (reader_id, genre_id) VALUES (3, 7);

INSERT INTO decisions (name) VALUES ("Approve");
INSERT INTO decisions (name) VALUES ("Reject");
INSERT INTO decisions (name) VALUES ("NeedMoreInfo");
