package com.example.jobseekerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseekerapp.Models.Faq;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    private List<Faq> faqList;
    private OnFaqClickListener listener;

    public interface OnFaqClickListener {
        void onFaqClick(Faq faq);
    }

    public FaqAdapter(List<Faq> faqList, OnFaqClickListener listener) {
        this.faqList = faqList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        Faq faq = faqList.get(position);
        holder.txtQuestion.setText(faq.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFaqClick(faq);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    static class FaqViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion;

        FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txt_question);
        }
    }
}
