package com.ildong.urlshortner.url.ui;

import com.ildong.urlshortner.url.application.UrlService;
import com.ildong.urlshortner.url.domain.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
public class UrlRestController {

    final private UrlService urlService;

    @Autowired
    public UrlRestController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)
    RedirectView url(@PathVariable String id, HttpServletRequest request){
        Optional<Url> opUrl = urlService.getShortUrl(id,request);
        return opUrl.map(Url -> new RedirectView(Url.getUrl())).orElseGet(() -> new RedirectView("https://www.whodadoc.com/consumer/main"));

    }

    @GetMapping(value = "/")
    @ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)
    RedirectView board() {
         return new RedirectView("https://www.whodadoc.com/consumer/main");
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity badRequestExceptionHandler(BadRequestException e)
    {
        ErrorResponse errorResponse = new ErrorResponse("잘못된 요청", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
