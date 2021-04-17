package top.viewv.EncBoxWeb.service;

import org.springframework.stereotype.Service;
import top.viewv.EncBoxWeb.model.symmetric.Decrypt;
import top.viewv.EncBoxWeb.model.symmetric.Encrypt;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Service
public class SafetyService {

    public void symmetricEncrypt(String sourcefilepath, String sourcefilename, String destfile, String algorithm,
                   SecretKey secretKey, Boolean ifAEAD, byte[] associatedData) throws IOException {
        Encrypt encrypt = new Encrypt();
        encrypt.encrypt(sourcefilepath,sourcefilename,destfile,algorithm,secretKey,ifAEAD,associatedData);
    }

    public void symmetricDecrypt(String sourcefile, String destfilepath, String password) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException, NoSuchProviderException, InvalidKeyException {
        Decrypt decrypt = new Decrypt();
        decrypt.decrypt(sourcefile,destfilepath,password);
    }
}
