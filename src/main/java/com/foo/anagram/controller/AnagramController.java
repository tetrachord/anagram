package com.foo.anagram.controller;

import com.foo.anagram.service.AnagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/")
public class AnagramController {

    @Autowired
    private AnagramService anagramService;

    @RequestMapping(value = "/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<String>> getAnagram(@PathVariable String word) {

        Map<String, List<String>> result = new HashMap<>();

        String[] words = word.split(",");

        for (String str : words) {

            List<String> anagrams = anagramService.findAnagramsFor(str);

            result.put(str, anagrams);
        }

        return result;
    }
}
