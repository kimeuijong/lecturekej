package lecture;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InquiryMypageRepository extends CrudRepository<InquiryMypage, Long> {

    List<InquiryMypage> findByClassId(Long classId);

    List<InquiryMypage> findByPaymentId(Long paymentId);
}