package edu.esipe.i3.ezipflix.frontend;

import edu.esipe.i3.ezipflix.frontend.data.entities.VideoConversions;
import edu.esipe.i3.ezipflix.frontend.data.services.ConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SpringBootApplication
@RestController
public class VideoDispatcher{

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoDispatcher.class);

    @Autowired
    ConversionService conversionService;
    public static void main(String[] args) throws Exception {
        SpringApplication.run(VideoDispatcher.class, args);
    }

    // ┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
    // │ REST Resources                                                                                                │
    // └───────────────────────────────────────────────────────────────────────────────────────────────────────────────┘
    @GetMapping(value = "/convert")
    public ConversionResponse convert(/*@RequestBody ConversionRequest request*/) throws Exception {
        VideoConversions videoConversion = new VideoConversions(
                UUID.randomUUID().toString(),
                /*request.getPath().toString()*/"origin",
        "target");
        String dbRetour = this.conversionService.save(videoConversion);
        String messageId = this.conversionService.publish(videoConversion);

        return new ConversionResponse(UUID.randomUUID().toString(),messageId, dbRetour);
    }

}
