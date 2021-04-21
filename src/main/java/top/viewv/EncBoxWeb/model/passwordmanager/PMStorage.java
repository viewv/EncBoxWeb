package top.viewv.EncBoxWeb.model.passwordmanager;

import java.util.HashMap;

public class PMStorage {
    public String twofa;
    public String userid;
    public String key;
    public HashMap<String, String> passwords = new HashMap<>();
}
