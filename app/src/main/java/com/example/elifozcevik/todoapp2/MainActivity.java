package com.example.elifozcevik.todoapp2;

import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GestureDetectorCompat mDetecror;
    private ScaleGestureDetector gs;

    AlertDialog dialog;
    AlertDialog.Builder builder, builder2;
    EditText input;
    ArrayList<String> todolist;
    ArrayAdapter<String> todolistAdapter;

    ArrayList<String> completedlist;
    ArrayAdapter<String> completedlistAdapter;
    ArrayList<String> arrayList1;

    ArrayList<String> todolisttask;
    ArrayAdapter<String> todolisttaskAdapter;

    ArrayList<String> all;
    ArrayAdapter<String> allAdapter;

    ArrayList<String> arrayList2;
    private ActionMode mActionMode;
    private ImageButton mButton;

    ListView list;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDetecror= new GestureDetectorCompat(this,new MyGestureListner());
        gs=new ScaleGestureDetector(this, new MyPicnhGesture());
        LinearLayout l2=findViewById(R.id.lay);

        l2.setOnTouchListener(new View.OnTouchListener() {
                                  @Override
                                  public boolean onTouch(View v, MotionEvent event) {
                                      gs.onTouchEvent(event);
                                      return true;
                                  }
                              }


        );

        completedlist = new ArrayList();
        completedlistAdapter = new ArrayAdapter<String>(this, R.layout.todolist, completedlist);


        todolist = new ArrayList<>();
        todolistAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, todolist);

        todolisttask = new ArrayList<>();
        todolisttaskAdapter = new ArrayAdapter<String>(this, R.layout.todolist, todolisttask);

        all = new ArrayList<>();
        allAdapter = new ArrayAdapter<String>(this, R.layout.all, all);

        arrayList1 = new ArrayList();
        arrayList2 = new ArrayList();



        mButton=(ImageButton) findViewById(R.id.img);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, mButton);
                popup.getMenuInflater().inflate(R.menu.settings, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.set:

                                return true;

                            default:
                                return false;
                        }

                    }
                    // implement click listener.
                });
                popup.show();
            }

        });






        /*
        TextView txt = findViewById(R.id.textView3);
        txt.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                if (mActionMode != null) return false;

                mActionMode =
                        MainActivity.this.startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });
        */

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_list);

        list = findViewById(R.id.list1);
        list.setAdapter(todolistAdapter);

        registerForContextMenu(list);  //BAK

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Job");
        input = (EditText) new EditText(this);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String jobName = input.getText().toString();
                todolistAdapter.add(jobName);
                arrayList1.add(jobName);
                input.setText("");

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == R.id.nav_camera) {
                            dialog.show();
                        } else if (id == R.id.nav_gallery) {
                            list.setAdapter(completedlistAdapter);

                        } else if (id == R.id.nav_todo) {
                            list.setAdapter(todolistAdapter);

                        } else if (id == R.id.nav_all) {
                            allAdapter.clear();
                            for (int i = 0; i < todolist.size(); i++)
                                allAdapter.add(todolist.get(i));

                            for (int i = 0; i < completedlist.size(); i++)
                                allAdapter.add(completedlist.get(i));

                            list.setAdapter(allAdapter);

                        } else System.exit(0);


                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    public void add(View v) {

        dialog.show();
    }


    public boolean onTouchEvent(MotionEvent event){
        //Log.d("UBE", event.toString());
        this.mDetecror.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


   public void itemclicked(final View v) {
        builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Warning!");
        builder2.setMessage("Task completed?");

        builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                TextView tx = (TextView) v;
                completedlistAdapter.add(tx.getText().toString());
                int i = 0;
                for (i = 0; i < todolist.size(); i++)
                    if (todolist.get(i).equals(tx.getText().toString()))
                        break;
                arrayList2.add(todolist.get(i));

                todolist.remove(i);
                arrayList1.remove(i);
                todolistAdapter.notifyDataSetChanged();

            }
        });
        builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder2.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                dialog.show();
                return true;
            case R.id.option_favorites:
                list.setAdapter(completedlistAdapter);
                return true;
            case R.id.item2:
                list.setAdapter(todolistAdapter);
                return true;
            case R.id.item3:
                allAdapter.clear();
                for (int i = 0; i < todolist.size(); i++)
                    allAdapter.add(todolist.get(i));
                for (int i = 0; i < completedlist.size(); i++)
                    allAdapter.add(completedlist.get(i));
                list.setAdapter(allAdapter);
                return true;
            case R.id.item4:
                System.exit(0);
                return true;

            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(Gravity.START)==false)
                    mDrawerLayout.openDrawer(Gravity.START);
                else
                    mDrawerLayout.closeDrawer(Gravity.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_delete:

               //todolist.remove();
                // arrayList1.remove();
                todolistAdapter.notifyDataSetChanged();

                return true;
            case R.id.context_update:

                return true;
            case R.id.context_completed:

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }







    public ActionMode.Callback mActionModeCallback =
            new ActionMode.Callback() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.nev_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false; // Return false if nothing is done.
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    mActionMode = null;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_camera:
                            dialog.show();
                            return true;
                        default:
                            return false;
                    }
                }

            };



}
