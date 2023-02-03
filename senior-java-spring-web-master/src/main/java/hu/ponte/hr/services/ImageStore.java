package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.entity.ImageMetaEntity;
import hu.ponte.hr.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageStore {

    ImageRepository imageRepository;
    SignService signService;

    public void saveImage(ImageMeta imageMeta) {
        String sign = signService.sign(imageMeta.getName());
        ImageMetaEntity imageMetaEntity = ImageMetaEntity.builder()
                .name(imageMeta.getName())
                .digitalSign(imageMeta.getDigitalSign())
                .size(imageMeta.getSize())
                .mimeType(imageMeta.getMimeType())
                .digitalSign(sign)
                .photo(imageMeta.getPhoto())
                .build();
        imageRepository.save(imageMetaEntity);
    }

    public List<ImageMeta> fetchImageList() {
        List<ImageMetaEntity> imageMetaEntities = (List<ImageMetaEntity>) imageRepository.findAll();
        return imageMetaEntities.stream().map(this::buildImageMeta).collect(Collectors.toList());
    }

    public ImageMeta findImageById(Long id) {
        return buildImageMeta(imageRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    private ImageMeta buildImageMeta(ImageMetaEntity imageMetaEntity) {
        return ImageMeta.builder()
                .id(imageMetaEntity.getId().toString())
                .name(imageMetaEntity.getName())
                .size(imageMetaEntity.getSize())
                .mimeType(imageMetaEntity.getMimeType())
                .digitalSign(imageMetaEntity.getDigitalSign())
                .photo(imageMetaEntity.getPhoto())
                .build();
    }

}
