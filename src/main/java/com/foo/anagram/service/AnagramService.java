package com.foo.anagram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class AnagramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnagramService.class);

//    private List<String> wordList;

    private Map<String, List<String>> anagramsMap;


    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {

        Resource resource = resourceLoader.getResource("classpath:wordlist.txt");
        try (InputStream inputStream = resource.getInputStream()) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            List<String> wordList = bufferedReader.lines().collect(Collectors.toList());

            anagramsMap = wordList.stream().collect(groupingBy(w -> sort(w) ));

        } catch (IOException e) {
            LOGGER.error("Could not read wordlist.txt");
        }
    }


    public List<String> findAnagramsFor(String word) {

        String sortedWord = sort(word);

        List<String> anagrams = anagramsMap.get(sortedWord);

        if (anagrams == null) {
            return new ArrayList<String>();
        }

        return anagrams.stream()
                .filter(item -> !item.equals(word))
                .collect(toList());
    }

    private String sort(String str){

        String lowerCaseWord = str.toLowerCase();
        char[] letters = str.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
