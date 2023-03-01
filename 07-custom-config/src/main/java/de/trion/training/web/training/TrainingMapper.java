package de.trion.training.web.training;

import de.trion.training.common.training.Training;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

   Training fromDto(TrainingDto trainingDto);
   TrainingDto toDto(Training training);
   List<TrainingDto> toDto(List<Training> trainings);
   List<Training> fromDto(List<Training> trainings);
}
