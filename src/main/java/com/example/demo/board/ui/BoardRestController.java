package com.example.demo.board.ui;

import com.example.demo.board.domain.Board;
import com.example.demo.board.domain.BoardDto;
import com.example.demo.board.infra.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
public class BoardRestController {

    final private BoardRepository boardRepository;
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
    public BoardRestController(BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)
    RedirectView board(@PathVariable String id){
        Optional<Board> opBoard = boardRepository.findById(Decode62(id));
        if(opBoard.isPresent()) {
            return new RedirectView(opBoard.get().getUrl());
        }
        else return new RedirectView("https://www.whodadoc.com/consumer/main");

    }

    @GetMapping("/boards")
    ResponseEntity boards(@PageableDefault(size = 3,sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable){
        //Pageable pageable = PageRequest.of(page,3, Sort.by("id").descending());
        Page<Board> boards = boardRepository.findAll(pageable);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @PostMapping("/board")
    ResponseEntity createBoard(@Valid @RequestBody Board board, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(FieldError e : bindingResult.getFieldErrors()){
                sb.append("[");
                sb.append(e.getField());
                sb.append("](은)는 ");
                sb.append(e.getDefaultMessage());
                sb.append(" 입력 된 변수는 [");
                sb.append(e.getRejectedValue());
                sb.append("] 입니다.\n");
            }
//            for(ErrorO  : bindingResult.getAllErrors())
            throw new BadRequestException(sb.toString());
        }
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.CREATED);
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity badRequestExceptionHandler(BadRequestException e)
    {
        ErrorResponse errorResponse = new ErrorResponse("잘못된 요청", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
