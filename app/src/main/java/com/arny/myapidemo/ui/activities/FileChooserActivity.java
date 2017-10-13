package com.arny.myapidemo.ui.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.arny.arnylib.files.FileUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.FileArrayAdapter;
import com.arny.myapidemo.models.Option;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileChooserActivity extends ListActivity {

    private File currentDir;
    private FileArrayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File(Environment.getExternalStorageDirectory().getPath());
        fill(currentDir);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fill(File folder) {
        Observable<List<Option>> objectObservable = Observable.create(e -> {
            e.onNext(getFolderItems(folder));
            e.onComplete();
        });
        objectObservable
                .doOnSubscribe(disposable -> runOnUiThread(() -> this.setTitle("Current Dir: loading..." )))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    this.setTitle("Current Dir: " + folder.getName());
                    adapter = new FileArrayAdapter(FileChooserActivity.this, R.layout.file_view, o);
                    this.setListAdapter(adapter);
                },throwable -> ToastMaker.toastError(this,throwable.getMessage()));
    }

    @NonNull
    private static List<Option> getFolderItems(File folder) {
        ArrayList<File> files = FileUtils.getDir(folder);
        List<Option> dir = new ArrayList<>();
        try {
            if (files != null && files.size() > 0) {
                for (File ff : files) {
                    long size = ff.length();
                    boolean isDirectory = ff.isDirectory();
                    String path = ff.getAbsolutePath();
                    String parent = ff.getParent();
                    dir.add(new Option(ff.getName(), path,size,isDirectory,parent));
                }
            }else{
                Log.e(FileChooserActivity.class.getSimpleName(), "fill: empty folder:" + folder.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sortDirByName(dir);
        if (!folder.getName().equalsIgnoreCase("sdcard")) {
            dir.add(0, new Option(folder.getName(), folder.getParent(), 0, true, folder.getParent()));
        }
        return dir;
    }

    private static void sortDirByName(List<Option> dir) {
        Collections.sort(dir, (o1, o2) -> {
            if (o1.getName() != null) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            } else {
                throw new IllegalArgumentException();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Option o = adapter.getItem(position);
        if (o != null && o.isDirectory()) {
            currentDir = new File(o.getPath());
            fill(currentDir);
        } else {
            onFileClick(o);
        }
    }

    private void onFileClick(Option o) {
        Toast.makeText(this, "File Clicked: " + FileUtils.formatFileSize(o.getSize(),3), Toast.LENGTH_SHORT).show();
    }
}