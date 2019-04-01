package com.duolingo.challenges.adapter;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duolingo.challenges.R;
import com.duolingo.challenges.contract.CharacterItemContract;
import com.duolingo.challenges.presenter.CharacterItemPresenter;
import com.duolingo.challenges.usecases.CharacterTextSizeUseCase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterViewHolder extends RecyclerView.ViewHolder
        implements CharacterItemContract.View {

    @BindView(R.id.vg_character_cell)
    ViewGroup vgCharacterCell;
    @BindView(R.id.v_selector)
    View vSelector;
    @BindView(R.id.tv_character)
    TextView tvCharacter;

    private Context context;
    private CharacterItemPresenter presenter;

    private View itemView;
    private final Integer gridSize;
    private final CharacterTextSizeUseCase characterTextSizeUseCase;

    public CharacterViewHolder(
            View itemView,
            Integer gridSize,
            CharacterTextSizeUseCase characterTextSizeUseCase
    ) {
        super(itemView);
        this.itemView = itemView;
        this.gridSize = gridSize;
        this.characterTextSizeUseCase = characterTextSizeUseCase;

        init();
    }

    private void init() {
        ButterKnife.bind(this, itemView);

        context = itemView.getContext();

        presenter = new CharacterItemPresenter(gridSize, characterTextSizeUseCase);
        presenter.setView(this);
        presenter.start();
    }

    public void onBindViewHolder(String character,
                                 int position,
                                 List<Integer> selectedItems,
                                 List<Integer> solutionItems) {
        presenter.onCharacterUpdated(character, position, selectedItems, solutionItems);
    }

    public void onViewRecycled() {
        presenter.destroy();
    }

    @Override
    public void setCharacterTextSize(@DimenRes int textSize) {
        tvCharacter.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.getResources().getDimension(textSize));
    }

    @Override
    public void setCharacterText(String text) {
        tvCharacter.setText(text);
    }

    @Override
    public void showSelector() {
        vSelector.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSelector() {
        vSelector.setVisibility(View.GONE);
    }

    @Override
    public void setCharacterTextColor(int colorRes) {
        tvCharacter.setTextColor(getColor(colorRes));
    }

    @Override
    public void setCellBackgroundColor(int colorRes) {
        vgCharacterCell.setBackgroundColor(getColor(colorRes));
    }

    @ColorInt
    private int getColor(@ColorRes int resource) {
        return ContextCompat.getColor(context, resource);
    }
}