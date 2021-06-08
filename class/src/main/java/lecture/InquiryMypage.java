package lecture;

import javax.persistence.*;

@Entity
@Table(name="InquiryMypage_table")
public class InquiryMypage {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long courseId;
        private Long classId;
        private Long fee;
        private String textBook;
        private String student;
        private String paymentStatus;
        private String deliveryStatus;
        private Long paymentId;
        private String status;


        public Long getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
        public Long getClassId() {
            return classId;
        }

        public void setClassId(Long classId) {
            this.classId = classId;
        }
        public Long getFee() {
            return fee;
        }

        public void setFee(Long fee) {
            this.fee = fee;
        }
        public String getTextBook() {
            return textBook;
        }

        public void setTextBook(String textBook) {
            this.textBook = textBook;
        }
        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }
        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }
        public String getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }
        public Long getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
        }

}
