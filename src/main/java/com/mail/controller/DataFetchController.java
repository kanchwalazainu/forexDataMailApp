
package com.mail.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mail.model.APIDetails;
import com.mail.model.DataDetails;
import com.mail.model.ForexCode;
import com.mail.model.MailAttachment;
import com.mail.repository.APIRepository;
import com.mail.repository.DataRepository;
import com.mail.repository.ForexcodeRepository;
import com.opencsv.CSVWriter;

@Component
public class DataFetchController {

	@Autowired
	private ForexcodeRepository forexRepository;

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private APIRepository apiRepository;

	@Autowired
	private JavaMailSender sender;

	private static final Logger log = LoggerFactory.getLogger(DataFetchController.class);

	/*
	 * // set this to false to disable this job; set it it true by
	 * 
	 * @Value("${example.scheduledJob.enabled:false}") private boolean
	 * scheduledJobEnabled;
	 */

	@Value("${data.dir}")
	private String dataDir;

	@Value("${endpoint}")
	private String apiURL;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd_HHmm");

	@Scheduled(cron = "${cron.expression}")  
	
	public void pulldata() throws MessagingException {
		
		String schedulerTime=dateFormat.format(new Date());

		RestTemplate restTemplate = new RestTemplate();
		ArrayList<String[]> csvdata = new ArrayList<String[]>();
		csvdata.add(new String[] { "FOREX", "VALUE" });
		// Fetching the API token and Email Addresses from the database
		APIDetails apidtls = getAPIToken();
		String apiToken = apidtls.getApi_token();
		String emailAddress = apidtls.getEmailid();

		// Fetching the forex codes from the database
		List<ForexCode> forexCodes = getForexCodes();
		String dataforDB="";
		for (ForexCode forexCode : forexCodes) {
			String code = forexCode.getCode();
			String restURL = apiURL + code + ".FOREX?api_token=" + apiToken + "&fmt=json&filter=close";
			log.info("RESTURL::" + restURL);
			try {
				 MailAttachment data = restTemplate.getForObject(restURL, MailAttachment.class);
				 log.info("Pulled data "+data.getClose()+" at " + schedulerTime);

				csvdata.add(new String[] {code, Double.toString(data.getClose())});
				dataforDB+="{"+code+","+Double.toString(data.getClose())+"}";
				
			} catch (Exception e) {
				sendErrorMail(emailAddress, e.getMessage(),schedulerTime);
				throw e;
			}

		}
		
		saveData(dataforDB,schedulerTime);

		String fileLocation = "";
		try {
			fileLocation = writeDatatoFile(csvdata,schedulerTime);
		} catch (IOException e) {
			sendErrorMail(emailAddress, e.getMessage(), schedulerTime);
			e.printStackTrace();
		}
		if (fileLocation != "") {

			sendMailAttachment(emailAddress, fileLocation,schedulerTime);
			deleteFile(fileLocation);

		}

	}

	private List<ForexCode> getForexCodes() {

		List<ForexCode> codes = new ArrayList<ForexCode>();

		forexRepository.findAll().forEach(codes::add);
		return codes;

	}

	private APIDetails getAPIToken() {

		return apiRepository.findAll().iterator().next();

	}

	private String writeDatatoFile(ArrayList<String[]> csvdata, String schedulerTime) throws IOException {

		File dir = new File(dataDir);
		if (!dir.exists()) {
			try {
				dir.mkdir();
			} catch (SecurityException se) {
				throw se;
			}

		}

		String name = "obsval_" + schedulerTime + ".csv";
		String fileLocation = dir.getAbsolutePath() + File.separator + name;

		log.info("Writing to " + fileLocation);
		CSVWriter writer = new CSVWriter(new FileWriter(fileLocation));
		writer.writeAll(csvdata);
		System.out.println("CSV File written successfully All at a time");
		writer.close();

		return fileLocation;

	}

	private String sendMailAttachment(String emailAddress, String fileLocation, String schedulerTime) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		log.info("SendingMail");
		try {
			helper.setTo(InternetAddress.parse(emailAddress));
			helper.setText("Please find the attached docuemnt of Forex code for your reference.");
			helper.setSubject("Forex data for " + schedulerTime);
			FileSystemResource file = new FileSystemResource(fileLocation);
			helper.addAttachment(file.getFilename(), file);
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Error while sending mail ..");
			return "Error while sending mail ..";
		}
		sender.send(message);
		log.info("Mail sent success");
		return "Mail Sent Success!";
	}

	private String sendErrorMail(String emailAddress, String exception, String schedulerTime) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		try {
			helper.setTo(InternetAddress.parse(emailAddress));
			helper.setText("The following exception occured while fetching Forex data::" + exception);
			helper.setSubject("Error Forex data for " + schedulerTime);

		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

	private void deleteFile(String filePath) {
		try {

			File file = new File(filePath);

			if (file.delete()) {
				log.info(file.getName() + " is deleted!");
			} else {
				log.info("Delete operation is failed.");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	
	 private void saveData(String dataforDB, String schedulerTime) { 
		 DataDetails data=new DataDetails(schedulerTime,dataforDB);
		 dataRepository.save(data);

	 }

}
