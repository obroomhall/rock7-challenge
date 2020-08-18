package com.rock7.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rock7.challenge.model.Positions;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Positions positions = objectMapper.readValue(new File("src/main/resources/positions.json"), Positions.class);
        System.out.println(positions);
    }
}
