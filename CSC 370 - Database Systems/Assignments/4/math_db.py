import psycopg2
import webbrowser

PSQL_DB = 'kaizen_blitz'
PSQL_USER = 'sarahspa'
PSQL_HOST = 'studentdb1.csc.uvic.ca'
PSQL_PW = 'V00819941' # This will be what you set during the installation of PostgreSQL

class User:
    def __init__(self, userID, db):
        self.userID = userID
        self.preferred_name = None
        self.db = db

    def get_worksheet(self):
        '''A worksheet selector interface which will prompt the user for the language, then topic,
        then level, then version. It will the open up a browser with the worksheet url'''
        print("\n--- Worksheet Selector ---\n")
        cursor = self.db.connection.cursor()

        # Prompt user for language, topic, level, version

        # Get language
        cursor.execute("PREPARE getWorksheetLanguage(int) as "
                       "SELECT language FROM languageofuser WHERE userID = $1")
        cursor.execute("EXECUTE getWorksheetLanguage(%s)", (str(self.userID),))
        data_list = cursor.fetchall()
        language = self._worksheet_prompt(data_list = data_list,
                                          err = "No available languages",
                                          prompt = "Select a language",
                                          data_name = "Languages")
        cursor.execute("DEALLOCATE getWorksheetLanguage")

        # Get topic
        cursor.execute("PREPARE getWorksheetTopic(text) as "
                       "SELECT DISTINCT topic FROM worksheets WHERE worksheetlanguage = $1;")
        cursor.execute("EXECUTE getWorksheetTopic(%s)", (str(language),))
        data_list = cursor.fetchall()                             
        topic = self._worksheet_prompt(data_list = data_list,
                                       err = "No available topics for this language!",
                                       prompt = "Select a topic",
                                       data_name = "Topics")
        cursor.execute("DEALLOCATE getWorksheetTopic")

        # Get level
        cursor.execute("PREPARE getWorksheetLevel(text, text) as "
                       "SELECT DISTINCT level FROM worksheets WHERE worksheetlanguage = $1 AND topic = $2;")
        cursor.execute("EXECUTE getWorksheetLevel(%s, %s)", (str(language), str(topic)))
        data_list = cursor.fetchall()
        level = self._worksheet_prompt(data_list = data_list,
                                       err = "Error",
                                       prompt = "Select a level",
                                       data_name = "Levels")
        cursor.execute("DEALLOCATE getWorksheetLevel")

        # Get version
        cursor.execute("PREPARE getWorksheetVersion(text, text, int) as "
                        "SELECT version from worksheets WHERE worksheetlanguage = $1 AND topic = $2 AND level = $3")
        cursor.execute("EXECUTE getWorksheetVersion(%s, %s, %s)", (str(language), str(topic), str(level)))
        data_list = cursor.fetchall()
        version = self._worksheet_prompt(data_list = data_list,
                                         err = "Error",
                                         prompt = "Select a version",
                                         data_name = "Versions")
        cursor.execute("DEALLOCATE getWorksheetVersion")

        # Get worksheet URL for specified worksheet
        cursor.execute("PREPARE getWorksheetURL(text, text, int, int) as "
                        "SELECT worksheeturl from worksheets WHERE worksheetlanguage = $1 AND topic = $2 AND level = $3 AND version = $4;")
        cursor.execute("EXECUTE getWorksheetURL(%s, %s, %s, %s)", (str(language), str(topic), str(level), str(version)))
        url = cursor.fetchone()[0]
        if not url:
            print("Error: Worksheet url does not exist")
            exit()
        cursor.execute("DEALLOCATE getWorksheetURL")

        # Open worksheet URL
        print("\n\nYou have selected worksheet: " + str(topic) + " " + str(level) + " in " + str(language) + ", version " + str(version))
        print("\nNow opening browser with url: " + str(url))
        webbrowser.open_new_tab(url)
        try:
            input("\nPress enter to return to main menu.")
        except Exception:
            pass

    def _worksheet_prompt(self, data_list, prompt, err, data_name):
        '''Will display the data (options) with data_name as the header.
        Then it will prompt the user tpo pick an option. If an error occurs,
        the err param is printed.'''
        valid_input = False
        if not data_list:
            print(err) # TODO: could make it go back to a prompt for user instead of exiting
            exit()
        
        option_num = 1
        print(data_name + ": ")
        for data in data_list:
            print(str(option_num) + ": " + str(data[0]))
            option_num = option_num + 1

        # Select data from list
        while not valid_input:
            option = input("\n" + prompt + ": ")
            try:
                option = int(option)
                if option < 1:
                    raise ValueError
                data = data_list[option - 1][0]
                valid_input = True
            except (IndexError, ValueError):
                # User input was invalid
                print("Invalid option. Please try again.")

        print("\n-----------------------\n")
        return data

