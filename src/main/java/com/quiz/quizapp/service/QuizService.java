package com.quiz.quizapp.service;

import com.quiz.quizapp.dao.QuestionDao;
import com.quiz.quizapp.dao.QuizDao;
import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.model.QuestionDTO;
import com.quiz.quizapp.model.Quiz;
import com.quiz.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public String createQuiz(int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestions(numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return "success";
    }

    public List<QuestionDTO> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isEmpty()) {
            return null;
        }
        List<Question> quizQuestions = quiz.get().getQuestions();
        List<QuestionDTO> questionsForUser = new ArrayList<>();
        for (Question q : quizQuestions) {
            QuestionDTO qD = new QuestionDTO(q.getId(), q.getQuestionText(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qD);
        }
        return questionsForUser;
    }

    public Integer calculateScore(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int correct = 0;
        int i = 0;
        for (Response response : responses) {
            if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
                correct++;
            }
            i++;
        }
        return correct;
    }
}
