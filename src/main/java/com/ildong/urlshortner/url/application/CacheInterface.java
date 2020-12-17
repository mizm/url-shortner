package com.ildong.urlshortner.url.application;

import com.ildong.urlshortner.url.domain.Log;
import com.ildong.urlshortner.url.domain.Url;

import java.util.Optional;

public interface CacheInterface {
    Log setLog(long urlId, String ip, String referer, String getDtm);
    Optional<Url> findById(long id);
}
