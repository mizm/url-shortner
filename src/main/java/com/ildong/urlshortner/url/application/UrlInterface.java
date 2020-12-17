package com.ildong.urlshortner.url.application;

import com.ildong.urlshortner.url.domain.Url;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UrlInterface {
    Optional<Url> getShortUrl(String id, HttpServletRequest request);
}
