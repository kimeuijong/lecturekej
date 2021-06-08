package lecture;

import lecture.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryMypageViewHandler {

    @Autowired
    private InquiryMypageRepository inquiryMypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentApproved_then_CREATE_1(@Payload PaymentApproved paymentApproved) {
        try {
            if (paymentApproved.isMe()) {
                // view 객체 생성
                InquiryMypage inquiryMypage = new InquiryMypage();
                // view 객체에 이벤트의 Value 를 set 함
                inquiryMypage.setClassId(paymentApproved.getClassId());
                inquiryMypage.setPaymentId(paymentApproved.getId());
                inquiryMypage.setCourseId(paymentApproved.getCourseId());
                inquiryMypage.setFee(paymentApproved.getFee());
                inquiryMypage.setStudent(paymentApproved.getStudent());
                inquiryMypage.setPaymentStatus(paymentApproved.getStatus());
                inquiryMypage.setTextBook(paymentApproved.getTextBook());
                inquiryMypage.setStatus("CLASS_START");
                // view 레파지 토리에 save
                inquiryMypageRepository.save(inquiryMypage);
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenClassModified_then_UPDATE_1(@Payload ClassModified classModified) {
        try {
            if (classModified.isMe()) {
                // view 객체 조회
                List<InquiryMypage> inquiryMypageList = inquiryMypageRepository.findByClassId(classModified.getId());
                for (InquiryMypage inquiryMypage : inquiryMypageList) {
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    inquiryMypage.setCourseId(classModified.getCourseId());
                    inquiryMypage.setFee(classModified.getFee());
                    inquiryMypage.setStudent(classModified.getStudent());
                    // view 레파지 토리에 save
                    inquiryMypageRepository.save(inquiryMypage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTextbookDeliveried_then_UPDATE_2(@Payload TextbookDeliveried textbookDeliveried) {
        try {
            if (textbookDeliveried.isMe()) {
                // view 객체 조회
                List<InquiryMypage> inquiryMypageList = inquiryMypageRepository
                        .findByPaymentId(textbookDeliveried.getPaymentId());
                for (InquiryMypage inquiryMypage : inquiryMypageList) {
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    inquiryMypage.setDeliveryStatus(textbookDeliveried.getStatus());
                    // view 레파지 토리에 save
                    inquiryMypageRepository.save(inquiryMypage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenClassCanceled_then_UPDATE_3(@Payload ClassCanceled classCanceled) {
        try {
            if (classCanceled.isMe()) {
                // view 객체 조회
                List<InquiryMypage> inquiryMypageList = inquiryMypageRepository.findByClassId(classCanceled.getId());
                for (InquiryMypage inquiryMypage : inquiryMypageList) {
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    inquiryMypage.setDeliveryStatus("DELIVERY_CANCELED");
                    inquiryMypage.setPaymentStatus("PAYMENT_CANCELED");;
                    inquiryMypage.setStatus("CLASS_CANCELED");

                    // view 레파지 토리에 save
                    inquiryMypageRepository.save(inquiryMypage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}