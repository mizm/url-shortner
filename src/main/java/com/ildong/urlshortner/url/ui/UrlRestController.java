package com.ildong.urlshortner.url.ui;

import com.ildong.urlshortner.url.domain.Url;
import com.ildong.urlshortner.url.infra.UrlRepository;
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
    private String Base62String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private int Base62 = 62;

    private String Encode62(String temp) {
        int id;
        try {
            id = Integer.parseInt(temp);

            StringBuilder sb = new StringBuilder();
        while(id > 0) {
            sb.append(Base62String.charAt((int) (id % Base62)));
            id /= Base62;
        }
        for(int i = sb.length(); i < 6; i++){
            sb.append("a");
        }
        return sb.toString();
        } catch(Exception e) {
            throw new BadRequestException("잘못된 요청입니다.");
        }
    }

    private int Decode62(String temp) {
        int re = 0;
        int power = 1;
//        for (int i = temp.length()-1; i >= 0; i--) {
        for(int i = 0; i < temp.length(); i++) {
            re += Base62String.indexOf(temp.charAt(i)) * power;
            power *= Base62;
        }
        return re;
    }
    @Autowired
    public UrlRestController(UrlRepository urlRepository, ModelMapper modelMapper) {
        this.urlRepository = urlRepository;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)
    RedirectView url(@PathVariable String id){
        Optional<Url> opBoard = urlRepository.findById(Decode62(id));
        log.info("######################## decodedID : {}",Decode62(id));
        log.info("######################## ID : {}",id);
        log.info("######################## URL : {}",opBoard.get().getUrl());
        return opBoard.map(Url -> new RedirectView(Url.getUrl())).orElseGet(() -> new RedirectView("https://www.whodadoc.com/consumer/main"));

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
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
