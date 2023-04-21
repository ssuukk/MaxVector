# MaxVector
A locally running vector database with GraphQL and REST API, with built-in methods to obtain embeddings from OpenAI and other services.

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
      findClosest(prompt: "house animal", k: 3) {
        id
        label
      }
    }

Of course - more features will be added as necessary.

## So how do I run it?
All you need is a local instance of Postgres with [pgvector](https://github.com/pgvector/pgvector) extension and a JAR build from this repo.
