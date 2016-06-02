package grability.com.grability;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import grability.com.grability.entities.ContentEntity;

/**
 * Created by Salsa on 30-May-16.
 */
public class DownloadContentImages{
    private String TAG_CONTENT_DOWNLOAD = "content.download";

    private File imagesDirectory = null;
    public static String imagesPath;
    public static String imagesFolderList[] = MonoleticApp.imagesFolderList;
    private String[] downloadUrl = {"", "", ""};

    private boolean isDBEmpty = false;
    private boolean isFolderEmpty = true;
    private boolean isInternetOn = false;

    public DownloadContentImages() {
        imagesPath = MonoleticApp.imagesPath;
        allDirectoryMaker();
    }

    private void allDirectoryMaker() {
        clearFolderFiles(imagesDirectory);
        directoryMaker(imagesPath);
        for (int i = 0; i < 3; i++) {
            directoryMaker(imagesPath + imagesFolderList[i]);
            clearFolderFiles(new File(imagesPath + imagesFolderList[i]));
        }
    }

    private void directoryMaker(String dir) {
        File imgDirectory = new File(dir);
        if (!imgDirectory.exists()) {
            imgDirectory.mkdirs();
        }
    }

    // A simple download manager, we assume that no harsh situation will happen such as
    // the JSON downloading was interupted or images download was interupted because of internet connection
    //or other reasons. In that harsh case we neeed to check combinations of isDatabaseEmpty or isFolderEmpty
    // or is there a difference between content of the server and the static content source, database or the folder
    // then for each case we need to react properly
    public void manageDownload() {
        isDBEmpty = MainApplication.dataSource.getAllContent().isEmpty();

        // Situation that can hapen is in the middle of downloading it can be interupted so
        // we need to check what is downloaded and what is not and resume
        if (!isDBEmpty) {
            MainApplication.dataSource.deleteAllContent();
            for (ContentEntity content : MonoleticApp.contentEntityArrayList) {
                MainApplication.dataSource.insertContent(content);
            }
        }

        if (!isDBEmpty && isFolderEmpty) {
            //TODO
        }

        downloadFiles();
    }

    public void run() {
        manageDownload();
    }

    private void getFileListFromSD() {
        // TODO
    }

    private void getContentListFromServer() {
        //TODO
    }

    private void clearFolderFiles(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] imagesFileList = directory.listFiles();
            if (imagesFileList.length > 0) {
                for (File file : imagesFileList) {
                    file.delete();
                }
            }
        }
    }


    private boolean downloadFiles() {
            //TODO we need to download only files that we don't have
            try {
                URL url;
                File file;
                URLConnection ucon;
                InputStream is;
                String fileName;
                String dirTemp;
                for (ContentEntity contentEntity : MonoleticApp.contentEntityArrayList) {
                    fileName = String.valueOf(contentEntity.getId().getImId());
                    Log.d(TAG_CONTENT_DOWNLOAD, " DownloadContentImages/downloadFiles Downloading:" + fileName);
                    downloadUrl[0] = contentEntity.getImImage().getLabelHeight53();
                    downloadUrl[1] = contentEntity.getImImage().getLabelHeight75();
                    downloadUrl[2] = contentEntity.getImImage().getLabelHeight100();
                    for (int i = 0; i < 3; i++) {
                        url = new URL(downloadUrl[i]);
                        dirTemp = imagesPath + imagesFolderList[i];
                        file = new File(dirTemp, fileName + ".png"); // files type: 73328.png
                    // Open a connection to that URL
                        ucon = url.openConnection();

                    // Define InputStreams to read from the URLConnection
                        is = ucon.getInputStream();
                    // Read bytes to the Buffer until there is nothing more to read(-1)
                        byte data[] = new byte[1024];
                        int count = 0;
                        FileOutputStream fos = new FileOutputStream(file);
                        while ((count = is.read(data)) != -1) {
                            fos.write(data, 0, count);
                        }

                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG_CONTENT_DOWNLOAD, " DownloadContentImages/downloadFiles Finished");

        return true;
    }
}
