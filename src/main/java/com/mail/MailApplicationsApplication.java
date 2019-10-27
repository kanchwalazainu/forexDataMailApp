package com.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MailApplicationsApplication {

	/*
	 * static RestTemplate restTemplate;
	 * 
	 * static String apiURL=
	 * "https://eodhistoricaldata.com/api/real-time/AUDUSD.FOREX?api_token=5dae818f0088a4.65981706&fmt=json&filter=close";
	 */
	/*
	 * public MailApplicationsApplication() { restTemplate = new RestTemplate(); }
	 */

	public static void main(String[] args) {
		SpringApplication.run(MailApplicationsApplication.class, args);
		/*
		 * try { MailAttachment data=getEntity(); System.out.println(data.getClose()); }
		 * catch(Exception e) { e.printStackTrace(); }
		 */
	}

	

	/*
	 * public static MailAttachment getEntity() throws Exception {
	 * System.out.println(apiURL); MailAttachment data = new MailAttachment();
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity<String> entity
	 * = new HttpEntity<String>(headers); data = restTemplate.getForObject(apiURL,
	 * MailAttachment.class, entity); System.out.println(data.toString()); return
	 * data; }
	 */

}
