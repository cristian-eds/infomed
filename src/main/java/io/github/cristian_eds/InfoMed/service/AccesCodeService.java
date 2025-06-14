package io.github.cristian_eds.InfoMed.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccesCodeService {

    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SIZE_CODE = 6;

    public String generateCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(SIZE_CODE);
        for (int i = 0; i < SIZE_CODE; i++) {
            int indexToChar = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            stringBuilder.append(ALPHANUMERIC_CHARACTERS.charAt(indexToChar));
        }
        return stringBuilder.toString();
    }
}
