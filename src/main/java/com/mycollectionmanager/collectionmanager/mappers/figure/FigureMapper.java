package com.mycollectionmanager.collectionmanager.mappers.figure;

import com.mycollectionmanager.collectionmanager.domain.dto.figure.FigureDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.figure.FigureDetailDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.Figure;
import com.mycollectionmanager.collectionmanager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class FigureMapper implements Mapper<Figure, FigureDetailDTO> {

    private final ModelMapper modelMapper;

    public FigureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        // Setting to allow map without the password token.
        // https://modelmapper.org/user-manual/configuration/#matching-strategies
        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    /**
     * Maps a Figure object to a FigureDTO object
     * @param figure The Figure object that will be mapped.
     * @return a FigureDTO object, which was mapped from the Figure object.
     */
    @Override
    public FigureDetailDTO mapTo(Figure figure) {
        return modelMapper.map(figure, FigureDetailDTO.class);
    }

    /**
     * Maps a FigureDetailDTO object to a Figure object
     * @param figureDetailDTO The FigureDTO object that will be mapped.
     * @return a Figure object, which was mapped from the FigureDTO object.
     */
    @Override
    public Figure mapFrom(FigureDetailDTO figureDetailDTO) {
        return modelMapper.map(figureDetailDTO, Figure.class);
    }
}
