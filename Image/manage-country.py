#Import
import os
import base64



#Main
def main():
    print("Start")

    #Crate a file named countries.sql
    file1 = open("countries1.sql", "w")
    file2 = open("countries2.sql", "w")
    file3 = open("countries3.sql", "w")
    file4 = open("countries4.sql", "w")

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
            #Save the insert into a variable
            insert = "INSERT INTO countries (flag, country, difficulty) VALUES ('" + flag + "', '" + country + "', 1);"
            
            #Write the 50 first into a file named countries1.sql using file1
            if files.index(f) < 50:
                file1.write(insert + "")

            #Write the 50 next into a file named countries2.sql using file2
            elif files.index(f) < 100:
                file2.write(insert + "")
            
            #Write the 50 next into a file named countries3.sql using file3
            elif files.index(f) < 150:
                file3.write(insert + "")
                
            #Write the last one into a file named countries4.sql using file4
            else:
                file4.write(insert + "")

    #Close the files
    file1.close()
    file2.close()
    file3.close()
    file4.close()
    
    print("End")

#If Main
if __name__ == "__main__":
    main()