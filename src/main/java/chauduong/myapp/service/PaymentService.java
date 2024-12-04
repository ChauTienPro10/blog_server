package chauduong.myapp.service;

import chauduong.myapp.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

}
