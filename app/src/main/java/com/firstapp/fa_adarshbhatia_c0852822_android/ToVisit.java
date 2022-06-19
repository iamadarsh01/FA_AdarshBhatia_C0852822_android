package com.firstapp.fa_adarshbhatia_c0852822_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.List;

public class ToVisit extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    List<Bean> list;

    AdapterTest at;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_visit);

        lv = findViewById(R.id.lv);

        databaseHelper=new DatabaseHelper(this);

        list=databaseHelper.getToVisitedPlaces();
        at=new AdapterTest(this, list);
        lv.setAdapter(at);

        SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(lv),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {

                                Bean b = list.get(position);

                                databaseHelper.deleteFavPlace(b.getId());

                                at.remove(position);
                            }
                        });

        lv.setOnTouchListener(touchListener);
        lv.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {

                    touchListener.undoPendingDismiss();

                    Bean b = list.get(position);

                    Intent intent = new Intent(ToVisit.this, EditLoc.class);
                    intent.putExtra("id", String.valueOf(b.getId()));
                    intent.putExtra("placename", b.getPlacename());
                    intent.putExtra("address", b.getAddress());
                    intent.putExtra("latitude", b.getLatitude());
                    intent.putExtra("longitude", b.getLongitude());
                    intent.putExtra("visited", b.getVisited());
                    intent.putExtra("pdate", b.getCreatedon());
                    startActivity(intent);

                } else {
                    Bean b = list.get(position);

                    Intent intent = new Intent(ToVisit.this, MapsActivity.class);
                    intent.putExtra("id", String.valueOf(b.getId()));
                    intent.putExtra("placename", b.getPlacename());
                    intent.putExtra("address", b.getAddress());
                    intent.putExtra("latitude", b.getLatitude());
                    intent.putExtra("longitude", b.getLongitude());
                    intent.putExtra("visited", b.getVisited());
                    intent.putExtra("pdate", b.getCreatedon());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        list=databaseHelper.getToVisitedPlaces();
        at=new AdapterTest(this, list);
        lv.setAdapter(at);

        SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(lv),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {

                                Bean b = list.get(position);

                                databaseHelper.deleteFavPlace(b.getId());

                                at.remove(position);
                            }
                        });

        lv.setOnTouchListener(touchListener);
        lv.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {

                    touchListener.undoPendingDismiss();

                    Bean b = list.get(position);

                    Intent intent = new Intent(ToVisit.this, EditLoc.class);
                    intent.putExtra("id", String.valueOf(b.getId()));
                    intent.putExtra("placename", b.getPlacename());
                    intent.putExtra("address", b.getAddress());
                    intent.putExtra("latitude", b.getLatitude());
                    intent.putExtra("longitude", b.getLongitude());
                    intent.putExtra("visited", b.getVisited());
                    intent.putExtra("pdate", b.getCreatedon());
                    startActivity(intent);

                } else {
                    Bean b = list.get(position);

                    Intent intent = new Intent(ToVisit.this, MapsActivity.class);
                    intent.putExtra("id", String.valueOf(b.getId()));
                    intent.putExtra("placename", b.getPlacename());
                    intent.putExtra("address", b.getAddress());
                    intent.putExtra("latitude", b.getLatitude());
                    intent.putExtra("longitude", b.getLongitude());
                    intent.putExtra("visited", b.getVisited());
                    intent.putExtra("pdate", b.getCreatedon());
                    startActivity(intent);
                }
            }
        });

    }
}