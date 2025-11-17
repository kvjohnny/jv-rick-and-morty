package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.exception.DataProcessingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharactersClient {
    private static final String BASE_URL =
            "https://rickandmortyapi.com/api/character?page=%s";
    private static final int AMOUNT_OF_PAGES = 42;
    private final ObjectMapper objectMapper;

    public List<CharacterDto> getAllCharacters() {
        List<CharacterDto> characterDtos = new ArrayList<>();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            for (int i = 1; i <= AMOUNT_OF_PAGES; i++) {
                String url = BASE_URL.formatted(i);
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = httpClient.send(
                        httpRequest, HttpResponse.BodyHandlers.ofString());
                CharacterResponseDataDto responseDataDto = objectMapper.readValue(
                        response.body(), CharacterResponseDataDto.class);
                List<CharacterDto> currentCharacterDtos = responseDataDto
                        .getResults()
                        .stream()
                        .toList();
                characterDtos.addAll(currentCharacterDtos);
            }
            return characterDtos;
        } catch (IOException | InterruptedException e) {
            throw new DataProcessingException("Can't get all characters", e);
        }
    }
}
