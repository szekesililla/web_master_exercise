package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.entity.Image;
import hu.ponte.hr.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageStore {

    ImageRepository imageRepository;
    SignService signService;

    public void saveImage(ImageMeta imageMeta) throws Exception {
        String sign = signService.sign(imageMeta.getName());
        Image image = Image.builder()
                .name(imageMeta.getName())
                .digitalSign(imageMeta.getDigitalSign())
                .size(imageMeta.getSize())
                .mimeType(imageMeta.getMimeType())
                .digitalSign(sign)
                .build();
        imageRepository.save(image);
    }

    public List<ImageMeta> fetchImageList() {
        List<Image> images = (List<Image>) imageRepository.findAll();
        return images.stream().map(this::buildImageMeta).collect(Collectors.toList());
    }

    private ImageMeta buildImageMeta(Image image) {
        return ImageMeta.builder()
                .id(image.getId().toString())
                .name(image.getName())
                .size(image.getSize())
                .mimeType(image.getMimeType())
                .digitalSign(image.getDigitalSign())
                .build();
    }

}
