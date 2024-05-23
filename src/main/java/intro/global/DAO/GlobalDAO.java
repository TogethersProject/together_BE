package intro.global.DAO;

import intro.global.bean.GlobalIntroDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalDAO extends JpaRepository<GlobalIntroDTO, Long> {
}
