
INSERT INTO CHARACTER (id, name , image, age, weight, history) VALUES (1, 'char1', 'image-url1', 20, 5.1, 'history1' );
INSERT INTO CHARACTER  (id, name, image, age, weight, history) VALUES (2, 'char2', 'image-url2', 21, 5.2, 'history2' );
INSERT INTO CHARACTER  (id, name, image, age, weight, history) VALUES (3, 'char3', 'image-url3', 22, 5.3, 'history3' );
INSERT INTO CHARACTER  (id, name, image, age, weight, history) VALUES (4, 'char4', 'image-url4', 23, 5.4, 'history4' );
INSERT INTO CHARACTER  (id, name, image, age, weight, history) VALUES (5, 'char5', 'image-url5', 24, 5.5, 'history5' );
INSERT INTO CHARACTER  (id, name, image, age, weight, history) VALUES (6, 'char6', 'image-url6', 25, 5.6, 'history6' );

INSERT INTO MOVIE (id, title, image, creation_date, rating) VALUES (1, 'movie1', 'movie-url1', DATE '1991-09-07', 1 );
INSERT INTO MOVIE (id, title, image, creation_date, rating) VALUES (2, 'movie2', 'movie-url2', DATE '1992-09-07', 2 );
INSERT INTO MOVIE (id, title, image, creation_date, rating) VALUES (3, 'movie3', 'movie-url3', DATE '1992-10-06', 3 );
INSERT INTO MOVIE (id, title, image, creation_date, rating) VALUES (4, 'movie4', 'movie-url4', DATE '1993-12-08', 4 );
INSERT INTO MOVIE (id, title, image, creation_date, rating) VALUES (5, 'movie5', 'movie-url5', DATE '1982-11-02', 5 );

INSERT INTO CHARACTER_MOVIE (character_id, movie_id) VALUES ( 1, 1 );
INSERT INTO CHARACTER_MOVIE (character_id, movie_id) VALUES ( 1, 2 );
INSERT INTO CHARACTER_MOVIE (character_id, movie_id) VALUES ( 1, 3 );