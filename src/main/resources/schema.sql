CREATE TABLE IF NOT EXISTS book (
                                    id SERIAL PRIMARY KEY,
                                    title VARCHAR(150) NOT NULL,
                                    author VARCHAR(150) NOT NULL,
                                    description VARCHAR(150)
);