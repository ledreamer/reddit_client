package satyaki.com.reddit_client;

/**
 * Created by satyaki on 19/10/15.
 */
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import android.os.Environment;
import android.util.Log;

/**
 * Implements the caching mechanism used by our app
 *
 * author Satyaki
 */
public class MyCache {

    // The cache directory should look something like this
    // Replace com.satyaki.com.reddit_client with your own package name
    static private String cacheDirectory =
            "/Android/data/satyaki.com.reddit_client/cache/";

    /*
     * Make sure the SD Card is available and we can write to it
     * Once confirmed, create the cache directory if it does not
     * exist yet
     */
    static {
        if(Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)){
            cacheDirectory=Environment.getExternalStorageDirectory()
                    +cacheDirectory;
            File f=new File(cacheDirectory);
            f.mkdirs();
        }
    }

    static public String convertToCacheName(String url){
        try {
            MessageDigest digest=MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            byte[] b=digest.digest();
            BigInteger bi=new BigInteger(b);
            return "mycache_"+bi.toString(16)+".cac";
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
            return null;
        }
    }

    private static boolean tooOld(long time){
        long now=new Date().getTime();
        long diff=now-time;
        if(diff>1000*60*5)
            return true;
        return false;
    }

    public static byte[] read(String url){
        try{
            String file=cacheDirectory+"/"+convertToCacheName(url);
            File f=new File(file);
            if(!f.exists() || f.length() < 1) return null;
            if(f.exists() && tooOld(f.lastModified())){
                // Delete the cached file if it is too old
                f.delete();
            }
            byte data[]=new byte[(int)f.length()];
            DataInputStream fis=new DataInputStream(
                    new FileInputStream(f));
            fis.readFully(data);
            fis.close();
            return data;
        }catch(Exception e) { return null; }
    }

    public static void write(String url, String data){
        try{
            String file=cacheDirectory+"/"+convertToCacheName(url);
            PrintWriter pw=new PrintWriter(new FileWriter(file));
            pw.print(data);
            pw.close();
        }catch(Exception e) { }
    }
}