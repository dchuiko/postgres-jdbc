postgres-jdbc
=============

`postgresql-jdbc-8.3-603.src/` is the original source patched with
https://github.com/deafbybeheading/pgjdbc/commit/d2a7d481a260c70bc62285eacb22cdebdeba8e1e.
The patch completely removes prepared statements when `prepareThreshold=0`.
