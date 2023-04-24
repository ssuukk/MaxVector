# MaxVector
A locally running vector database with GraphQL API, with built-in methods to obtain embeddings from OpenAI 
and other services. Written in Kotlin using Spring, with Postgres as the storage. Vector similarity provided 
by ultra fast algorithms of the [Faiss](https://github.com/facebookresearch/faiss) library.

## Why?
One day I tried to get Pinecone access and they put me on a wait list! I'm too impatient, so I just created a vector database myself.

## What does it do?
MaxVector is a vector database created for AI applications, so you can use it exactly like any other vector db - to store your
embeddings and query closest ones by distance (using euclidean, cosine or inner product) or metadata.

## Any other features?
Oh, sure. It can obtain embeddings automagically for you! With GraphQL interface it's enough to call this mutation to 
obtain embedding vectors for a list of words from OpenAI **straight into your database**:

    mutation storeEmbedding {
      storeEmbedding(queries: ["dog", "shark", "parrot"]) {
        status
        error
        count
      }
    }

Or it could lookup embeddings in your database by prompt, first obtaining the embedding from OpenAI, like here:

    query findClosest {
      findClosest(prompt: "house animal", k: 3, measure: COSINE) {
        id
        label
        metadata {
          example
        }
        embedding {
          coords
        }
      }
    }

If you prefer to query the DB by supplying all 2048 coordinates, you can of course do that, too:

    query closestVectors {
      findClosestByVector(vec:[1.5, 3.45, 32.3, (2045 more coords...)], k: 3, measure: EUCLIDEAN) {
        id
        label
      }  
    }


![Built-in GraphQL interface](resources/GraphQL_interface.jpg)

Of course - more features will be added as necessary.

# Installation
All you need is a local instance of Postgres with [pgvector](https://github.com/pgvector/pgvector) extension and a JAR build from this repo.

## Installing and configuring Postgres

On any Debian-ish Linux install Postgres by:

    $ sudo apt-get install postgresql

Then configure admin user and password:

    $ sudo -u postgres psql postgres

And in Postgres shell:

    postgres=# \password postgres

To set up password for the postgres user, which is empty by default. You can then exit Postgres shell, and create
a new database user - this is the user you will need to put in `application.properties` file later:

    $ sudo -u postgres createuser --interactive --password user12
    Shall the new role be a superuser? (y/n) n
    Shall the new role be allowed to create databases? (y/n) y
    Shall the new role be allowed to create more new roles? (y/n) n
    Password: 

Create a db - whatever name you choose you will need to use it in `application.properties` file later:

    $ sudo -u postgres createdb testdb -O user12

Finally edit your postgres config file to trust locally running MaxVector:

    $ sudo vi /etc/postgresql/9.5/main/pg_hba.conf

Edit the file like this:

    # "local" is for Unix domain socket connections only
    local   all             all                                     trust
    # IPv4 local connections:
    host    all             all             127.0.0.1/32            trust

And restart postgres service:

    $ sudo service postgresql restart

## Installing Postgres vector extension

Go to [pgvector GitHub](https://github.com/pgvector/pgvector#installation) and check the installation section 
there to install Vector extension.

## Configuring MaxVector

Edit `src\main\resources\application.properties` file, add `openAIapiKey` line with your OpenAI API key:

    spring.datasource.url=jdbc:postgresql://localhost:5432/<the name of the db>
    spring.datasource.username=<the non-admin user you added>
    spring.datasource.password=<the user's password>
    
    openAIapiKey=sk-<your-openAPI-key>

## Build the application

Use Gradle.

# Usage

Query the db with GraphQL, either by plain HTTP requests or any GraphQL client, like [Apollo](https://github.com/apollographql)
or the built-in web interface on port [8080](http://localhost:8080/graphiql?path=/graphql) of your machine.
Check `src\main\resources\graphql\schema.gqls` for currently implemented queries and mutations.

Note that first insert into the database will determine dimensionality of the vectors it holds.