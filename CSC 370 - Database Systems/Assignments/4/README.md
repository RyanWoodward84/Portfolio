# math-db
## Instructions
1. Download and install [PostgreSQL v.12](https://www.postgresql.org/download/). Note the password that you use when installing (I used `postgres`).
2. Download and install [psycopg2](https://pypi.org/project/psycopg2/)

## How to make a local PostgreSQL database in Windows
1. Add the PostgreSQL bin folder to your Windows environment path: Go to Control Panel > System > Advanced System Settings > Environment Variables > from the System Variables box select "PATH", then append this string to the existing PATH Variable Value: `;C:\Program Files\PostgreSQL\12\bin`. Note that the directory will be different if you installed PostgreSQL somewhere else or if you install a different version than 12.
2. Open up command prompt.
3. Type `psql -U postgres` to connect with username `postgres`. Press enter.
4. When prompted for a password, type the password that you used when installing PostgreSQL. In my case, I used `postgres`. Press Enter.
5. If successful, you should now see a prompt as `postgres=#`. To create a database, type `CREATE DATABASE math;` and press enter. This will take a few seconds If successfull, you should see `CREATE DATABASE` and a new prompt.
6. Tada! You can now connect to the database `math` with username `postgres` and password `postgres` through our Python code.

## Access your database with psql
To verify that the python code is working correctly, you can access your database with psql and see that the data is valid:  
1. In command prompt, type `psql -U postgres` to login as user `postgres`. Press enter.
2. Enter your password (`postgres`) and press enter.
3. You can now directly interact with your databases and view the data.

Some useful commands:  
* `\list`: List all the databases
* `\connect math`: Connect to the math database
* `\dt`: List all the tables in the current database
* `\d+ users`: List schema of users table

## Resources
* [psycopg2 Documentation](http://initd.org/psycopg/docs/)
* [psql commands](http://postgresguide.com/utilities/psql.html)