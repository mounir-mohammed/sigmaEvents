package ma.sig.events.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import ma.sig.events.domain.Area;
import ma.sig.events.domain.Category;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.Fonction;
import ma.sig.events.service.dto.AreaDTO;
import ma.sig.events.service.dto.CategoryDTO;
import ma.sig.events.service.dto.EventDTO;
import ma.sig.events.service.dto.FonctionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fonction} and its DTO {@link FonctionDTO}.
 */
@Mapper(componentModel = "spring")
public interface FonctionMapper extends EntityMapper<FonctionDTO, Fonction> {
    @Mapping(target = "areas", source = "areas", qualifiedByName = "areaAreaIdSet")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryCategoryId")
    @Mapping(target = "event", source = "event", qualifiedByName = "eventEventId")
    FonctionDTO toDto(Fonction s);

    @Mapping(target = "removeArea", ignore = true)
    Fonction toEntity(FonctionDTO fonctionDTO);

    @Named("areaAreaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "areaId", source = "areaId")
    AreaDTO toDtoAreaAreaId(Area area);

    @Named("areaAreaIdSet")
    default Set<AreaDTO> toDtoAreaAreaIdSet(Set<Area> area) {
        return area.stream().map(this::toDtoAreaAreaId).collect(Collectors.toSet());
    }

    @Named("categoryCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "categoryId", source = "categoryId")
    CategoryDTO toDtoCategoryCategoryId(Category category);

    @Named("eventEventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "eventId", source = "eventId")
    EventDTO toDtoEventEventId(Event event);
}
