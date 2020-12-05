package com.example.camerabarcodescanner.presentation.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camerabarcodescanner.R;
import com.example.camerabarcodescanner.data.entity.Codigo;

import java.util.ArrayList;
import java.util.List;

public class CodesBarsAdapter extends RecyclerView.Adapter<CodesBarsAdapter.CodesBarsViewHolder> {
    private List<Codigo> codes = new ArrayList<>();

    public void loadItems(List<Codigo> listCodes) {
        codes = listCodes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CodesBarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_bar_item, parent, false);
        return new CodesBarsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CodesBarsViewHolder holder, int position) {
        final Codigo code = codes.get(position);
        holder.bind(code);
    }

    @Override
    public int getItemCount() {
        return this.codes.size();
    }

    static class CodesBarsViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodeBar;
        LinearLayout linearLayout;

        private CodesBarsViewHolder(@NonNull View view) {
            super(view);
            tvCodeBar = view.findViewById(R.id.tvCodeBar);
            linearLayout = view.findViewById(R.id.bgItem);
        }

        private void bind(Codigo code) {
            tvCodeBar.setText(code.valor);
            if (code.isUpload()) {
                linearLayout.setBackgroundColor(Color.GREEN);
            }
        }
    }
}
