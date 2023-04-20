CREATE EXTENSION vector;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS items;
CREATE TABLE cities(id serial PRIMARY KEY, name VARCHAR(255), population integer);
CREATE TABLE items (id bigserial PRIMARY KEY, embedding vector(3));