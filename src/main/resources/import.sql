INSERT INTO user (username, password) VALUES ('test', 'test');


-- Table authority
INSERT INTO authority (name) VALUES ('admin');


-- Table user_authorities
INSERT INTO user_authorities(user_id, authority_id) VALUES ((SELECT id FROM user WHERE username='test'), (SELECT id FROM authority WHERE name='admin'));


create table if not exists oauth_access_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(255)
);

create table if not exists oauth_refresh_token (
  token_id VARCHAR(255),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);