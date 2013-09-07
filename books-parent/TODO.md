TODO
====

- Delete a book (client/server)
- Redirect to the book once added (client)
- Api key: POST /app to modify it, warning if no key at all (client)
- Refetch book data at will from a specified source (client/server)
- Submit a custom cover URL (client/server)
- Edit the full book data: title, subtitle, author, description, isbn10, isbn13, page_count, language, publish_date (client/server)
- Add a book manually (client/server)
- FB Open Graph integration (client/server)

Facebook Graph API
------------------

https://developers.facebook.com/tools/explorer?method=GET&path=book.reads%3Fids%3D1036202288

me/friends?fields=first_name,last_name,id to fetch friends
book.reads?ids=1036202288,...&fields=data to fetch friends' books
?ids=10150805569322417,10150684831643097&fields=data to fetch books' ISBN