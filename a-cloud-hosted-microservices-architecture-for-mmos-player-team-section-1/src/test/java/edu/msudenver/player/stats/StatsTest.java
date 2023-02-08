package edu.msudenver.player.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = Stats.class)
class StatsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsRepository statsRepository;

    @MockBean
    protected EntityManager entityManager;

    @SpyBean
    private StatsService statsService;

    @BeforeEach
    public void setup() {
        statsService.entityManager = entityManager;
    }

    @Test
    public void testGetAllPlayerStats() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/stats/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Stats stats = new Stats(4L, 564L, 25);

        Mockito.when(statsRepository.findAll()).thenReturn(Arrays.asList(stats));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("564"));
    }

    @Test
    public void testGetSinglePlayerStats() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/stats/8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Stats stats = new Stats(8L, 123L, 25);

        Mockito.when(statsRepository.findByPlayerId(Mockito.anyLong())).thenReturn(Arrays.asList(stats));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("8"));
    }

    @Test
    public void testStats() {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/stats/8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Stats stats = new Stats(8L, 123L, 25);

        assertEquals(8, stats.getPlayerId());
        assertEquals(123, stats.getMonsterId());
        assertEquals(25, stats.getPoints());

        stats.setPlayerId(1);
        stats.setMonsterId(2);
        stats.setPoints(45);

        assertEquals(1, stats.getPlayerId());
        assertEquals(2, stats.getPlayerId());
        assertEquals(45, stats.getPlayerId());
    }

    @Test
    public void testGetPlayerStatsNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/stats/0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(statsRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testCreateStats() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/stats/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"playerId\":4, \"monsterId\":564, \"points\":25}")
                .contentType(MediaType.APPLICATION_JSON);

        Stats stats = new Stats(4L, 564L, 25);
        Mockito.when(statsRepository.saveAndFlush(Mockito.any())).thenReturn(stats);
        Mockito.when(statsRepository.save(Mockito.any())).thenReturn(stats);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("564"));
    }

    @Test
    public void testCreateStatsBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/stats/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"playerId\":4}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(statsRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(statsRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testDeleteStats() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/stats/4")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Stats stats = new Stats(4L, 564L, 25);
        Mockito.when(statsRepository.findById(Mockito.any())).thenReturn(Optional.of(stats));
        Mockito.when(statsRepository.existsById(Mockito.any())).thenReturn(true);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void testDeleteStatsNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/stats/4")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(statsRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(statsRepository.existsById(Mockito.any())).thenReturn(false);
        Mockito.doThrow(IllegalArgumentException.class)
                .when(statsRepository)
                .deleteById(Mockito.any());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }
}
