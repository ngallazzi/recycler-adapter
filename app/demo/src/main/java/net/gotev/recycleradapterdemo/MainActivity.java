package net.gotev.recycleradapterdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import net.gotev.recycleradapter.RecyclerAdapter;
import net.gotev.recycleradapterdemo.leavebehind.MyLeaveBehindItem;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerAdapter adapter;
    private Random random;

    private Random getRandom() {
        if (random == null) {
            random = new Random(System.currentTimeMillis());
        }
        return random;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new RecyclerAdapter();
        adapter.setEmptyItem(new EmptyItem(getString(R.string.empty_list)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.enableDragDrop(recyclerView);

        adapter.add(new MyLeaveBehindItem("swipe to left to leave behind", "option"));

        for (int i = 0; i < getRandom().nextInt(200) + 50; i++) {
            if (i % 2 == 0)
                adapter.add(new ExampleItem(this, "example item " + i));
            else
                adapter.add(new TextWithButtonItem("text with button " + i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_demo:
                SyncActivity.show(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.remove_all_items_of_a_kind)
    public void onRemoveAllItemsOfAkind() {
        adapter.removeAllItemsWithClass(ExampleItem.class);
    }

    @OnClick(R.id.remove_last_item_of_a_kind)
    public void onRemoveLastItemOfAkind() {
        adapter.removeLastItemWithClass(TextWithButtonItem.class);
    }

    @OnClick(R.id.add_item)
    public void onAddItem() {
        adapter.add(new ExampleItem(this, "added item " + UUID.randomUUID().toString()));
    }

    @OnTextChanged(R.id.search)
    public void onSearchTextChanged(CharSequence search) {
        adapter.filter(search.toString());
    }
}
