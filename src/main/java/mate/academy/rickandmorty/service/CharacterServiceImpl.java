package mate.academy.rickandmorty.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterDto;
import mate.academy.rickandmorty.exception.DataProcessingException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharactersClient client;
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public Character getRandomCharacter() {
        return characterRepository.findById(new Random()
                        .nextLong(1, characterRepository.count() + 1))
                .orElseThrow(() -> new DataProcessingException("Can't get random character"));
    }

    @Override
    public List<Character> findAllCharactersByName(String name) {
        return characterRepository.findAllByNameContainingIgnoreCase(name);
    }

    @PostConstruct
    public void saveCharacters() {
        List<CharacterDto> characterDtos = client.getAllCharacters();
        List<Character> characters = characterDtos.stream()
                .map(characterMapper::toModel)
                .toList();
        characterRepository.saveAll(characters);
    }
}
