package mate.academy.rickandmorty.dto.external;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CharacterResponseDataDto {
    private List<CharacterDto> results;
}
