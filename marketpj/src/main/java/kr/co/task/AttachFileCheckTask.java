package kr.co.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.controller.AdminController;
import kr.co.dao.AdminDAO;
import kr.co.vo.AttachImageVO;

@Component
public class AttachFileCheckTask {
	
	
	private static final Logger logger = LoggerFactory.getLogger(AttachFileCheckTask.class);
	
	@Autowired
	private AdminDAO dao;
	
	private String getFolderYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}	
	
	@Scheduled(cron="0 0 1 * * *")
	public void checkFiles() throws Exception{	
		
		System.out.println("시작");
		// DB에 저장된 파일 리스트
		List<AttachImageVO> fileList = dao.checkFileList();
		for(int i=0; i<fileList.size() ; i++) {
			System.out.println("DB에 저장된 파일 리스트:  " + fileList.get(i));
		}
		
		
		// 비교 기준 파일 리스트(Path객체)
		List<Path> checkFilePath = new ArrayList<Path>();
			//원본 이미지
		fileList.forEach(vo -> {
			Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
			checkFilePath.add(path);
		});		
			//썸네일 이미지
		fileList.forEach(vo -> {
			Path path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" +  vo.getUuid() + "_" + vo.getFileName());
			checkFilePath.add(path);
		});
		
		for(int i=0 ; i<checkFilePath.size(); i++) {
			System.out.println("체크파일 리스트 :    " + checkFilePath.get(i));
		}
		
		// 디렉토리 파일 리스트
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		File[] targetFile = targetDir.listFiles();
		
		
		// 삭제 대상 파일 리스트(분류)
		List<File> removeFileList = new ArrayList<File>(Arrays.asList(targetFile));		
		for(File file : targetFile){
			checkFilePath.forEach(checkFile ->{
				if(file.toPath().equals(checkFile)) 
					removeFileList.remove(file);	
			});
		}
		for(int i=0 ; i<removeFileList.size(); i++) {
			System.out.println("삭제 대상 파일 리스트:     " +  removeFileList.get(i));
		}
		
		
		// 삭제 대상 파일 제거
		logger.warn("file Delete : ");
		for(File file : removeFileList) {
			System.out.println("파일 삭제:      " + file);
			file.delete();
		}		
		
		logger.warn("========================================");
		
	}
}
