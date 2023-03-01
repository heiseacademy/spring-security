package de.trion.training.common.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository)
    {
        this.trainingRepository = trainingRepository;
    }

    public Page<Training> findAll(Pageable pageable)
    {
        return trainingRepository.findAll(pageable);
    }

    public Training getById(String id)
    {
        return trainingRepository.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public Training save(Training training)
    {
        return trainingRepository.save(training);
    }

    public List<Training> findAll()
    {
        return trainingRepository.findAll();
    }
}
