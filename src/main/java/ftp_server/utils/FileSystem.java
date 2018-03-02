package ftp_server.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FileSystem {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd HH:mm", Locale.ENGLISH);

    public static String normalizeDirPath(String homename, String pathname) {
        String answer;

        Path path = Paths.get(isAbsolutePathname(pathname) ? homename + pathname : homename + "/" + pathname);

        if (isValidDirectoryName(path)) {
            String normPathname = path.normalize().toString();

            answer = normPathname.startsWith(homename) ? normPathname.substring(homename.length()) + "/" : "/";
        } else {
            answer = null;
        }

        return answer;
    }

    public static String getAbsolutePath(String homename, String pathname) {
        Path path = Paths.get(isAbsolutePathname(pathname) ? homename + pathname : homename + "/" + pathname);

        return path.normalize().toString();
    }

    private static boolean isAbsolutePathname(String pathname) {
        return !pathname.isEmpty() && '/' == pathname.charAt(0);
    }

    private static boolean isValidDirectoryName(Path path) {
        final File file = path.toFile();
        return file.exists() && file.isDirectory();
    }

    public static String fileInfo(String pathname) {
        File file = new File(pathname);
        String owner;
        try {
            owner = Files.getOwner(file.toPath()).toString();
        } catch (IOException e) {
            owner = "";
        }
        StringBuilder builder = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());

        builder.append(file.isDirectory() ? "d" : "-")
                .append(file.canRead() ? 'r' : '-')
                .append(file.canWrite() ? 'w' : '-')
                .append(file.canExecute() ? 'x' : '-')
                .append(' ')
                .append(String.format("%3d", 1)).append(' ')
                .append(String.format("%-8s", owner))
                .append(' ')
                .append(String.format("%-8s", owner))
                .append(' ')
                .append(String.format("%8d", file.length()))
                .append(' ')
                .append(DATE_FORMAT.format(cal.getTime()))
                .append(' ')
                .append(file.getName());

        return builder.toString();
    }

    public static String dirInfo(String homepath, String pathname) {
        StringBuilder builder = new StringBuilder();

        File file = new File(pathname);
        String owner;
        try {
            owner = Files.getOwner(file.toPath()).toString();
        } catch (IOException e) {
            owner = "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());

        builder.append(file.isDirectory() ? "d" : "-")
                .append(file.canRead() ? 'r' : '-')
                .append(file.canWrite() ? 'w' : '-')
                .append(file.canExecute() ? 'x' : '-')
                .append(' ')
                .append(String.format("%3d", 1)).append(' ')
                .append(String.format("%-8s", owner))
                .append(' ')
                .append(String.format("%-8s", owner))
                .append(' ')
                .append(String.format("%8d", file.length()))
                .append(' ')
                .append(DATE_FORMAT.format(cal.getTime()))
                .append(' ')
                .append('.')
                .append("\n");

        String parent = file.getParent();
        if(parent.startsWith(homepath)) {
            file = new File(parent);
        }

        try {
            owner = Files.getOwner(file.toPath()).toString();
        } catch (IOException e) {
            owner = "";
        }
        cal.setTimeInMillis(file.lastModified());

        builder.append(file.isDirectory() ? "d" : "-")
                .append(file.canRead() ? 'r' : '-')
                .append(file.canWrite() ? 'w' : '-')
                .append(file.canExecute() ? 'x' : '-')
                .append(' ')
                .append(String.format("%3d", 1)).append(' ')
                .append(String.format("%-8s", owner))
                .append(' ')
                .append(String.format("%-8s", owner))
                .append(' ')
                .append(String.format("%8d", file.length()))
                .append(' ')
                .append(DATE_FORMAT.format(cal.getTime()))
                .append(' ')
                .append("..")
                .append("\n");

        String[] files = new File(pathname).list();
        if (files != null) {
            for(String f : files) {
                builder.append(FileSystem.fileInfo(pathname + "/" + f))
                        .append("\n");
            }
        }
        return builder.toString();
    }
}
