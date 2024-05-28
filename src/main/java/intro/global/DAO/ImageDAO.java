package intro.global.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import intro.global.bean.ImageDTO;
import java.math.BigInteger;

@Repository
public interface ImageDAO extends JpaRepository<ImageDTO, Integer> {
}
