package top.viewv.EncBoxWeb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import top.viewv.EncBoxWeb.entity.FilenameEntity;

public interface FilenameRepository extends JpaRepository<FilenameEntity,String> {
    FilenameEntity findFilenameEntityByFilenameuuid(String filenameuuid);

    @Modifying
    @Transactional
    void deleteFilenameEntityByFilenameuuid(String filenameuuid);
}
