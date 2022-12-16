#Import
import os
import base64



#Main
def main():
    print("Start")

    #Crate a file named countries.sql
    file = open("countries.sql", "w")

    #Get the name of every file in the folder where this script is located
    files = os.listdir()
    
    #Loop through the files
    for f in files:

        #If the file is a png file
        if f.endswith(".png"):

            #Get the name of the country
            country = f.split(".")[0]

            #Transform the image into base64 using base64.b64encode()
            flag = base64.b64encode(open(f, "rb").read()).decode("utf-8")
            
            #Create a insert statement int the table country with the following value flags wihch is the image (blob), country which is the country name and difficulty wich is at one.
            file.write("INSERT INTO countries (flag, country, difficulty) VALUES ('" + flag + "', '" + country + "', 1);")

    #Close the file
    file.close()

    #Ask the user if the script should be executed
    if input("Do you want to execute the script? (y/n)") == "y":
        #Execute the script
        insert_into_database()

    print("End")

#Def InsertIntoDatabase
def insert_into_database():
    #Variables
    user = "javaprojet"
    password = "devops"
    database = "javaprojet"
    host = "45.155.169.116"
    port= "6006"

    #Execute the script into the database using the variables
    os.system("mysql -u " + user + " -p" + password + " -D " + database + " -h " + host + " -P " + port + " < countries.sql")

#If Main
if __name__ == "__main__":
    main()