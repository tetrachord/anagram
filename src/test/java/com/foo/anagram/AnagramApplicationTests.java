package com.foo.anagram;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnagramApplicationTests {

    @Autowired
    private MockMvc mvc;

	@Test
	public void shouldGenerateAnagramForSingleWord() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/crepitus")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.crepitus", contains("cuprites","pictures","piecrust")));
	}

    @Test
    public void shouldGenerateAnagramForMultipleWords() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/crepitus,paste,kinship,enlist,boaster,fresher,sinks,knits,sort")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crepitus", contains("cuprites","pictures","piecrust")))
                .andExpect(jsonPath("$.paste", contains("pates","peats","septa","spate","tapes","tepas")))
                .andExpect(jsonPath("$.kinship", contains("pinkish")))
                .andExpect(jsonPath("$.enlist", contains("elints","inlets","listen","silent","tinsel")))
                .andExpect(jsonPath("$.boaster", contains("Barotse","boaters","borates","rebatos","sorbate")))
                .andExpect(jsonPath("$.fresher", contains("refresh")))
                .andExpect(jsonPath("$.sinks", contains("skins")))
                .andExpect(jsonPath("$.knits", contains("skint","stink","tinks")))
                .andExpect(jsonPath("$.sort", contains("orts","rots","stor","tors")));
    }

    @Test
    public void shouldReturnEmptyResultForNonExistentWord() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/sdfwehrtgegfg")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sdfwehrtgegfg", hasSize(0)));
    }
}
