package com.regulo.dev.insects;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.regulo.dev.insects.data.Insect;
import com.regulo.dev.insects.data.InsectAsyncTaskLoader;
import com.regulo.dev.insects.data.InsectRecyclerAdapter;
import com.regulo.dev.insects.data.Insects;


public class ListBugsFragment extends Fragment implements
        InsectRecyclerAdapter.RecyclerOnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{

    public interface IOnClickListener {
        void onItemSelected(final Insect insect);
    }

    private IOnClickListener mCallback;

    private RecyclerView mRecyclerView;
    private InsectRecyclerAdapter mInsectRecyclerAdapter;
    boolean sortedBy = false;

    private static final String EXTRA_INSECT_DETAILS = "insectExtra";
    public static final String EXTRA_INSECTS = "insectList";
    public static final String EXTRA_ANSWER = "selectedInsect";

    public static final int ALL_INSECTS_LOADER = 1;
    public static final int NAMES_ALPHABETICALLY_LOADER = 2;
    public static final int MOST_DANGER_LOADER = 3;
    FloatingActionButton fab;
    Toolbar toolbar;

    public static Insects mInsectsList;

    public ListBugsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (IOnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_bugs, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setupToolbar();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInsectRecyclerAdapter = new InsectRecyclerAdapter(getActivity(), this);
        mInsectsList = new Insects(mInsectRecyclerAdapter.getInsectsList());
        setupFloatingActionButton();
        getActivity().getLoaderManager().initLoader(ALL_INSECTS_LOADER, null, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Resources res = getResources();
        boolean showEmptyDetails = res.getBoolean(R.bool.show_empty_details);
        int orientation = res.getConfiguration().orientation;
        Fragment fragment = getFragmentManager().findFragmentById(R.id.details_container);

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && fragment == null && showEmptyDetails) {
            EmptyFragment emptyFragment = new EmptyFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_container, emptyFragment).commit();
        }
    }

    public void setupToolbar(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
    }

    public void setupFloatingActionButton(){
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putParcelableArrayListExtra(EXTRA_INSECTS, mInsectsList);
                intent.putExtra(EXTRA_ANSWER, mInsectRecyclerAdapter.getInsectsList().get(2));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new InsectAsyncTaskLoader(getActivity(), id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mInsectRecyclerAdapter.setData(data);
        mRecyclerView.setAdapter(mInsectRecyclerAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(Insect insect) {
        mCallback.onItemSelected(insect);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(
            new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                if(sortedBy) {
                    //Order alphabetically
                    getActivity().getLoaderManager().initLoader(NAMES_ALPHABETICALLY_LOADER, null, this);
                    sortedBy = false;
                 } else {
                    //Order most danger first
                    getActivity().getLoaderManager().initLoader(MOST_DANGER_LOADER, null, this);
                    sortedBy = true;
                }
                return true;

            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
