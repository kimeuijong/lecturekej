package lecture;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import lecture.external.Payment;
import lecture.external.PaymentService;

@Entity
@Table(name = "Class_table")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long courseId;
    private Long fee;
    private String student;
    private String textBook;

    @PostPersist
    public void onPostPersist() throws Exception {
        // Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        Payment payment = new Payment();
        // mappings goes here
        payment.setClassId(this.getId());
        payment.setCourseId(this.getCourseId());
        payment.setFee(this.getFee());
        payment.setStudent(this.getStudent());
        payment.setStatus("PAYMENT_COMPLETED");
        payment.setTextBook(this.getTextBook());

        if (ClassApplication.applicationContext.getBean(PaymentService.class).pay(payment)) {
            ClassRegistered classRegistered = new ClassRegistered();
            BeanUtils.copyProperties(this, classRegistered);
            classRegistered.publishAfterCommit();
        }else {
            throw new RollbackException("Failed during payment");
        }
    }

    @PostUpdate
    public void onPostUpdate() {
        ClassModified classModified = new ClassModified();
        BeanUtils.copyProperties(this, classModified);
        classModified.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        ClassCanceled classCanceled = new ClassCanceled();
        BeanUtils.copyProperties(this, classCanceled);
        classCanceled.publishAfterCommit();
    }

    public String getTextBook() {
        return textBook;
    }

    public void setTextBook(String textBook) {
        this.textBook = textBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

}
