INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_MODERATOR'),
       ('ROLE_PRODUCER'),
       ('ROLE_LISTENER');

INSERT INTO users (username, password, email)
VALUES ('admin', '$2a$10$922YL199pGXmdqnzUDl0RuqfCYQFavczuDXl9I93jTbn6NKVnM7Au', 'admin@mail.ru');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);