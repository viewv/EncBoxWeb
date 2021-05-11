package top.viewv.EncBoxWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.viewv.EncBoxWeb.Repository.FilenameRepository;
import top.viewv.EncBoxWeb.entity.FilenameEntity;

@Service
public class DataService {
    @Autowired
    private FilenameRepository filenameRepository;

    public String getFilenameByFilenameuuid(String filenameuuid){
        FilenameEntity filenameEntity = filenameRepository.findFilenameEntityByFilenameuuid(filenameuuid);
        return filenameEntity.getFilename();
    }

    public boolean addFile(String filename, String filenameuuid){
        FilenameEntity filenameEntity = new FilenameEntity();
        filenameEntity.setFilename(filename);
        filenameEntity.setFilenameuuid(filenameuuid);
        try {
           filenameRepository.save(filenameEntity);
           return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void deleteFileuuid(String filenameuuid){
        filenameRepository.deleteFilenameEntityByFilenameuuid(filenameuuid);
    }
}
