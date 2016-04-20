package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myapplication.model.DaoMaster;
import com.example.myapplication.model.DaoSession;
import com.example.myapplication.model.Note;
import com.example.myapplication.model.NoteDao;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.irecyclerview.CommonHolder;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.fastandroiddev.irecyclerview.IRecyclerView;
import com.ijustyce.fastandroiddev.irecyclerview.PullToRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.list)
    IRecyclerView recyclerView;

    private Handler handler;

    private List<Note> array;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        handler = new Handler();

        initData();
        doInit();

     //   FileUtils.showFile(FileUtils.getAvailablePath(this, "my"), this);
    }

    class myAdapter extends IAdapter<Note>{

        @Override
        public CommonHolder createViewHolder(Context mContext, ViewGroup parent) {

            ILog.i("===createViewHolder===");
            return CommonHolder.getInstance(R.layout.item, mContext, parent);
        }

        @Override
        public void createView(CommonHolder commonHolder, Note object) {

            ILog.i("===createView===");
            if (object != null)
            commonHolder.setText(R.id.textView, object.getId() + " " + object.getText());
        }

        public myAdapter(List<Note> mData, Context mContext) {
            super(mData, mContext);
        }
    }

    private Runnable onFinish = new Runnable() {
        @Override
        public void run() {
            recyclerView.onRefreshEnd();
            recyclerView.onLoadEnd();
            recyclerView.notifyDataSetChanged();
        }
    };

    private void addData(NoteDao noteDao){

        ILog.i("===addData===");
        List<Note> list = new ArrayList<>();
        for (int i = 1; i < 201; i ++){

            Note note = new Note();
            note.setText("text " + i);
            note.setContent("hahhaha");
            list.add(note);
        }
        noteDao.insertInTx(list);
        array.addAll(noteDao.queryBuilder().offset(array.size()).limit(10).list());
    }

    private void initData(){

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();

        array = new ArrayList<>();
        array = noteDao.queryBuilder().offset(array.size()).limit(10).list();
       // if (array.size() < 10){
            array.clear();
            addData(noteDao);
     //   }

//        noteDao.insert(new Note());
//        noteDao.update(new Note());
//        noteDao.delete(new Note());
//        noteDao.deleteByKey(new Note().getId());
//        noteDao.loadAll();
//        noteDao.queryBuilder().where(NoteDao.Properties.Id.ge(10)).list();  //  >=
//        noteDao.queryBuilder().where(NoteDao.Properties.Id.gt(10)).list();  //  >
//        noteDao.queryBuilder().where(NoteDao.Properties.Id.le(10)).list();  //  <=
//        noteDao.queryBuilder().where(NoteDao.Properties.Id.lt(10)).list();  //  <
//
//        noteDao.queryBuilder().offset(10).limit(10);

        //  ge greater or equal gt greater than , le less or equal lt less then
    }

    private void printArray(){

        int size = array.size();
        for (int i = 0; i < size; i++){
            Note tmp = array.get(i);
            if (tmp == null){
                ILog.i("===array===", "tmp is null ...");
            }else {
                ILog.i("===array===", "id is " + tmp.getId() + " text is " + tmp.getText());
            }
        }
    }

    private void doInit(){

  //      recyclerView.setStaggeredGridLayout(3, false);
        recyclerView.setAdapter(new myAdapter(array, this));
        printArray();
        recyclerView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {

                ILog.i("===loadingmore===");
                array.clear();
                array.addAll(noteDao.queryBuilder().offset(array.size()).limit(10).list());
                handler.postDelayed(onFinish, 3000);
            }

            @Override
            public void onLoadMore() {

                ILog.i("===loadingmore===");
                array.addAll(noteDao.queryBuilder().offset(array.size()).limit(10).list());
                printArray();
                ILog.i("===size===", "" + array.size());
                handler.postDelayed(onFinish, 3000);
            }
        });

        recyclerView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.view_header1, null));
        recyclerView.addFooterView(LayoutInflater.from(this).inflate(R.layout.view_header1, null));
    }
}
