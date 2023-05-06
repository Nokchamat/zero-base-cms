package com.zerobase.cms.user.client.mailgun;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Data
public class SendMailForm {

    private String from;
    private String to;
    private String subject;
    private String text;

}
