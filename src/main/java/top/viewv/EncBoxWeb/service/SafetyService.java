package top.viewv.EncBoxWeb.service;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.viewv.EncBoxWeb.model.mac.SHA;
import top.viewv.EncBoxWeb.model.symmetric.Decrypt;
import top.viewv.EncBoxWeb.model.symmetric.Encrypt;
import top.viewv.EncBoxWeb.model.tools.Base64Tool;
import top.viewv.EncBoxWeb.model.tools.GenerateKeyPair;
import top.viewv.EncBoxWeb.model.tools.GenerateSecKey;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
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

    public HashMap<String,String> generateKeypair(){
        KeyPair kp = GenerateKeyPair.generate("RSA", 2048);

        Key pk = kp.getPublic();
        Key rk = kp.getPrivate();

        String publickey = Base64Tool.tobase64(pk.getEncoded());
        String privatekey = Base64Tool.tobase64(rk.getEncoded());

        HashMap<String,String> result = new HashMap<>();
        result.put("publickey",publickey);
        result.put("privatekey",privatekey);

        return result;
    }

    public String asymmetricEncrypt(String publickey, String plain){
        byte[] publickeyBytes = Base64Tool.tobytes(publickey);

        Security.addProvider(new BouncyCastleProvider());
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA", "BC");
            X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(publickeyBytes);
            PublicKey pk = kf.generatePublic(pkSpec);

            byte[] cipher = top.viewv.EncBoxWeb.model.asymmetric.Encrypt.encrypt(plain.getBytes(StandardCharsets.UTF_8),"RSA",pk);

            return Base64Tool.tobase64(cipher);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            System.out.println("asyenc error!");
            return null;
        }
    }

    public String asymmetricDecrypt(String privatekey, String cipher){
        byte[] privatekeyBytes = Base64Tool.tobytes(privatekey);

        Security.addProvider(new BouncyCastleProvider());
        try {
            KeyFactory kf;
            kf = KeyFactory.getInstance("RSA", "BC");
            PKCS8EncodedKeySpec skSpec = new PKCS8EncodedKeySpec(privatekeyBytes);
            PrivateKey sk = kf.generatePrivate(skSpec);

            byte[] plain = top.viewv.EncBoxWeb.model.asymmetric.Decrypt.decrypt(Base64Tool.tobytes(cipher),"RSA",sk);

            return new String(plain,StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            System.out.println("asydec error!");
            return null;
        }
    }

    public String hash(String filenameuuid,String algorithm){
        String mode;
        switch (algorithm){
            case "SHA-512/224":
                mode = "512/224";
                break;
            case "SHA-512/226":
                mode = "512/226";
                break;
            case "SHA3-256":
                mode = "3/256";
                break;
            case "SHA3-512":
                mode = "3/512";
                break;
            case "SHA-256":
            default:
                mode = "256";
                break;
        }

        try {
            byte[] hashBytes = SHA.digest(filenameuuid,mode);
            StringBuilder hexString = new StringBuilder();
            String tempHexString;

            for (byte hash : hashBytes) {
                tempHexString = Integer.toHexString(0xFF & hash);
                if (tempHexString.length() != 2)
                    tempHexString = "0" + tempHexString;
                hexString.append(tempHexString);
            }

            return hexString.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
