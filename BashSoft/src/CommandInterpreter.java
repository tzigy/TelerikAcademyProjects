import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Tzigy on 05.06.2016.
 */
public class CommandInterpreter {

    public static void interpretCommand(String input) {
        String[] data = input.split("\\s+");
        String command = data[0];

        switch (command) {
            case "open":
                tryOpenFile(input, data);
                break;
            case "mkdir":
                tryCreateDirectory(input, data);
                break;
            case "ls":
                tryTraverseFolders(input, data);
                break;
            case "cmp":
                tryCompareFiles(input, data);
                break;
            case "changeDirRel":
                tryChangeRelativePath(input, data);
                break;
            case "changeDirAbs":
                tryChangeAbsolutePath(input, data);
                break;
            case "readDb":
                tryReadDataBaseFromFile(input, data);
                break;
            case "help":
                getHelp(input, data);
                break;
            case "show":
                tryShowWantedCourse(input, data);
                break;
            case "download":
                tryDownloadFile(input, data);
                break;
            case "downloadAsynk":
                tryDownloadAsynk(input, data);
                break;
            default:
                displayInvalidCommandMessage(command);
                break;
        }
    }


    private static void displayInvalidCommandMessage(String input) {
        String output = String.format("The command '%s' is invalid!\n", input);
        OutputWriter.displayException(output);
    }

    private static void tryDownloadAsynk(String input, String[] data) {

    }

    private static void tryDownloadFile(String input, String[] data) {

    }

    private static void tryOpenFile(String input, String[] data) {

        if (data.length != 2) {
            displayInvalidCommandMessage(input);
            return;
        }

        String fileName = data[1];
        String filePath = SessionData.currentPath + "\\" + fileName;
        File file = new File(filePath);

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tryCreateDirectory(String input, String[] data) {

        if (data.length != 2) {
            displayInvalidCommandMessage(input);
            return;
        }

        String fileName = data[1];
        IOManager.createDirectoryInCurrentFolder(fileName);
    }

    private static void tryTraverseFolders(String input, String[] data) {

        if (data.length != 1 && data.length != 2) {
            displayInvalidCommandMessage(input);
            return;
        }

        if (data.length == 1) {
            IOManager.traverseDirectory(0);
        } else {
            Integer depth = Integer.parseInt(data[1]);
            IOManager.traverseDirectory(depth);
        }
    }

    private static void tryCompareFiles(String input, String[] data) {

        if (data.length != 3){
            displayInvalidCommandMessage(input);
            return;
        }

        String firstPath = data[1];
        String secondPath = data[2];
        Tester.compareContent(firstPath, secondPath);
    }
    private static void tryChangeRelativePath(String input, String[] data) {

        if (data.length != 2){
            displayInvalidCommandMessage(input);
            return;
        }

        String relativePath = data[1];
        IOManager.changeCurrentDirRelativePath(relativePath);
    }

    private static void tryChangeAbsolutePath(String input, String[] data) {

        if (data.length != 2){
            displayInvalidCommandMessage(input);
            return;
        }

        String absolutePath = data[1];
        IOManager.changeCurrentDirAbsolute(absolutePath);
    }

    private static void tryReadDataBaseFromFile(String input, String[] data) {
        if (data.length != 2){
            displayInvalidCommandMessage(input);
            return;
        }

        String fileName = data[1];
        StudentsRepository.initializeData(fileName);
    }

    private static void getHelp(String input, String[] data) {

        OutputWriter.writeMessageOnNewLine("mkdir path - make directory");
        OutputWriter.writeMessageOnNewLine("ls depth - traverse directory");
        OutputWriter.writeMessageOnNewLine("cmp path1 path2 - compare two files");
        OutputWriter.writeMessageOnNewLine("changeDirRel relativePath - change directory");
        OutputWriter.writeMessageOnNewLine("changeDir absolutePath - change directory");
        OutputWriter.writeMessageOnNewLine("readDb path - read students data base");
        OutputWriter.writeMessageOnNewLine("filterExcelent - filter excelent students (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("filterExcelent path - filter excelent students (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("filterAverage - filter average students (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("filterAverage path - filter average students (the output is written in a file)");
        OutputWriter.writeMessageOnNewLine("filterPoor - filter low grade students (the output is on the console)");
        OutputWriter.writeMessageOnNewLine("filterPoor path - filter low grade students (the output is written in a file)");
        OutputWriter.writeMessageOnNewLine("order - sort students in increasing order (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("order path - sort students in increasing order (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("decOrder - sort students in decreasing order (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("decOrder path - sort students in decreasing order (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("download pathOfFile - download file (saved in current directory)");
        OutputWriter.writeMessageOnNewLine("downloadAsync path - download file asynchronously (save in the current directory)");
        OutputWriter.writeMessageOnNewLine("help - get help");
        OutputWriter.writeEmptyLine();
    }

    private static void tryShowWantedCourse(String input, String[] data) {
        if(data.length != 2 && data.length != 3){
            displayInvalidCommandMessage(input);
            return;
        }

        String courseName = data[1];

        if (data.length == 2){

            StudentsRepository.getStudentsByCourse(courseName);
        }

        if (data.length == 3){
            String userName = data[2];
            StudentsRepository.getStudentsMarksInCourse(courseName, userName);
        }
    }
}
