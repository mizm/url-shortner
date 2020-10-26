package com.ildong.urlshortner.url.ui;

import com.ildong.urlshortner.url.domain.Url;
import com.ildong.urlshortner.url.infra.UrlRepository;
import com.ildong.urlshortner.url.util.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Slf4j
@RestController
public class UrlRestController {

    final private UrlRepository urlRepository;
    final private ModelMapper modelMapper;

    @Autowired
    public UrlRestController(UrlRepository urlRepository, ModelMapper modelMapper) {
        this.urlRepository = urlRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)
    RedirectView url(@PathVariable String id){
        Optional<Url> opBoard = urlRepository.findById(Decoder.decode62(id));
        log.info("######################## decodedID : {}",Decoder.decode62(id));
        log.info("######################## ID : {}",id);
        log.info("######################## URL : {}",opBoard.get().getUrl());
        return opBoard.map(Url -> new RedirectView(Url.getUrl())).orElseGet(() -> new RedirectView("https://www.whodadoc.com/consumer/main"));

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
