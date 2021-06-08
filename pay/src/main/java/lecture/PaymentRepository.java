package lecture;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long> {

    List<Payment> findByClassId(Long classId);
    List<Payment> findByAdId(Long adId);
}