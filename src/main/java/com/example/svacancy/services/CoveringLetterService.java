package com.example.svacancy.services;


import com.example.svacancy.Model.CoveringLetter;
import com.example.svacancy.repos.CoveringLetterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoveringLetterService {

    private final CoveringLetterRepo coveringLetterRepo;

    public List<CoveringLetter> getAllLetters(){
        return coveringLetterRepo.findAll();
    }

    public void saveLetter(CoveringLetter coveringLetter) {
        coveringLetterRepo.save(coveringLetter);
    }
}
