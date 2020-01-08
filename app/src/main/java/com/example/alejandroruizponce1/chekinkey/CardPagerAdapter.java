package com.example.alejandroruizponce1.chekinkey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private Context context;
    private float mBaseElevation;

    public CardPagerAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        View.OnClickListener onClickListener = null;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Reserva " + position + " pulsada!");
                Intent intent_next = new Intent(context, BookingExpandedActivity.class);
                context.startActivity(intent_next);
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
                }
            }
        };

        cardView.setOnClickListener(onClickListener);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.nameHotelTextView);
        titleTextView.setText(item.getTitle());
    }

}
