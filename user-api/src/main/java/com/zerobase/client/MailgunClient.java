package com.zerobase.client;

import com.zerobase.client.mailgun.SendMailForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/*
@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3")
@Qualifier("mailgun")
public interface MailgunClient {

    @PostMapping("sandbox82a5883d85e1480aa7e75c8c2a13c71e.mailgun.org/messages")
    ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);
}
*/
