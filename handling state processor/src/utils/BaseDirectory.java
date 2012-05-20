package utils;

import java.io.File;

/**
 * @author victor.konopelko
 *         Date: 30.01.12
 */
public class BaseDirectory {

    //http://stackoverflow.com/questions/1099300/whats-the-difference-between-getpath-getabsolutepath-and-getcanonicalpath
    //http://www.avajava.com/tutorials/lessons/whats-the-difference-between-a-files-path-absolute-path-and-canonical-path.html
    //http://illegalargumentexception.blogspot.com/2008/04/java-finding-application-directory.html
    public static String get() {
        return new File("handling state processor").getAbsolutePath();
    }


}
