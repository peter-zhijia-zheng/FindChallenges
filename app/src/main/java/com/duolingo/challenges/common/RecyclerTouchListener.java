package com.duolingo.challenges.common;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private static final int INVALID_POSITION = -1;
    private Rect rect;
    private int lastNotifiedPosition = -1;
    private final ItemTouchListener itemTouchListener;

    public RecyclerTouchListener(ItemTouchListener itemTouchListener) {
        this.itemTouchListener = itemTouchListener;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
        View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            rect = new Rect(
                    recyclerView.getLeft(),
                    recyclerView.getTop(),
                    recyclerView.getRight(),
                    recyclerView.getBottom());
        }

        if (action == MotionEvent.ACTION_MOVE && rect != null) {
            if (!rect.contains(
                    (int) (recyclerView.getLeft() + event.getX()),
                    (int) (recyclerView.getTop() + event.getY()))) {
                notifyListener(INVALID_POSITION, MotionEvent.ACTION_OUTSIDE);
                lastNotifiedPosition = INVALID_POSITION;
                return false;
            }
        }

        if (child != null) {
            int position = recyclerView.getChildAdapterPosition(child);
            if (position != lastNotifiedPosition) {
                lastNotifiedPosition = position;
                notifyListener(lastNotifiedPosition, event.getAction());
            }
        }

        if (action == MotionEvent.ACTION_UP) {
            lastNotifiedPosition = INVALID_POSITION;
            notifyListener(lastNotifiedPosition, event.getAction());
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // Don't do anything
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // Don't do anything
    }

    private void notifyListener(int position, int eventAction) {
        itemTouchListener.onItemTouched(position, eventAction);
    }

    public interface ItemTouchListener {
        void onItemTouched(int position, int eventAction);
    }
}