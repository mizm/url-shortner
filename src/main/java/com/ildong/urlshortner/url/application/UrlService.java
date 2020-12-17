package com.ildong.urlshortner.url.application;

import com.ildong.urlshortner.url.domain.Log;
import com.ildong.urlshortner.url.domain.Url;
import com.ildong.urlshortner.url.util.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UrlService implements UrlInterface {

    final private CacheInterface cacheInterface;

    public UrlService(CacheInterface cacheInterface) {
        this.cacheInterface = cacheInterface;
    }


    public Optional<Url> getShortUrl(String id, HttpServletRequest request) {
        long urlId = Decoder.decode62(id);
        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        String getDtm = transFormat.format(from);
        String referer = request.getHeader("referer");
        if(referer == null) {
            referer = "null";
        }

        log.info("{}",request.getRemoteHost());
        log.info("{}",request.getHeader("referer"));
        log.info("######################## decodedID : {}", urlId);
        log.info("######################## ID : {}",id);

        // log 관련
        Log log = cacheInterface.setLog(urlId,request.getRemoteHost(),referer,getDtm);

        // 결과 반환
        return cacheInterface.findById(urlId);
    }

}
