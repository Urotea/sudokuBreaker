package dev.urotea.sudokubreaker;


import android.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;

import dev.urotea.sudokubreaker.Fragment.AreaFragment;
import dev.urotea.sudokubreaker.Model.AreaModel;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AreaFragment.OnFragmentInteractionListener {
    private final static int SUDOKU_COL = 9;
    private final static int SUDOKU_ROW = 9;
    private final static int SUDOKU_AREA = 9;

    @ViewsById({R.id.left_up, R.id.middle_up, R.id.right_up,
                R.id.left_middle, R.id.middle_middle, R.id.right_middle,
                R.id.left_bottom, R.id.middle_bottom, R.id.right_bottom})
    List<GridLayout> sudokuAreaList;

    /** それぞれのエリアで保持しているデータと、エリアのタグのリスト */
    private List<AreaModel> areaModelList;

    /** 描画しているすべてのTextViewを格納したリスト*/
    private List<TextView> sudokuList;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @ViewById(R.id.calc_button)
    Button calcButton;

    /** Fragmentを管理する変数*/
    private FragmentTransaction transaction = null;

    /** 現在表示しているフラグメント*/
    private AreaFragment fragment = null;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        sudokuList = new ArrayList<>();
        for (int i = 0; i < SUDOKU_COL * SUDOKU_ROW; i += 1) {
            sudokuList.add(new TextView(this));
        }

        // それぞれのエリアの内容と識別子を作成
        areaModelList = new ArrayList<>();
        for(int i=0;i<SUDOKU_AREA; i+=1) {
            areaModelList.add(new AreaModel());
        }

        int row = 0, col = 0, areaCount = 0;
        for (TextView textView : sudokuList) {
            textView.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(row), GridLayout.spec(col)
            ));
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);

            // setWidthはpixelなのでdipに変換する
            textView.setWidth((int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
            textView.setHeight((int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
            textView.setBackgroundResource(R.drawable.simple_frame);
            sudokuAreaList.get(areaCount).addView(textView);
            areaModelList.get(areaCount).addList("");
            col += 1;
            col %= 3;
            row = col == 0 ? row + 1 : row;
            row %= 3;
            areaCount = (col == 0 && row == 0) ? areaCount + 1 : areaCount;
        }
        // Gridレイアウトにタグを設定し、後で判別可能に
        for(int i=0;i<sudokuAreaList.size();i+=1) {
            sudokuAreaList.get(i).setTag(i);
            areaModelList.get(i).setTag(i);
        }
    }

    @Click({R.id.left_up, R.id.middle_up, R.id.right_up,
            R.id.left_middle, R.id.middle_middle, R.id.right_middle,
            R.id.left_bottom, R.id.middle_bottom, R.id.right_bottom})
    void layoutsClicked(View v) {
        if(transaction != null && !transaction.isEmpty()) return;

        fragment = AreaFragment.newInstance(areaModelList.get(
                Integer.parseInt(v.getTag().toString())));
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment)
                .addToBackStack(null).commit();
        calcButton.setVisibility(View.INVISIBLE);
    }

    @Click(R.id.calc_button)
    void calcButtonClicked() {
        Toast.makeText(this, "計算開始", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        transaction = null;
        calcButton.setVisibility(View.VISIBLE);
        super.onBackPressed();

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

    @Override
    public void onClickSubmitButton(AreaModel areaModel) {
        getFragmentManager().popBackStack();
        calcButton.setVisibility(View.VISIBLE);
        transaction = null;
        setModeltoUI(areaModel);
    }

    private void setModeltoUI(AreaModel model) {
        areaModelList.set(model.getTag(), model);
        List<String> tmp = model.getList();
        for(int i=model.getTag() * SUDOKU_AREA,j=0; i < (model.getTag() + 1)*SUDOKU_AREA;i+=1,j+=1) {
            sudokuList.get(i).setText(tmp.get(j));
        }
    }
}
