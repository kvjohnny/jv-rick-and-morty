package mate.academy.rickandmorty.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CharacterResponseDataDto {
    private InfoDto info;
    private List<CharacterDto> results;
}
