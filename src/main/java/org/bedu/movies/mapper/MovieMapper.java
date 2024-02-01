package org.bedu.movies.mapper;

import org.bedu.movies.dto.CreateMovieDTO;
import org.bedu.movies.dto.MovieDTO;
import org.bedu.movies.dto.UpdateMovieDTO;
import org.bedu.movies.model.Movie;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {
  
  MovieDTO toDTO(Movie model);

  List<MovieDTO> toDTO(List<Movie> model);

  @Mapping(target = "id", ignore = true)
  Movie toModel(CreateMovieDTO dto);

  void updateModel(UpdateMovieDTO dto, @MappingTarget Movie model);
}
