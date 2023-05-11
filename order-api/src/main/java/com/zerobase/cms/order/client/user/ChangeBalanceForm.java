package com.zerobase.cms.order.client.user;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeBalanceForm {

    private String from;
    private String message;
    private Integer money;

}
