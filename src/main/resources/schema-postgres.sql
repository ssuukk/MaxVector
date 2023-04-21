CREATE EXTENSION vector;
DROP TABLE IF EXISTS items;
CREATE TABLE items (id bigserial PRIMARY KEY, embedding vector(3));