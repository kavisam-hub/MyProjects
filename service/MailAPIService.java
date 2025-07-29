package com.dtvs.dtvsonline.service;
 

public interface MailAPIService {
	//send mail
	public void ReadyToSendEmail(String toAddress, String fromAddress, String subject, String msgBody); 
}