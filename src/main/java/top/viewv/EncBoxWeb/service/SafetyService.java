package top.viewv.EncBoxWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.viewv.EncBoxWeb.model.symmetric.Decrypt;
import top.viewv.EncBoxWeb.model.symmetric.Encrypt;
import top.viewv.EncBoxWeb.model.tools.GenerateSecKey;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

@Service
public class SafetyService {

    @Autowired
    DataService dataService;

    public String symmetricEncrypt(String filenameuuid, String algorithm, int keylength,
                                   String password, Boolean ifAEAD, String associatedDataString) throws IOException {
        String filename = dataService.getFilenameByFilenameuuid(filenameuuid);
        SecretKey secretKey = GenerateSecKey.generateKey(password, keylength, 65566,
                1, "AES");

        byte[] associatedData = new byte[0];
        if (ifAEAD){
            associatedData = associatedDataString.getBytes();
        }

        Encrypt encrypt = new Encrypt();
        encrypt.encrypt(filenameuuid,filename,filenameuuid+"enc", algorithm,secretKey,ifAEAD,associatedData);

        File file = new File(filenameuuid);
        file.delete();

        File newfile = new File(filenameuuid+"enc");
        file = new File(filenameuuid);
        newfile.renameTo(file);

        return filenameuuid;
    }

    public String symmetricDecrypt(String filenameuuid, String password) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException, NoSuchProviderException, InvalidKeyException {
        Decrypt decrypt = new Decrypt();
        Map<String,String> result = decrypt.decrypt(filenameuuid,password);

        return result.get("filename");
    }
}