class Teacher(User):
    def __init__(self, userID, db):
        User.__init__(self, userID, db)
    
    def interface(self):
        print("\nHello teacher, " + str(self.preferred_name) + "!")
        print("Teacher interface not implemented yet.")

class Student(User):
    def __init__(self, userID, db):
        User.__init__(self, userID, db)
        self.position = "Student"

    def interface(self):
        '''Is the main student text interface. It will prompt the user to select
        an option.'''
        print("\nHello student, " + str(self.preferred_name) + "!")
        print("\nOptions:\n")
        print("1: Get worksheet\n"
              "2: Submit worksheet\n"
              "3: Check mark\n"
              "4: Quit")

        valid_option = False
        while not valid_option:
            try:
                option = int(input("\nSelect an option: "))
            except ValueError:
                print("Invalid option. Please try again.")
                continue

            if (option == 1):
                self.get_worksheet()
                valid_option = True
            elif (option == 2):
                self.submit_worksheet()
                valid_option = True
            elif (option == 3):
                self.check_mark()
                valid_option = True
            elif (option == 4):
                return False
            else:
                print("Invalid option. Please try again.")
        return True

    def submit_worksheet(self):
        print("Submitting worksheets not implemented yet.")
        input("Press enter to continue.")

    def check_mark(self):
        print("Check marks not implemented yet.")
        input("Press enter to continue.")

class MathDB:
    def __init__(self, db_name, user_name, password, host):
        self.db_name = db_name
        self.user_name = user_name
        self.password = password
        self.host = host
        self.connection = None
        self.user = None

    def open(self):
        """Open database"""
        self.connection = psycopg2.connect(dbname = self.db_name, user = self.user_name, password = self.password, host = self.host)

    def close(self):
        """Close database"""
        self.connection.close()

    def reset_db(self):
        """Delete any existing tables and recreate them"""
        try:
            cursor = self.connection.cursor()
            cursor.execute(open("a3.txt", "r").read())
            self.connection.commit()
        except Exception as e:
            print("Could not reset DB: " + str(e))
        print("Database reset")

    def fill_db(self):
        """Fill in database with sample data"""
        try:
            cursor = self.connection.cursor()
            cursor.execute(open("sample_data.txt", "r").read()) # TODO: we need sample_data.txt file!
            self.connection.commit()
        except Exception as e:
            print("Could not fill DB: " + str(e))
        print("Database filled")

    def set_user(self, userID):
        """Check if userID is valid or not. Return corresponding student/teacher, or None if userID not found."""
        cursor = self.connection.cursor()
        user = None

        # Check if user is student
        cursor.execute("PREPARE checkIfStudent(int) as "
                       "SELECT COUNT(1) FROM students WHERE userid = $1")
        cursor.execute("EXECUTE checkIfStudent(%s)", (userID,))
        if cursor.fetchone()[0] == 1:
            user = Student(userID, self)
        cursor.execute("DEALLOCATE checkIfStudent")

        # Check if user is teacher
        cursor.execute("PREPARE checkIfTeacher(int) as "
                       "SELECT COUNT(1) FROM teachers WHERE userid = $1")
        cursor.execute("EXECUTE checkIfTeacher(%s)", (userID,))
        if cursor.fetchone()[0] == 1:
            user = Teacher(userID, self)
        cursor.execute("DEALLOCATE checkIfTeacher")

        if user is not None:
            # Get user's preferred name
            cursor.execute("PREPARE getName(int) as "
                        "SELECT preferredname FROM users WHERE userid = $1")
            cursor.execute("EXECUTE getName(%s)", (userID,))
            user.preferred_name = cursor.fetchone()[0]
            cursor.execute("DEALLOCATE getName")

        return user

    def verify_db(self):
        """Verify that all required tables exist."""
        pass

def main():
    db = MathDB(PSQL_DB, PSQL_USER, PSQL_PW, PSQL_HOST)
    db.open()
    #db.reset_db()
    #db.fill_db()

    user = None
    while user is None:
        try:
            userID = input("Welcome to the math database. Please enter your userID:")
            user = db.set_user(userID)
        except Exception:
            user = None
            userID = ""
        
        if user is None:
            print("UserID " + str(userID) + " not found. Try again.")

    cont = True
    while cont:
        cont = user.interface()

    print("Goodbye!")
    db.close()

if __name__ == "__main__":
    main()
