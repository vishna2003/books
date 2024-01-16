Sismics Books
=============

What is Books?
---------------

Books is an open source, lightweight book management system.

Books is written in Java, and may be run on any operating system with Java support.

Features
--------

- Responsive user interface
- Add book by barcode scanning
- Bookshelves system
- Multi-users
- Goodreads import
- Support Google Books API and Open Library API
- RESTful Web API

License
-------

Books is released under the terms of the GPL license. See `LICENSE` for more
information or see <http://opensource.org/licenses/GPL-2.0>.

How to build Books from the sources
-----------------------------------

Prerequisites: JDK 7, Maven 3, Tesseract 3.02

Books is organized in several Maven modules:

  - books-parent
  - books-core
  - books-web
  - books-web-common

First off, clone the repository: `git clone git://github.com/sismics/books.git`
or download the sources from GitHub.

#### Launch the build

From the `books-parent` directory:

    mvn clean -DskipTests install

#### Run a stand-alone version

From the `books-web` directory:

    mvn jetty:run

#### Navigate to the following URL on your browser:

  http://localhost:8080/books-web/#/login

Use default credentials as admin(username) and admin(password).
    
#### Build a .war to deploy to your servlet container

From the `books-web` directory:

    mvn -Pprod -DskipTests clean install

You will get your deployable WAR in the `target` directory.
