package com.together.board.DAO;

import com.together.board.bean.ImageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDAO extends JpaRepository<ImageDTO, Integer> {
}
