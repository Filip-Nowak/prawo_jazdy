package org.example.prawo_jazdy.entity;

import lombok.Builder;
import org.apache.tomcat.util.json.JSONParser;


public interface Question {
    String getQuestion();
    int getNumber();
    int getCorrectAnswer();

}
