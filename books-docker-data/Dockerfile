FROM busybox
VOLUME ["/var/books"]
ADD data /var/books/
RUN chown -R 1001:1001 /var/books
CMD ["/bin/sh"]
