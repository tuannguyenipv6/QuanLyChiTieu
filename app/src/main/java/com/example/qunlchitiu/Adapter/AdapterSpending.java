package com.example.qunlchitiu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qunlchitiu.Object.Spending;
import com.example.qunlchitiu.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterSpending extends RecyclerView.Adapter<AdapterSpending.ViewHolderSpending> {
    private List<Spending> Spendings;
    //set data
    public void setSpendings(List<Spending> spendings) {
        Spendings = spendings;
        notifyDataSetChanged();
    }
    //Contructor
    public AdapterSpending(List<Spending> spendings) {
        Spendings = spendings;
    }

    @NonNull
    @Override
    public ViewHolderSpending onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spending, parent, false);
        return new ViewHolderSpending(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSpending holder, int position) {
        Spending spending = Spendings.get(position);
        if (spending == null){
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("E, dd/MM/yyyy-HH:mm");
        String strDate = formatter.format(spending.getmTime());

        holder.txConten.setText(spending.getmExpenses());
        holder.txMoney.setText(spending.getmMoney() + " VNĐ");
        holder.txDate.setText(strDate);
    }

    @Override
    public int getItemCount() {
        if (Spendings != null){
            return Spendings.size();
        }
        return 0;

    }

    public class ViewHolderSpending extends RecyclerView.ViewHolder {
        TextView txConten, txMoney, txDate;
        public ViewHolderSpending(@NonNull View itemView) {
            super(itemView);
            txConten = itemView.findViewById(R.id.itConten);
            txMoney = itemView.findViewById(R.id.itMoney);
            txDate = itemView.findViewById(R.id.itDate);
        }
    }
}
