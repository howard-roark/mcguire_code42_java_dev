# Input And Analysis README

## IDE Instructions
### Intellij
1. Use Intellij's built in VCS tools to pull down the project from [GitHub](https://github.com/howard-roark/mcguire_code42_java_dev.git)
1. In the project structure set the src/ dir to be the Source directory
1. Run tests with TestNG library
1. Running InputAndAnalysis with no arguments will print the report for the sample data used for unit testing
1. Editing the run configurations will allow you too enter in an absolute path to a text file that can be read and reported on

## Notes on development and usage
- Java SDK Version 8
- Must instantiate InputAndAnalysis with a valid path to a file
- Once an object is successfully created you must run the object
    ```java
    InputAndAnalysis iaa = new InputAndAnalysis("/valid/path/to/file.txt");
   iaa.run();
    ```
- Once the object has been run the user can print the desired report via the object's toString method
    ```java
    System.out.println(iaa);
    ```
- The public API will also be ready for consumption
    ```java
    iaa.getTotal();
    iaa.getAverage();
    iaa.getMedian();
    iaa.getCountOfNumbers();
    iaa.contains(String src);
    ```
