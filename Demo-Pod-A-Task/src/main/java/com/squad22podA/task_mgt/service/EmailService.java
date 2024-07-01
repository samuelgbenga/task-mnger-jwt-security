package com.squad22podA.task_mgt.service;

import com.squad22podA.task_mgt.payload.request.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}
