package romanow.abc.android.menu;

import android.content.Intent;

import java.io.BufferedReader;
import java.util.ArrayList;

import romanow.abc.android.I_ArchiveMultiSelector;
import romanow.abc.android.MainActivity;
import romanow.abc.android.R;
import romanow.abc.android.service.AppData;
import romanow.abc.android.yandexmap.MapFilesActivity;
import romanow.abc.android.FileDescription;
import romanow.abc.android.FileDescriptionList;


public class MIMap extends MenuItem {
    public MIMap(MainActivity main0) {
        super(main0);
        main.addMenuList(new MenuItemAction("Показать на карте") {
            @Override
            public void onSelect() {
                main.selectMultiFromArchive("Показать на карте",procMapMultiSelector);
                }
            });
        }
    //----------------------------------------------------------------------------------------------
    private I_ArchiveMultiSelector procMapMultiSelector = new I_ArchiveMultiSelector() {
        @Override
        public void onSelect(FileDescriptionList fd, boolean longClick) {
            Intent intent = new Intent();
            intent.setClass(main.getApplicationContext(), MapFilesActivity.class);
            main.startActivity(intent);
            try {
                ArrayList<FileDescription> list = AppData.ctx().getFileList();
                list.clear();
                for (FileDescription ff : fd){
                    BufferedReader reader = main.openReader(ff.getOriginalFileName());
                    reader.close();
                    if (ff.getGps().gpsValid()) {
                        list.add(ff);
                        AppData.ctx().sendGPS(ff.getGps(), R.drawable.ballgreen, ff.getOriginalFileName(), true);
                        }
                }
            } catch (Exception ee){ main.errorMes(ee.toString());}
        }
    };

}
