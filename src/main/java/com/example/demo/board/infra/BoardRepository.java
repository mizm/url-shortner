package com.example.demo.board.infra;

import com.example.demo.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
   //Camel case로 알아서 맞추어서 할 수 있음.
//   Optional<Board> findByDeletedAndId(boolean deleted, int id);
}
