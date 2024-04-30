
package uz.pdp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Ordering;

public interface OrderingRepository extends JpaRepository<Ordering,Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);

}

