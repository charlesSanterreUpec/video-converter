package edu.esipe.i3.ezipflix.frontend.data.services;

import edu.esipe.i3.ezipflix.frontend.data.entities.VideoConversions;

public interface ConversionService {

    String publish(VideoConversions video) throws Exception;

    String save(VideoConversions video);

}
