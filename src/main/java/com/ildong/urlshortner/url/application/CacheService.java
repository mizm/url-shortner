package com.ildong.urlshortner.url.application;

import com.ildong.urlshortner.url.domain.Log;
import com.ildong.urlshortner.url.domain.LogId;
import com.ildong.urlshortner.url.domain.Url;
import com.ildong.urlshortner.url.infra.LogRepository;
import com.ildong.urlshortner.url.infra.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CacheService implements CacheInterface {

    final private UrlRepository urlRepository;
    final private LogRepository logRepository;

    public CacheService(UrlRepository urlRepository, LogRepository logRepository) {
        this.urlRepository = urlRepository;
        this.logRepository = logRepository;
    }

    @Override
    @Cacheable(key = "#urlId.toString().concat(#ip).concat(#getDtm).concat(#referer)", value = "getLog")
    public Log setLog(long urlId, String ip, String referer, String getDtm) {
//        Optional<Log> opLog = logRepository.findByUrlIdAndGetDtmAndIpAndReferer(urlId,getDtm,ip,referer);
        //log.info("여기는 setLog");
        LogId logId = new LogId(urlId,ip,referer,getDtm);
        Log log = new Log(logId);
        logRepository.save(log);
        return log;
    }
    @Override
    @Cacheable(key = "#id", value = "getShortUrl")
    public Optional<Url> findById(long id) {
        //log.info("여기는 findById");
        Optional<Url> opUrl = urlRepository.findById(id);
        return opUrl;
    }
}
