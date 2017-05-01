package dev.urotea.sudokubreaker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final static int SUDOKU_COL = 9;
    private final static int SUDOKU_ROW = 9;
    private final static int SUDOKU_AREA = 9;

    private List<TextView> sudokuList;
    private List<GridLayout> sudokuAreaList;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @ViewById
    GridLayout left_up;

    @ViewById
    GridLayout middle_up;

    @ViewById
    GridLayout right_up;

    @ViewById
    GridLayout left_middle;

    @ViewById
    GridLayout middle_middle;

    @ViewById
    GridLayout right_middle;

    @ViewById
    GridLayout left_bottom;

    @ViewById
    GridLayout middle_bottom;

    @ViewById
    GridLayout right_bottom;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        sudokuList = new ArrayList<>();
        for(int i=0;i<SUDOKU_COL * SUDOKU_ROW; i+=1) {
            sudokuList.add(new TextView(this));
        }
        sudokuAreaList = new ArrayList<>();
        sudokuAreaList.add(left_up);
        sudokuAreaList.add(middle_up);
        sudokuAreaList.add(right_up);
        sudokuAreaList.add(left_middle);
        sudokuAreaList.add(middle_middle);
        sudokuAreaList.add(right_middle);
        sudokuAreaList.add(left_bottom);
        sudokuAreaList.add(middle_bottom);
        sudokuAreaList.add(right_bottom);

        int row = 0, col = 0, areaCount = 0;
        for(TextView textView : sudokuList) {
            textView.setText(row+","+col);
            textView.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(row), GridLayout.spec(col)
            ));
            sudokuAreaList.get(areaCount).addView(textView);
            col += 1;
            col %= 3;
            row = col == 0 ? row + 1 : row;
            row %= 3;
            areaCount = (col == 0 && row == 0) ? areaCount + 1 : areaCount;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
