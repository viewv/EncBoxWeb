package top.viewv.EncBoxWeb.service;

import org.springframework.stereotype.Service;
import top.viewv.EncBoxWeb.model.passwordmanager.PMSerialize;
import top.viewv.EncBoxWeb.model.passwordmanager.PMStorage;
import top.viewv.EncBoxWeb.utils.FilenameUUID;

import java.io.FileNotFoundException;

@Service
public class PasswordService {

    public PMStorage decode(String filename) throws FileNotFoundException {
        PMStorage pmStorage;

        PMSerialize pmSerialize = new PMSerialize();
        pmStorage = pmSerialize.deserialize(filename);
        return pmStorage;
    }

    public String encode(PMStorage pmStorage) throws FileNotFoundException {
        String filename = FilenameUUID.getRandomname();

        PMSerialize pmSerialize = new PMSerialize();
        pmSerialize.serialize(pmStorage,filename + ".ser");
        return filename + ".ser";
    }
}
