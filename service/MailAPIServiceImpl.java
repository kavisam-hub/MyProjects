package com.dtvs.dtvsonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
 


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;



@Service
public class MailAPIServiceImpl implements MailAPIService
   {
   
	@Autowired
	private JavaMailSender mailSender; // MailSender interface defines a strategy
										// for sending simple mails
	
	@Override
	public void ReadyToSendEmail(final String toAddress, final String fromAddress,
			final String subject, final String msgBody) {
		
		
//		SimpleMailMessage mailMsg = new SimpleMailMessage();
//		mailMsg.setFrom(fromAddress);
//		mailMsg.setTo(toAddress);
//		mailMsg.setSubject(subject);
//		mailMsg.setText(msgBody);
//		mailMsg.setContent("<html><body><img src='cid:identifier1234'></body></html>","text/html");
//        mailSender.send(mailMsg);

		 
//        MimeMessagePreparator preparator = new MimeMessagePreparator() {
//        
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//            	
//            	
//            	MimeMessageHelper messageHelper = new MimeMessageHelper(
//            			mimeMessage, true);
//            			messageHelper.setTo(toAddress);
//            			messageHelper.setSubject(subject);
//            			messageHelper.setFrom("donotreply@delphitvs.com");;
//            			messageHelper.setText(msgBody,true);
//            			// determines if there is an upload file, attach it to the e-mail
//          
//            }
//        };
//		
//        try {
//            this.mailsender.send(preparator);
//        }
//        catch (MailException ex) {
//            // simply log it and go on...
//            System.err.println(ex.getMessage());            
//        }
		
		//MimeMessage message = sender.createMimeMessage();
		mailSender.send(new MimeMessagePreparator() {
			
			   public void prepare(MimeMessage mimeMessage) throws MessagingException {
			     MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			     message.setFrom(fromAddress);
			     message.setTo(toAddress);
			     message.setSubject(subject);			   
			     message.setText(msgBody,true);			     
			  //   message.setCc(fromAddress);
			     message.setBcc(fromAddress);
			     message.setPriority(1);
			     
			     //  message.setContent("<html><body><img src='cid:identifier1234'></body></html>","text/html");
			    // message.addInline("myLogo", new ClassPathResource("img/mylogo.gif"));
			   //  message.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));
			   }
			 });	
		
	}
	
	
	//send mail

}