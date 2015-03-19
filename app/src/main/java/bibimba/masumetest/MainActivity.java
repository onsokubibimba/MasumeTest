package bibimba.masumetest;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;


public class MainActivity extends ActionBarActivity {

    private SurfaceView surfaceView;
    private gamewindow gv;

    // メッセージ表示領域用のListView
    private ListView lvLog;
    private ArrayAdapter<String> lvLogItemsAdapter;

    private final int TESTI = 1;

    // 宝箱の処理
    private void checkChest() {
        Item item = this.gv.openChest();
        if (item != null) {
            this.lvLogItemsAdapter.insert(item.getName() + "を手に入れた！", 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //テスト用に切り替え
        if (TESTI == 0) {
            gv = new gamewindow(this);
            setContentView(gv);
        } else {
            setContentView(R.layout.activity_main);
            surfaceView = (SurfaceView)findViewById(R.id.gameView);

            // WindowManagerのインスタンス取得
            WindowManager wm = getWindowManager();
            // Displayのインスタンス取得
            Display disp = wm.getDefaultDisplay();
            Point size = new Point();
            disp.getSize(size);

            //gv.setBottom(size.y / 2);
            surfaceView.layout(0,0,size.x,size.y / 2);
            gv = new gamewindow(this,surfaceView);

            this.lvLog = (ListView)findViewById(R.id.lvLog);
            this.lvLogItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            this.lvLog.setAdapter(this.lvLogItemsAdapter);

            Button btn = (Button)findViewById(R.id.button_up);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!gv.isMoving) {
                        gv.direction = 3;
                        gv.isMoving = true;
                    }
                    checkChest();

                }
            });

            Button btn2 = (Button)findViewById(R.id.button_down);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!gv.isMoving) {
                        gv.direction = 0;
                        gv.isMoving = true;
                    }
                    checkChest();

                }
            });

            Button btn3 = (Button)findViewById(R.id.button_left);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!gv.isMoving) {
                        gv.direction = 1;
                        gv.isMoving = true;
                    }
                    checkChest();
                }
            });

            Button btn4 = (Button)findViewById(R.id.button_right);
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!gv.isMoving) {
                        gv.direction = 2;
                        gv.isMoving = true;
                    }
                    checkChest();
                }
            });

            Switch sb = (Switch)findViewById(R.id.MoveTypeSwitch);
            sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        gv.movetype = 1;
                    } else {
                        gv.movetype = 0;
                    }
                }
            });

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
