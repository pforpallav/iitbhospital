
import org.json.simple.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pallav
 */
public class ConfigFetcher {
    private static String VCAP;
    private static JSONObject json;
    private static String dbname;
    private static String dbserver;
    private static String dbuser;
    private static String dbpassword;
    
    public ConfigFetcher(){
        init();
    }
    
    public void init(){
        VCAP = java.lang.System.getenv("VCAP_SERVICES");
        //VCAP = "{\"mysql-5.1\":[{\"name\":\"mysql-4f700\",\"label\":\"mysql-5.1\",\"plan\":\"free\",\"tags\":[\"mysql\",\"mysql-5.1\",\"relational\"],\"credentials\":{\"name\":\"mydb\",\"hostname\":\"localhost\",\"host\":\"localhost\",\"port\":3306,\"user\":\"root\",\"username\":\"root\",\"password\":\"vallap\"}},{\"name\":\"mysql-f1a13\",\"label\":\"mysql-5.1\",\"plan\":\"free\",\"tags\":[\"mysql\",\"mysql-5.1\",\"relational\"],\"credentials\":{\"name\":\"mydb\",\"hostname\":\"mysql-node01.us-east-1.aws.af.cm\",\"host\":\"127.0.0.1\",\"port\":3306,\"user\":\"root\",\"username\":\"root\",\"password\":\"vallap\"}}]}";
        json = (JSONObject)JSONValue.parse(VCAP);
        JSONArray creds = (JSONArray) json.get("mysql-5.1");
        JSONObject cred1 = (JSONObject) creds.get(0);
        cred1 = (JSONObject) cred1.get("credentials");
        JSONObject temp = cred1;
        dbname = (String)temp.get("name");
        dbserver = (String)temp.get("hostname");
        dbuser = (String)temp.get("user");
        dbpassword = (String)temp.get("password");
        
    }
    
    public String fetchDBNAME(){
        //return "mydb";
        return dbname;
    }
    public String fetchDBSERVER(){
        //return "localhost";
        return dbserver;
    }
    public String fetchDBUSER(){
        //return "root";
        return dbuser;
    }
    public String fetchDBPASS(){
        //return "vallap";
        return dbpassword;
    }
}
