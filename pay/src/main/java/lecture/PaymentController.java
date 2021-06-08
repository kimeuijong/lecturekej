package lecture;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @PostMapping(value = "/succeedPayment")
    public boolean succeedPayment(@RequestBody Map<String, String> param) {

        Payment payment = new Payment();
        boolean result = false;

        payment.setClassId(Long.parseLong(param.get("classId")));
        payment.setCourseId(Long.parseLong(param.get("courseId")));
        payment.setFee(Long.parseLong(param.get("fee")));
        payment.setStudent(param.get("student"));
        payment.setStatus(param.get("status"));
        payment.setTextBook(param.get("textBook"));

        try {
            payment = paymentRepository.save(payment);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping(value = "/payment")
    public Payment registerPayment(@RequestBody Map<String, String> param) {

        Payment payment = new Payment();

        payment.setClassId(Long.parseLong(param.get("classId")));
        payment.setCourseId(Long.parseLong(param.get("courseId")));
        payment.setFee(Long.parseLong(param.get("free")));
        payment.setStudent(param.get("student"));
        payment.setStatus(param.get("status"));

        payment = paymentRepository.save(payment);

        return payment;
    }

    @PatchMapping(value = "/payment/{id}")
    public Payment modifyPayment(@RequestBody Map<String, String> param, @PathVariable String id) {

        Optional<Payment> opt = paymentRepository.findById(Long.parseLong(id));
        Payment payment = null;

        if (opt.isPresent()) {
            payment = opt.get();

            if (param.get("classId") != null)
                payment.setClassId(Long.parseLong(param.get("classId")));
            if (param.get("courseId") != null)
                payment.setCourseId(Long.parseLong(param.get("courseId")));
            if (param.get("fee") != null)
                payment.setFee(Long.parseLong(param.get("fee")));
            if (param.get("student") != null)
                payment.setStudent(param.get("student"));
            if (param.get("status") != null)
                payment.setStatus(param.get("status"));

            payment = paymentRepository.save(payment);
        }

        return payment;
    }

    @PutMapping(value = "/payment/{id}")
    public Payment modifyPaymentPut(@RequestBody Map<String, String> param, @PathVariable String id) {
        return this.modifyPayment(param, id);
    }

    @PatchMapping(value = "/payment/cancel/{classId}")
    public Payment modifyPayment(@PathVariable String classId) {
        Payment retPayment = null;
        List<Payment> paymentList = paymentRepository.findByClassId(Long.parseLong(classId));

        for (Payment payment : paymentList) {
            payment.setStatus("CANCEL");

            retPayment = paymentRepository.save(payment);
        }

        return retPayment;
    }

    @PutMapping(value = "/payment/cancel/{classId}")
    public Payment modifyPaymentPut(@PathVariable String classId) {
        return this.modifyPayment(classId);
    }

    @GetMapping(value = "/payment/{id}")
    public Payment inquiryPaymentById(@PathVariable String id) {

        Optional<Payment> opt = paymentRepository.findById(Long.parseLong(id));
        Payment payment = null;

        if (opt.isPresent())
            payment = opt.get();

        return payment;
    }

    @GetMapping(value = "/payment")
    public Iterable<Payment> inquiryPayment() {

        Iterable<Payment> iter = paymentRepository.findAll();

        return iter;
    }

    @PostMapping(value = "/succeedAdPayment")
    public boolean succeedAdPayment(@RequestBody Map<String, String> param) {

        Payment payment = new Payment();
        boolean result = false;

        payment.setCourseId(Long.parseLong(param.get("courseId")));
        payment.setAdId(Long.parseLong(param.get("adId")));
        payment.setStatus(param.get("status"));
        

        try {
            payment = paymentRepository.save(payment);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
