import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class FileOrganizer {

    private static final Map<String, String> MONTH_MAP = new HashMap<String, String>() {{
        put("January", "01");
        put("February", "02");
        put("March", "03");
        put("April", "04");
        put("May", "05");
        put("June", "06");
        put("July", "07");
        put("August", "08");
        put("September", "09");
        put("October", "10");
        put("November", "11");
        put("December", "12");
    }};

    public static void organize(String sourceDir, String targetDir) throws IOException {
        File[] filesToOrganize = new File(sourceDir).listFiles();
        if (filesToOrganize != null) {
            for (File file : filesToOrganize) {
                organizeFile(file, targetDir);
            }
        }
    }

    private static void organizeFile(File file, String baseDir) {
        String fileName = file.getName();
        String fileType = getFileExtension(fileName);

        if (file.isDirectory()) {
            handleDirectory(file, baseDir);
            return;
        }

        try {
            switch (fileType.toLowerCase()) {
                case ".txt":
                    moveFile(file, baseDir + "/Text Files");
                    break;
                case ".pdf":
                    moveFile(file, baseDir + "/PDFs");
                    break;
                case ".docx":
                case ".doc":
                    moveFile(file, baseDir + "/Word Documents");
                    break;
                case ".pptx":
                case ".ppt":
                    moveFile(file, baseDir + "/PowerPoints");
                    break;
                case ".html":
                    moveFile(file, baseDir + "/Web Pages");
                    break;
                case ".xlsx":
                case ".xls":
                    moveFile(file, baseDir + "/Excel Files");
                    break;
                case ".jpg":
                case ".jpeg":
                case ".png":
                case ".gif":
                case ".webp":
                case ".tiff":
                case ".bmp":
                    moveFile(file, baseDir + "/Pictures");
                    break;
                case ".mp4":
                case ".mkv":
                case ".avi":
                case ".mov":
                case ".flv":
                case ".wmv":
                case ".ts":
                    organizeVideos(file, baseDir);
                    break;
                case ".zip":
                case ".rar":
                case ".7z":
                case ".arj":
                case ".deb":
                case ".gz":
                case ".pkg":
                case ".sitx":
                case ".z":
                    moveFile(file, baseDir + "/Compressed Files");
                    break;
                case ".exe":  // Handle .exe files as Applications
                    moveFile(file, baseDir + "/Applications");
                    break;
                default:
                    moveFile(file, baseDir + "/Others");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleDirectory(File directory, String baseDir) {
        File[] filesInDir = directory.listFiles();
        if (filesInDir != null) {
            for (File file : filesInDir) {
                organizeFile(file, baseDir);
            }
        }
    }

    private static void moveFile(File sourceFile, String destinationDir) throws IOException {
        File destDir = new File(destinationDir);
        destDir.mkdirs();
        Path sourcePath = sourceFile.toPath();
        Path destPath = Paths.get(destinationDir, sourceFile.getName());

        try {
            Files.move(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved: " + sourceFile.getName() + " to " + destPath.toString());
        } catch (FileAlreadyExistsException e) {
            int count = 1;
            String baseName = getBaseName(sourceFile.getName());
            String extension = getFileExtension(sourceFile.getName());
            String newName = baseName + "_" + count + extension;
            while (Files.exists(Paths.get(destinationDir, newName))) {
                count++;
                newName = baseName + "_" + count + extension;
            }
            Files.move(sourcePath, Paths.get(destinationDir, newName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved: " + sourceFile.getName() + " to " + newName);
        }
    }

    private static void organizeVideos(File videoFile, String baseDir) throws IOException {
        long fileSizeMB = videoFile.length() / (1024 * 1024);

        if (fileSizeMB < 15) {
            moveFile(videoFile, baseDir + "/Videos/Shorts");
        } else if (fileSizeMB < 60) {
            moveFile(videoFile, baseDir + "/Videos/Videos");
        } else if (fileSizeMB < 400) {
            moveFile(videoFile, baseDir + "/Videos/Series");
        } else {
            moveFile(videoFile, baseDir + "/Videos/Movies");
        }
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex);
    }

    private static String getBaseName(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }
}
