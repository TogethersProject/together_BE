package com.together.board.DAO;

import com.together.board.bean.BoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface BoardDAO extends JpaRepository<BoardDTO, BigInteger> {
}
