package sam.businesscardplanner.BusinessCard;

import java.io.File;

/**
 * Created by Administrator on 8/11/2015.
 */
abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
