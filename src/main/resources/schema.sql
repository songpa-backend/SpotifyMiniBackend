CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL
);

CREATE TABLE musics (
                        music_id INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(100) NOT NULL,
                        artist VARCHAR(100) NOT NULL,
                        genre VARCHAR(50),
                        duration INT NOT NULL COMMENT '재생시간(초)',

                        INDEX idx_musics_title (title),
                        INDEX idx_musics_artist (artist)
);

CREATE TABLE comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY,
                          content VARCHAR(500) NOT NULL,
                          user_id INT NOT NULL,
                          music_id INT NOT NULL,

                          CONSTRAINT fk_comments_user
                              FOREIGN KEY (user_id)
                                  REFERENCES users(user_id)
                                  ON DELETE CASCADE,

                          CONSTRAINT fk_comments_music
                              FOREIGN KEY (music_id)
                                  REFERENCES musics(music_id)
                                  ON DELETE CASCADE,

                          INDEX idx_comments_music_id (music_id)
);

CREATE TABLE likes (
                       like_id INT AUTO_INCREMENT PRIMARY KEY,
                       user_id INT NOT NULL,
                       music_id INT NOT NULL,

                       CONSTRAINT fk_likes_user
                           FOREIGN KEY (user_id)
                               REFERENCES users(user_id)
                               ON DELETE CASCADE,

                       CONSTRAINT fk_likes_music
                           FOREIGN KEY (music_id)
                               REFERENCES musics(music_id)
                               ON DELETE CASCADE,

                       CONSTRAINT uk_likes_user_music
                           UNIQUE (user_id, music_id),

                       INDEX idx_likes_music_id (music_id)
);