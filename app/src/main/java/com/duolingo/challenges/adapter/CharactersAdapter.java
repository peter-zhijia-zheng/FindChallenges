package com.duolingo.challenges.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.duolingo.challenges.R;
import com.duolingo.challenges.usecases.CharacterTextSizeUseCase;
import com.duolingo.challenges.usecases.ScreenSizeUseCase;

import java.util.ArrayList;
import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Integer gridSize;
    private ScreenSizeUseCase screenSizeUseCase;
    private CharacterTextSizeUseCase characterTextUseCase;

    private LayoutInflater layoutInflater;
    private List<String> characters = new ArrayList<>();
    private List<Integer> selectedItems = new ArrayList<>();
    private List<Integer> solutionItems = new ArrayList<>();
    private int cellSize;

    public CharactersAdapter(
            Context context,
            Integer gridSize,
            ScreenSizeUseCase screenSizeUseCase,
            CharacterTextSizeUseCase characterTextUseCase) {
        this.context = context;
        this.gridSize = gridSize;
        this.screenSizeUseCase = screenSizeUseCase;
        this.characterTextUseCase = characterTextUseCase;

        init();
    }

    private void init() {
        layoutInflater = LayoutInflater.from(context);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.character_grid_margin);
        int width = screenSizeUseCase.getScreenWidth() - (margin * 2);
        cellSize = width / gridSize;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.listitem_character, parent, false);
        setViewLayoutParams(view);
        return new CharacterViewHolder(view, gridSize, characterTextUseCase);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        String character = characters.get(position);
        CharacterViewHolder characterViewHolder = (CharacterViewHolder) viewHolder;
        characterViewHolder.onBindViewHolder(
                character,
                position,
                selectedItems,
                solutionItems);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        if (holder instanceof CharacterViewHolder) {
            ((CharacterViewHolder) holder).onViewRecycled();
        }
    }

    public void setCharacters(List<String> charactersList) {
        characters.clear();
        characters.addAll(charactersList);
        notifyDataSetChanged();
    }

    public void setSelectedItems(List<Integer> positions) {
        selectedItems.clear();
        selectedItems.addAll(positions);
        notifyDataSetChanged();
    }

    public void setSolutionItems(List<Integer> positions) {
        solutionItems.clear();
        solutionItems.addAll(positions);
        notifyDataSetChanged();
    }

    private void setViewLayoutParams(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(cellSize, cellSize);
        view.setLayoutParams(params);
    }

}
