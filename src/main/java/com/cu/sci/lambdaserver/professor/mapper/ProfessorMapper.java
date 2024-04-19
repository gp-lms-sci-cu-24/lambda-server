package com.cu.sci.lambdaserver.professor.mapper;

import com.cu.sci.lambdaserver.professor.Professor;
import com.cu.sci.lambdaserver.professor.dto.ProfessorDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper implements IMapper<Professor, ProfessorDto> {
    private final ModelMapper modelMapper;

    public ProfessorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfessorDto mapTo(Professor professor) {
        return modelMapper.map(professor, ProfessorDto.class);
    }

    @Override
    public Professor mapFrom(ProfessorDto professorDto) {
        return modelMapper.map(professorDto, Professor.class);
    }

    @Override
    public Professor update(ProfessorDto professorDto, Professor professor) {
        return null;
    }
}