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
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.dto.CharacterResponseDataDto;
import mate.academy.rickandmorty.exception.DataProcessingException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharactersClient {
    private static final String BASE_URL =
            "https://rickandmortyapi.com/api/character?page";
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<CharacterDto> getAllCharacters() {
        List<CharacterDto> characterDtos = new ArrayList<>();
        String url = BASE_URL;
        try {
            while (url != null) {
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
                url = responseDataDto.getInfo().next();
            }
            return characterDtos;
        } catch (IOException | InterruptedException e) {
            throw new DataProcessingException("Can't get all characters", e);
        }
    }
}
