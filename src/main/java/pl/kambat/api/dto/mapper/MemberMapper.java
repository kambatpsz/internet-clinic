package pl.kambat.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kambat.api.dto.MemberDTO;
import pl.kambat.domain.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member map(final MemberDTO memberDTO);
}
