package com.nc.med.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class DatabaseBackup {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${root.dir.path}")
    private String rootDirPath;

    @Value("${root.dir.dump_exe_path}")
    private String dumpExePath;

    @Value("${mysql.db_name:dummy}")
    private String mysqlDbName;

    public static void backup_mac(String dbUsername, String dbPassword, String dbName, String outputFile)
            throws IOException, InterruptedException {
        outputFile = System.getProperty("user.home") + "/Documents/smart-accounting-book/database_back_up";
        String command = String.format("mysqldump -u%s -p%s --add-drop-table --databases %s -r %s",
                dbUsername, dbPassword, dbName, outputFile);

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    public static void backup_windows(String userName, String password, String dbName, String dumpExe, String dumpSavePath) throws InterruptedException, IOException {
        String batchCommand = dumpExe +
                " -h localhost" +
                " --port 3306" +
                " -u " + userName +
                " --password=" + password +
                " --add-drop-database -B " + dbName +
                " -r " + dumpSavePath;

        Process process = Runtime.getRuntime().exec(batchCommand);
        process.waitFor();
    }

    private void findOldestFiles() throws IOException {
        List<Path> oldestFiles;
        Path path = Paths.get(rootDirPath);
        long count = 0L;

        try {
            count = Files.walk(path).filter(Files::isRegularFile).count();
        } catch (IOException ignored) {
        }

        long fileCount = 200L - count;
        if (fileCount < 0) {
            Comparator<? super Path> lastModifiedComparator =
                    Comparator.comparingLong(p -> p.toFile().lastModified());

            try (Stream<Path> paths = Files.walk(path)) {
                oldestFiles = paths.filter(Files::isRegularFile)
                        .sorted(lastModifiedComparator)
                        .limit(Math.abs(fileCount))
                        .collect(Collectors.toList());
            }

            if (count > 48) {
                for (Path path1 : oldestFiles) {
                    Files.walk(path1)
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile).forEach(File::delete);
                }
            }
        }
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void schedule() throws IOException {
        try {
            log.info("Backup Started at " + new Date());
            //Date backupDate = new Date();
            //SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            int day = now.getDayOfMonth();
            int hour = now.getHour();

            String saveFileName = year + "_" + month + "_" + day + "_" + hour + "_" + mysqlDbName + ".sql";
            String savePath = rootDirPath + File.separator + saveFileName;

            int failCount = 0;
            try {
                backup_mac(userName, password, mysqlDbName, savePath);
            } catch (Exception e) {
                failCount++;
            }

            try {
                backup_windows(userName, password, mysqlDbName, dumpExePath, savePath);
            } catch (Exception e) {
                failCount++;
            }

            if (failCount == 2) {
                log.error("Unable to take backup of mysql");
            } else {
                log.info("Backup has taken successfully");
            }
        } catch (Exception e) {
            log.error("Unable to take backup of mysql");
        } finally {
            findOldestFiles();
        }
    }
}
