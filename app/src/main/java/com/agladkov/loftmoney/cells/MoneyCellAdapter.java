package com.agladkov.loftmoney.cells;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.agladkov.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class MoneyCellAdapter extends RecyclerView.Adapter<MoneyCellAdapter.MoneyViewHolder> {

    private List<MoneyItem> moneyItemList = new ArrayList<>();

    public MoneyCellAdapterClick moneyCellAdapterClick;

    public void setData(List<MoneyItem> moneyItems) {
        moneyItemList.clear();
        moneyItemList.addAll(moneyItems);

        notifyDataSetChanged();
    }

    public List<MoneyItem> getMoneyItemList() {
        return moneyItemList;
    }

    public void updateItem(MoneyItem moneyItem) {
        int itemPosition = moneyItemList.indexOf(moneyItem);
        moneyItemList.set(itemPosition, moneyItem);
        notifyItemChanged(itemPosition);
    }

    public void deleteSelectedItems() {
        List<MoneyItem> selectedItems = new ArrayList<>();
        for (MoneyItem moneyItem : moneyItemList) {
            if (moneyItem.isSelected()) {
                selectedItems.add(moneyItem);
            }
        }

        moneyItemList.removeAll(selectedItems);
        notifyDataSetChanged();
    }

    public void setMoneyCellAdapterClick(MoneyCellAdapterClick moneyCellAdapterClick) {
        this.moneyCellAdapterClick = moneyCellAdapterClick;
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MoneyViewHolder(layoutInflater.inflate(R.layout.cell_money, parent, false),
                moneyCellAdapterClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(moneyItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return moneyItemList.size();
    }

    static class MoneyViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView valueTextView;
        private MoneyCellAdapterClick moneyCellAdapterClick;

        public MoneyViewHolder(@NonNull View itemView, final MoneyCellAdapterClick moneyCellAdapterClick) {
            super(itemView);

            this.moneyCellAdapterClick = moneyCellAdapterClick;

            titleTextView = itemView.findViewById(R.id.moneyCellTitleView);
            valueTextView = itemView.findViewById(R.id.moneyCellValueView);
        }

        public void bind(final MoneyItem moneyItem) {
            titleTextView.setText(moneyItem.getTitle());
            valueTextView.setText(moneyItem.getValue());

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),
                moneyItem.isSelected() ? R.color.cellSelectionColor : android.R.color.white));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moneyCellAdapterClick != null) {
                        moneyCellAdapterClick.onCellClick(moneyItem);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (moneyCellAdapterClick != null) {
                        moneyCellAdapterClick.onLongCellClick(moneyItem);
                    }

                    return true;
                }
            });
        }
    }
}
