package hu.ponte.hr.repository;

import hu.ponte.hr.entity.ImageMetaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<ImageMetaEntity, Long> {
}