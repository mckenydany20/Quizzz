package com.example.easyquiz;

public class Question {
    private String question;
    private String difficulty;

    public Question(String question, String difficulty) {
        this.question = question;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        return question != null ? question.equals(question1.question) : question1.question == null;
    }

    @Override
    public int hashCode() {
        return question != null ? question.hashCode() : 0;
    }
}