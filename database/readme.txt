How to extract tables :

pg_dump --schema-only -U postgres --no-owner Dlearn_db > db.sql

v4 : added a field in survey (description)
v5 : changed field 'description' data type to varying char