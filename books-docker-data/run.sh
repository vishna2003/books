#!/bin/sh
docker rm -f sismics_books_data
docker run --name sismics_books_data sismics/books_data
