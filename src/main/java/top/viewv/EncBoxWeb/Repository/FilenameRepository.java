package top.viewv.EncBoxWeb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.viewv.EncBoxWeb.entity.FilenameEntity;

public interface FilenameRepository extends JpaRepository<FilenameEntity,String> {
    FilenameEntity findFilenameEntityByFilenameuuid(String filenameuuid);
    void deleteByFilenameuuid(String filenameuuid);
}
