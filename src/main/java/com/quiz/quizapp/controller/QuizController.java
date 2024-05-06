package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.model.QuestionDTO;
import com.quiz.quizapp.model.Quiz;
import com.quiz.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam int numQ, @RequestParam String title, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(quizService.createQuiz(numQ, title), HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(@PathVariable Integer id) {
        List<QuestionDTO> questionList = quizService.getQuizQuestions(id);
        if (questionList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

}
