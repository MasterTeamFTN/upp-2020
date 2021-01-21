package rs.ac.uns.ftn.uppservice.common.mapper;

import org.springframework.stereotype.Component;

@Component
public interface CustomMapper<T, V> {
    V entityToDto(T t);
}
