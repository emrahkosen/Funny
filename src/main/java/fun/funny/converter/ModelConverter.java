package fun.funny.converter;

import org.springframework.stereotype.Component;

@Component
public interface ModelConverter <E, D>{
    D entity2Dto(E e);
    E createDto2Entity(D dto);
    void updateDto2Entity(D dto, E entity);
}
