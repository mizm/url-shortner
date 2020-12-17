package com.ildong.urlshortner.url.infra;

import com.ildong.urlshortner.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
   //Camel case로 알아서 맞추어서 할 수 있음.
//   Optional<Board> findByDeletedAndId(boolean deleted, int id);
}
