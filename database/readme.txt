How to extract tables :

pg_dump --schema-only -U postgres --no-owner Dlearn_db > db.sql

Insert Heroku data:

g_restore --verbose --clean --no-acl --no-owner -h localhost -U postgres -d "Dlearn_db" F:\SoftwareFactoryProject\DLearnHelsinki-backend\database\heroku_23.11.2017.dump