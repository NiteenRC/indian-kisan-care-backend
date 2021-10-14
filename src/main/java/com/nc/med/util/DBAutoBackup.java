package com.nc.med.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DBAutoBackup {

	@Scheduled(cron = "0 30 1 * * ?")
	public void schedule() {
		System.out.println("Backup Started at " + new Date());
		Date backupDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String dbNameList = "client_1 client_2";
		String saveFileName = "Daily_DB_Backup" + "_" + format.format(backupDate) + ".sql";
		String filePath = "/Users/niteenchougula/Documents/workspace/indian-kisan-care-backend/src/main/resources";
		String dbUserName = "root";
		String dbUserPassword = "Root@123";

		String savePath = filePath + File.separator + saveFileName;
		String executeCmd = "mysqldump -u " + dbUserName + " -p" + dbUserPassword + "  --databases " + dbNameList
				+ " -r " + savePath;

		Process runtimeProcess = null;
		try {
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int processComplete = 0;
		try {
			processComplete = runtimeProcess.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (processComplete == 0) {
			System.out.println("Backup Complete at " + new Date());
		} else {
			System.out.println("Backup Failure");
		}
	}
}
