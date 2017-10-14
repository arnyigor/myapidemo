package com.arny.myapidemo.ui.activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.files.FileUtils;
import com.arny.arnylib.utils.*;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.FoldersViewHolder;
import com.arny.myapidemo.models.FolderFile;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;
import io.reactivex.Observable;
import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FoldersCleanerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialogFragment.TimePickerDialogHandler {

	private static final int REQUEST_DIRECTORY = 101;
	private RecyclerView rvFolders;
	private FloatingActionButton fabAddFolder;
	private int hrs;
	private int min;
	private ProgressDialog pDialog;
	private SimpleBindableAdapter<FolderFile, FoldersViewHolder> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_folders_cleaner);
		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);
		initToolbar();
		findViewById(R.id.btnCleanTime).setOnClickListener(this);
		findViewById(R.id.btnCleanTime).setVisibility(View.GONE);
		findViewById(R.id.btnClean).setOnClickListener(this);
		rvFolders = (RecyclerView) findViewById(R.id.rvFolders);
		rvFolders.setLayoutManager(new LinearLayoutManager(this));
		fabAddFolder = (FloatingActionButton) findViewById(R.id.fabAddFolder);
		fabAddFolder.setOnClickListener(this);
		adapter = new SimpleBindableAdapter<>(this, R.layout.simple_example_item, FoldersViewHolder.class);
		rvFolders.setAdapter(adapter);
		adapter.setActionListener(new FoldersViewHolder.SimpleActionListener() {
			@Override
			public void OnRemove(int position) {
				long id = adapter.getItems().get(position).getId();
				DBProvider.deleteDB("cleanfile", "_id = ?",
						new String[]{String.valueOf(id)}, FoldersCleanerActivity.this);
				adapter.removeChild(position);
			}

			@Override
			public void OnItemClickListener(int position, Object Item) {

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utility.mainThreadObservable(loadDB())
				.subscribe(folderFiles -> {
					Log.i(FoldersCleanerActivity.class.getSimpleName(), "onCreate: folderFiles = " + folderFiles);
					adapter.clear();
					adapter.addAll(folderFiles);
				}, throwable -> ToastMaker.toastError(this, "Ошибка загрузки списка:" + throwable.getMessage()));
	}

	private Observable<ArrayList<FolderFile>> loadDB() {
		return Observable.create(e -> {
			e.onNext(getDBFolderFiles());
			e.onComplete();
		})
				.map(o -> (ArrayList<FolderFile>) o)
				.map(folderFiles -> {
					for (FolderFile folderFile : folderFiles) {
						folderFile.setSize(FileUtils.getFolderSize(new File(folderFile.getFilePath())));
					}
					return folderFiles;
				});
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			setTitle("Очистка директорий");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				super.onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private ArrayList<FolderFile> getDBFolderFiles() {
		Cursor cursor = DBProvider.selectDB("cleanfile", null, null, null, null, this);
		return DBProvider.getCursorObjectList(cursor, FolderFile.class);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnClean:
				Stopwatch stopwatch = new Stopwatch();
				stopwatch.start();
				Utility.mainThreadObservable(
						Utility.makeObservable(adapter.getItems()).map(folderFiles -> {
							for (FolderFile folderFile : folderFiles) {
								boolean rem = FileUtils.deleteFile(new File(folderFile.getFilePath()));
								if (!rem) {
									return false;
								}
							}
							stopwatch.stop();
							return true;
						})
								.map(aBoolean -> {
									if (aBoolean) {
										List<FolderFile> items = adapter.getItems();
										int pos = 0;
										for (FolderFile item : items) {
											item.setSize(FileUtils.getFolderSize(new File(item.getFilePath())));
											int finalPos = pos;
											runOnUiThread(() -> {
												adapter.notifyItemChanged(finalPos);
											});
											pos++;
										}
									}
									return aBoolean;
								})
				).subscribe(aBoolean -> {
					if (aBoolean) {
						ToastMaker.toastSuccess(this, "Файлы удалены за " + stopwatch.getElapsedTimeMili() + " мс");
					} else {
						ToastMaker.toastError(this, "Файлы не удалены");
					}
				}, throwable -> ToastMaker.toastError(this, "Ошибка удаления:" + throwable.getMessage()));
				break;
			case R.id.btnCleanTime:
				TimePickerBuilder tpb = new TimePickerBuilder()
						.setFragmentManager(getSupportFragmentManager())
						.setStyleResId(R.style.BetterPickersDialogFragment);
				tpb.show();
				break;
			case R.id.fabAddFolder:
				Intent filePickerActivity = new Intent(this, FilePickerActivity.class);
				filePickerActivity.putExtra(FilePickerActivity.SCOPE, Scope.ALL);
				filePickerActivity.putExtra(FilePickerActivity.REQUEST, Request.DIRECTORY);
				filePickerActivity.putExtra(FilePickerActivity.INTENT_EXTRA_FAB_COLOR_ID, android.R.color.holo_green_dark);
				startActivityForResult(filePickerActivity, REQUEST_DIRECTORY);
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ((requestCode == REQUEST_DIRECTORY) && (resultCode == RESULT_OK)) {
			Stopwatch stopwatch = new Stopwatch();
			stopwatch.start();
			String folder = data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH);
			Utility.mainThreadObservable(
					Observable.zip(getTime(), getFolder(folder), this::saveFolder)
							.map(aBoolean -> {
								if (!aBoolean) {
									runOnUiThread(() -> ToastMaker.toast(this, "Папка не добавлена"));
								}
								return aBoolean;
							})
							.doOnSubscribe(disposable -> {
								runOnUiThread(() -> DroidUtils.showProgress(pDialog,"Добавление папки:" +new File(folder).getName() ));
							})
							.flatMap(aBoolean -> loadDB()))
					.subscribe(folderFiles -> {
						DroidUtils.hideProgress(pDialog);
						adapter.clear();
						adapter.addAll(folderFiles);
					}, throwable -> ToastMaker.toastError(this, "Ошибка сохранения:" + throwable.getMessage()));
		}
	}

	private Observable<String> getTime() {
		return Observable.create(e -> {
			e.onNext(getStringDateTime());
			e.onComplete();
		});
	}

	private Observable<FolderFile> getFolder(String path) {
		return Observable.create(e -> {
			e.onNext(getMediaFileInfo(path));
			e.onComplete();
		});
	}

	private boolean saveFolder(String time, FolderFile file) {
		ContentValues values = new ContentValues();
		values.put("cleantime", time);
		values.put("name", file.getFileName());
		values.put("path", file.getFilePath());
		values.put("size", file.getSize());
		Cursor cursor = DBProvider.selectDB("cleanfile", new String[]{"_id"},
				"name = ? AND path = ?", new String[]{values.getAsString("name"), values.getAsString("path")}, null, this);
		Long cursorObject = DBProvider.getCursorObject(cursor, Long.class);
		if (cursorObject != null && cursorObject > 0) {
			return DBProvider.updateDB("cleanfile", values, "_id = ?",
					new String[]{String.valueOf(cursorObject)}, this) > 0;
		} else {
			return DBProvider.insertDB("cleanfile", values, this) > 0;
		}
	}

	@NonNull
	private static FolderFile getMediaFileInfo(String path) {
		File file = new File(path);
		FolderFile fileInfo = new FolderFile();
		fileInfo.setFileName(file.getName());
		fileInfo.setFilePath(file.getPath());
		fileInfo.setSize(FileUtils.getFolderSize(new File(fileInfo.getFilePath())));
		fileInfo.setFileType(MediaFile.getMimeTypeForFile(file.getPath()));
		return fileInfo;
	}

	@Override
	public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
		this.hrs = hourOfDay;
		this.min = minute;
	}

	private String getStringDateTime() {
		DateTime now = DateTime.now();
		DateTime nextTime = DateTime.now();
		nextTime.withHourOfDay(hrs);
		nextTime.withMinuteOfHour(min);
		long nowMillis = now.getMillis();
		long nextTimeMillis = nextTime.getMillis();
		if (nowMillis > nextTimeMillis) {
			nextTime.plusDays(1);
		}
		return DateTimeUtils.getDateTime(nextTime, "ddMMyyyyHHmm");
	}
}
