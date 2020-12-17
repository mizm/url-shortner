package com.ildong.urlshortner.url.infra;

import com.ildong.urlshortner.url.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
   //Camel case로 알아서 맞추어서 할 수 있음.
   //Optional<Log> findByUrlIdAndGetDtmAndIpAndReferer(long urlId, String getDtm, String ip, String referer);
}
