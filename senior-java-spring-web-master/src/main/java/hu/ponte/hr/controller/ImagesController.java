package hu.ponte.hr.controller;


import hu.ponte.hr.services.ImageStore;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@RestController()
@RequestMapping("api/images")
@AllArgsConstructor
public class ImagesController {

    private ImageStore imageStore;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {
        return imageStore.fetchImageList();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            ImageMeta image = imageStore.findImageById(Long.valueOf(id));
            InputStream in = new ByteArrayInputStream(image.getPhoto());
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        } catch (NoSuchElementException | IOException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image Not Found", exception);
        }
    }

}
