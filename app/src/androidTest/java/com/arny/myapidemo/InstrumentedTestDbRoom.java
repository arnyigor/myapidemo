package com.arny.myapidemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.files.FileUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.Utility;
import com.arny.arnylib.utils.generators.Generator;
import com.arny.myapidemo.api.User;
import com.arny.myapidemo.database.RoomDB;
import com.arny.myapidemo.models.InfoObject;
import com.arny.myapidemo.models.TestSubObject;
import com.arny.myapidemo.ui.activities.DBHelperActivity;
import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTestDbRoom {
    @Test
    public void getValidContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertNotNull(appContext);
    }

    @Test
    public void getValidDbRoom() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        List<TestSubObject> listTestSubObject = RoomDB.getDb(appContext).getTestDao().getListTest();
        assertNotNull(listTestSubObject);
    }

    @Test
    public void saveObjectToRoom() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TestSubObject testSubObject = new TestSubObject();
        testSubObject.setTitle(Generator.getWord());
        testSubObject.setGuid(Generator.getUUID());
        InfoObject infoObject = new InfoObject(Generator.getMaleName(), Generator.getUUID());
        infoObject.setSize(MathUtils.randLong(0, 10000));
        infoObject.setType(Generator.getWord());
        ArrayList<InfoObject> arrayList = new ArrayList<>();
        arrayList.add(infoObject);
        testSubObject.setInfo(arrayList);
        long row = RoomDB.getDb(appContext).getTestDao().insert(testSubObject);
        assertTrue(row != 0);
        Log.i(DBHelperActivity.class.getSimpleName(), "rxSave: row:" + row);
        ArrayList<User> users = new ArrayList<>();
        int cntUsers = MathUtils.randInt(1, 50);
        for (int i = 0; i < cntUsers; i++) {
            User e = new User(Generator.getWord(), Generator.getMaleName());
            e.setParentId(row);
            users.add(e);
        }
        long[] rows = RoomDB.getDb(appContext).getTestDao().insert(users);
        assertThat(rows).doesNotHaveDuplicates();
        List<TestSubObject> listTest = RoomDB.getDb(appContext).getTestDao().getListTest();
        Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "saveObjectToRoom: listTest:" + listTest);
        assertThat(listTest).is(new Condition<List<? extends TestSubObject>>() {
            @Override
            public boolean matches(List<? extends TestSubObject> values) {
                return values != null && values.size() > 0;
            }
        });
    }

    @Test
    public void getUsersDbRoom() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        List<TestSubObject> listTestSubObject = RoomDB.getDb(appContext).getTestDao().getListTest();
        assertThat(listTestSubObject).is(new Condition<List<? extends TestSubObject>>() {
            @Override
            public boolean matches(List<? extends TestSubObject> subObjects) {
                for (TestSubObject subObject : subObjects) {
                    long id = subObject.getId();
                    Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: id:" +id);
                    List<User> users = RoomDB.getDb(appContext).getTestDao().getUsers(id);
                    Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: users:" +users);
                    assertThat(users).is(new Condition<List<? extends User>>() {
                        @Override
                        public boolean matches(List<? extends User> value) {
                            return value != null && value.size() > 0;
                        }
                    });
                }
                return subObjects.size() > 0;
            }
        });
    }

    @Test
    public void getJsonDbRoom() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        List<TestSubObject> listTestSubObject = RoomDB.getDb(appContext).getTestDao().getListTest();
        assertThat(listTestSubObject).is(new Condition<List<? extends TestSubObject>>() {
            @Override
            public boolean matches(List<? extends TestSubObject> subObjects) {
                for (TestSubObject subObject : subObjects) {
                    ArrayList<InfoObject> info = subObject.getInfo();
                    Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: info:" +info);
                    assertThat(info).is(new Condition<List<? extends InfoObject>>() {
                        @Override
                        public boolean matches(List<? extends InfoObject> value) {
                            return !Utility.empty(value);
                        }
                    });
                }
                return subObjects.size() > 0;
            }
        });
    }

    @Test
    public void migrationLoadDbRoom() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ArrayList<String> filenames = FileUtils.listAssetFiles(appContext,"migrations");
        ArrayList<String> roomMigrations = DBProvider.getSortedRoomMigrations(filenames);
        assertThat(roomMigrations).is(new Condition<List<? extends String>>() {
            @Override
            public boolean matches(List<? extends String> value) {
                Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: value:" +value);
                return value!=null && value.size()>0;
            }
        });
    }

    @Test
    public void migrationMatchDbRoom() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ArrayList<String> filenames = FileUtils.listAssetFiles(appContext,"migrations");
        ArrayList<String> roomMigrations = DBProvider.getSortedRoomMigrations(filenames);
        assertThat(roomMigrations.get(0)).is(new Condition<String>() {
            @Override
            public boolean matches(String value) {
                String roomMigrationMatch = DBProvider.getRoomMigrationMatch(value);
                Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: roomMigrationMatch:" +roomMigrationMatch);
                return !Utility.empty(roomMigrationMatch);
            }
        });
    }

    @Test
    public void migrationVersionsDbRoom() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ArrayList<String> filenames = FileUtils.listAssetFiles(appContext,"migrations");
        ArrayList<String> roomMigrations = DBProvider.getSortedRoomMigrations(filenames);
        Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "migrationDbRoom: roomMigrations:" +roomMigrations);
        assertThat(roomMigrations.get(0)).is(new Condition<String>() {
            @Override
            public boolean matches(String value) {
                String roomMigrationMatch = DBProvider.getRoomMigrationMatch(value);
                Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: roomMigrationMatch:" +roomMigrationMatch);
                int start = DBProvider.getRoomMigrationVersion(value,0);
                int end = DBProvider.getRoomMigrationVersion(value,1);
                Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: start:" +start);
                Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "matches: end:" +end);
                boolean isZero = (start == end) && start==0;
                boolean isDowngraded = start < end;
                return isZero || isDowngraded;
            }
        });
    }

    @Test
    public void roomDbVersion() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        int versionFromFile = DBProvider.getDbVersionFromFile(appContext, "Room.db");
        Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "roomDbVersion: " + versionFromFile);
        assertThat(versionFromFile).isNotEqualTo(0);
    }

    @Test
    public void roomTables() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        List<String> dbTables = RoomDB.getDb(appContext).getTestDao().getDbTables();
        Log.i(InstrumentedTestDbRoom.class.getSimpleName(), "roomTables: dbTables:" + dbTables);
        assertThat(dbTables).contains("category_test");
//        assertThat(dbTables).contains("gooditems");
    }


}
