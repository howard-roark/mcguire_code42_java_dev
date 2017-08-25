# File Scanner README

## IDE Instructions
### IntelliJ
1. Use Intellij's built in VCS tools to pull down the project from [GitHub](https://github.com/howard-roark/mcguire_code42_java_dev.git)
1. In the project structure set the src/ dir to be the Source directory
1. Run tests with TestNG library

## Notes on development and usage
- Java SDK8
- Create a FileScanner object with the default constructor
```java
FileScanner fs = new FileScanner();
```
- Create a ScanResult object by passing in a valid path to the FileScanner's scan method
```java
ScanResult result = fs.scan('/');
```
- Access results via new ScanResult object
```java
result.getNumFiles();
result.getNumDirectories();
result.getTotalBytes();
result.getAvgBytes();
```
- Permission errors have not been handled in any other way than to print the directory that could not be accessed
