package com.example.philzpayment;

import com.example.philzpayment.PaymentsCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderStatus {
    String order_num;
    String status;
    private String message;
}
