package lecture;

import lecture.config.kafka.KafkaProcessor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler {
    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverClassCanceled_CancelPayment(@Payload ClassCanceled classCanceled) {

        if (classCanceled.isMe()) {
            List<Payment> paymentList = paymentRepository.findByClassId(classCanceled.getId());

            for (Payment payment : paymentList) {
                payment.setStatus("CANCEL");
                paymentRepository.save(payment);
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverAdCanceled_CancelPayment(@Payload AdCanceled adCanceled){

       // if(!adCanceled.validate()) return;

        //System.out.println("\n\n##### listener CancelPayment : " + adCanceled.toJson() + "\n\n");

        // Sample Logic //
        //Payment payment = new Payment();
        //paymentRepository.save(payment);
        if (adCanceled.isMe()) {
            List<Payment> paymentList = paymentRepository.findByAdId(adCanceled.getId());

            for (Payment payment : paymentList) {
                payment.setStatus("CANCEL");
                paymentRepository.save(payment);
            }
        }     
    }
}
