package hu.ponte.hr.controller.upload;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.services.ImageStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
@Component
@RequestMapping("api/file")
@AllArgsConstructor
public class UploadController {

    ImageStore imageStore;

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(@RequestParam("file") MultipartFile file) throws Exception {
        ImageMeta image = ImageMeta.builder()
                .size(file.getSize())
                .name(file.getOriginalFilename())
                .mimeType(file.getContentType())
                .build();
        imageStore.saveImage(image);
        return "ok";
    }
}
