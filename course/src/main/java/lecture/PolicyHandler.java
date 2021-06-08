package lecture;

import lecture.config.kafka.KafkaProcessor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    CourseRepository courseRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_DeliveryTextbook(@Payload PaymentApproved paymentApproved) {

        if (paymentApproved.isMe()) {

            Delivery delivery = new Delivery();
            delivery.setClassId(paymentApproved.getClassId());
            delivery.setCourseId(paymentApproved.getCourseId());
            delivery.setStudent(paymentApproved.getStudent());
            delivery.setPaymentId(paymentApproved.getId());
            delivery.setTextBook(paymentApproved.getTextBook());
            delivery.setStatus("DELIVERY_START");

            Optional<Course> opt = courseRepository.findById(paymentApproved.getClassId());

            Course course;
            if (opt.isPresent()) {
                course = opt.get();
                delivery.setTextBook(course.getTextBook());
            }
            deliveryRepository.save(delivery);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCanceled_CancelTextbook(@Payload PaymentCanceled paymentCanceled) {

        if (paymentCanceled.isMe()) {
            List<Delivery> deliveryList = deliveryRepository.findByClassId(paymentCanceled.getClassId());

            for (Delivery delivery:deliveryList)
            {
                delivery.setStatus("DELIVERY_CANCEL");
                deliveryRepository.save(delivery);
            }

        }
    }

}
