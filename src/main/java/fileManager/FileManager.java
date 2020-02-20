package fileManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private Path path;
    private boolean saved;

    public FileManager(){
        path = null;
        this.saved = false;
    }

    public FileManager(File file, boolean saved){
        setPath(file);
        this.saved = saved;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setPath(File file){
        this.path = Paths.get(String.valueOf(file));
    }

    public Path getPath(){
        return path;
    }

    public String getExtension(){
        try {
            return path.toString().substring(path.toString().indexOf("."));
        }catch(StringIndexOutOfBoundsException e){
            return null;
        }
    }
}
