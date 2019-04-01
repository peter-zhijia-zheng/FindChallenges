package com.duolingo.challenges.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.duolingo.challenges.R;
import com.duolingo.challenges.adapter.CharactersAdapter;
import com.duolingo.challenges.common.RecyclerTouchListener;
import com.duolingo.challenges.contract.TranslationsContract;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.presenter.TranslationsPresenter;
import com.duolingo.challenges.ui.base.BaseFragment;
import com.duolingo.challenges.usecases.CharacterTextSizeUseCase;
import com.duolingo.challenges.usecases.CoordinatesArrayUseCase;
import com.duolingo.challenges.usecases.CoordinatesComparatorUseCase;
import com.duolingo.challenges.usecases.FlagSelectorUseCase;
import com.duolingo.challenges.usecases.PositionCoordinateUseCase;
import com.duolingo.challenges.usecases.ScreenSizeUseCase;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TranslationsFragment extends BaseFragment implements
        TranslationsContract.View {
    private static final float NO_TRANSLATION_Y = 0f;
    private static final float TRANSLATION_Y = 200f;
    private static final String KEY_TRANSLATION = "KEY_TRANSLATION";

    public static Fragment newInstance(Translation translation) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_TRANSLATION, translation);
        TranslationsFragment fragment = new TranslationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ScreenSizeUseCase screenSizeUseCase;
    @Inject
    CharacterTextSizeUseCase characterTextUseCase;
    @Inject
    PositionCoordinateUseCase positionCoordinateUseCase;
    @Inject
    CoordinatesArrayUseCase coordinatesArrayUseCase;
    @Inject
    CoordinatesComparatorUseCase coordinateComparatorUseCase;
    @Inject
    FlagSelectorUseCase flagSelectorUseCase;

    @BindView(R.id.iv_source)
    ImageView ivSource;
    @BindView(R.id.tv_source_word)
    TextView tvSourceWord;
    @BindView(R.id.iv_target)
    ImageView ivTarget;
    @BindView(R.id.rv_translations)
    RecyclerView rvTranslations;
    @BindView(R.id.tv_solutions_ratio)
    TextView tvSolutionRatio;
    @BindView(R.id.btn_next)
    Button btnNext;

    private FragmentContainer container;
    private TranslationsPresenter presenter;
    private Translation translation;
    private CharactersAdapter charactersAdapter;
    private RecyclerTouchListener recyclerViewTouchListener;

    private RecyclerTouchListener.ItemTouchListener itemTouchListener = new RecyclerTouchListener.ItemTouchListener() {
        @Override
        public void onItemTouched(int position, int eventAction) {
            presenter.onCharacterTouched(position, eventAction);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        container = (FragmentContainer) (parentFragment == null ? context : parentFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        translation = getArguments().getParcelable(KEY_TRANSLATION);

        recyclerViewTouchListener = new RecyclerTouchListener(itemTouchListener);

        charactersAdapter = new CharactersAdapter(
                getActivity(),
                translation.gridSize,
                screenSizeUseCase,
                characterTextUseCase);

        presenter = new TranslationsPresenter(
                translation,
                positionCoordinateUseCase,
                coordinatesArrayUseCase,
                coordinateComparatorUseCase,
                flagSelectorUseCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translations, container, false);
        bindView(this, view);

        tvSourceWord.setText(translation.word);

        int columnCount = translation.gridSize;

        rvTranslations.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        rvTranslations.setAdapter(charactersAdapter);
        rvTranslations.setHasFixedSize(true);

        presenter.setView(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.start(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstanceState(outState);
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                container.onGridCompleted();
                break;
        }
    }

    @Override
    public void startListeningTouchEvents() {
        rvTranslations.addOnItemTouchListener(recyclerViewTouchListener);
    }

    @Override
    public void stopListeningTouchEvents() {
        rvTranslations.removeOnItemTouchListener(recyclerViewTouchListener);
    }

    @Override
    public void setCharacters(List<String> characters) {
        charactersAdapter.setCharacters(characters);
    }

    @Override
    public void setSourceFlag(@DrawableRes int resource) {
        ivSource.setImageResource(resource);
    }

    @Override
    public void setTargetFlag(@DrawableRes int resource) {
        ivTarget.setImageResource(resource);
    }

    @Override
    public void setSelectedItems(List<Integer> positions) {
        charactersAdapter.setSelectedItems(positions);
    }

    @Override
    public void setSolutionItems(List<Integer> positions) {
        charactersAdapter.setSolutionItems(positions);
    }

    @Override
    public void updateSolutionsRatio(int found, int expected) {
        tvSolutionRatio.setText(getString(R.string.translation_solution_ratio, found, expected));
    }

    @Override
    public void showNextButton() {
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setTranslationY(TRANSLATION_Y);
        btnNext.animate().translationY(NO_TRANSLATION_Y);
    }

    interface FragmentContainer {
        void onGridCompleted();
    }

}