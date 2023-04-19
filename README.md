# Pool Connection using JDBC
### Introduction
- In this project, we have made a CRUD (Create, Read, Update, Delete) with MariaDB using the Hikary library to establish a connection pool.
### DATABASE
- We have created a table called programming_language that contains 2 columns: name and rating.
### HIKARI
- Hikari offers us a JDBC implementation that provides us with a connection pool to our database.
### CODE
#### CLASS MAIN()
- The first thing we have done is to import the HikariDataSource class which is part of the Hikari library and allows us to create and maintain a connection pool for our database. We have configured it with our connection to the database through the URL, username and password in the initDatabaseConnectionPool() method.
- In the main we have called the different methods that complete the CRUD operating on the database.
#### CREATEDATA()
- With this method, what we do is to insert data on our programming_language table.
- We establish the connection
- We make an INSERT to insert the data to our table
```
INSERT INTO programming_language(name, rating) VALUES (?, ?)
```
#### READDATA()
- With this method, it allows us to read the data that we have in our table.
- We establish the connection
- We perform a SELECT to show us the columns name and rating, in descending order by rating.
```
SELECT name, rating FROM programming_language ORDER BY rating DESC
```
#### UPDATEDATA()
- This method allows us to update the data we have in the table.
- Establish the connection
- We perform an UPDATE with the new values that we want the rows to have.
```
 UPDATE programming_language SET rating = ? WHERE name = ?
```
#### DELETEDATA()
- This method allows us to delete data from our table.
- We establish the connection
- We make a DELETE that will allow us to delete by name
```
 DELETE FROM programming_language WHERE name LIKE ?
```
#### INITDATABASECONNECTIONPOOL()
- This method is responsible for making the connection, using the URL, username and password.
#### CLOSEDATABASECONNECTION
- This method allows us to close the connection pool when it is no longer necessary with the HIKARI close() method.
### CONCLUSION
We find it very interesting that the connection pool allows you to reuse the connections to save resources, to allow several simultaneous connections to the database without waiting, etc.
