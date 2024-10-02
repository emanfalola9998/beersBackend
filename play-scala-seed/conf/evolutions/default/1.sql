# --- !Ups

CREATE TABLE beers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  first_brewed VARCHAR(50),
  description TEXT,
  image_url VARCHAR(255),
  abv DOUBLE,
  ph DOUBLE
);

# --- !Downs

DROP TABLE beers;
