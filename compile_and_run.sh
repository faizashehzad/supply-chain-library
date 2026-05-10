#!/bin/bash
# 1. Create the bin directory if it doesn't exist
mkdir -p bin

# 2. Remove all existing .class files from the src directory
echo "Cleaning up redundant .class files from src..."
find src -name "*.class" -exec rm -f {} +

# 3. Compile the project into the bin directory
echo "Compiling..."
javac -d bin -sourcepath src $(find src -name "*.java")

# 4. Run the program
if [ $? -eq 0 ]; then
    echo "Compilation successful. Running demo..."
    java -cp bin client.Main
else
    echo "Compilation failed."
fi