package com.quiz.quizapp.service;

import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "success";
    }

    public String editQuestion(Integer id, Question question) {
        Question editQuestion = questionDao.getReferenceById(id);
        editQuestion.setQuestionText(question.getQuestionText());
        editQuestion.setCategory(question.getCategory());
        editQuestion.setDifficulty(question.getDifficulty());
        editQuestion.setOption1(question.getOption1());
        editQuestion.setOption2(question.getOption2());
        editQuestion.setOption3(question.getOption3());
        editQuestion.setOption4(question.getOption4());
        questionDao.save(editQuestion);
        return "success, question " + editQuestion.getId() + " has been successfully altered";
    }

    public String deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return "Question " + id + " has been deleted";
    }
}
