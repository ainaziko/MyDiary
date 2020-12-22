# MyDiary
Do you keep a journal and write in there everything you think including your secrets, feelings to capture moments you don't want to forget soon but fear less someone reads it? Here is the solution - application that protects your notes for sure, so no one has possibility to have an access to them.

This Application works with MySQL database. Notes are capable of being added (created), read, updated, deleted. Notes are updated evey time user types a letter.
There are two tables with one-to-many relationship in database: users and notes. If user is deleted, then his notes will be deleted automatically. App can be used by multiple users and even if app is deleted, notes will not be lost anyway unless user himself/herself deletes them by hand. 

There are 3 scenes: authorisation, registration and main. Animations are also included and work in authorisation window when user doesn't exist or incorrect password whether login is typed in fields.
