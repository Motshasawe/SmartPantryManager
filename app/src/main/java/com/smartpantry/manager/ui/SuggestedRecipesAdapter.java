package com.smartpantry.manager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.manager.R;
import com.smartpantry.manager.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesAdapter extends RecyclerView.Adapter<SuggestedRecipesAdapter.ViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    private final List<Recipe> recipes = new ArrayList<>();
    private final List<Boolean> almostThereFlags = new ArrayList<>();
    private final OnRecipeClickListener listener;

    public SuggestedRecipesAdapter(OnRecipeClickListener listener) {
        this.listener = listener;
    }

    public void setRecipes(List<Recipe> newRecipes) {
        recipes.clear();
        almostThereFlags.clear();
        if (newRecipes != null) {
            recipes.addAll(newRecipes);
        }
        notifyDataSetChanged();
    }

    public void setRecipes(List<Recipe> newRecipes, List<Boolean> almostFlags) {
        recipes.clear();
        almostThereFlags.clear();
        if (newRecipes != null) {
            recipes.addAll(newRecipes);
        }
        if (almostFlags != null) {
            almostThereFlags.addAll(almostFlags);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.textRecipeName.setText(recipe.getName());
        int count = recipe.getIngredients() != null ? recipe.getIngredients().size() : 0;
        boolean almost = position < almostThereFlags.size() && almostThereFlags.get(position);
        if (almost) {
            holder.textRecipeMeta.setText("Almost there - missing 1 ingredient");
            holder.textRecipeMeta.setTextColor(holder.itemView.getContext().getColor(R.color.amber_500));
        } else {
            holder.textRecipeMeta.setText(count + " ingredient" + (count == 1 ? "" : "s"));
            holder.textRecipeMeta.setTextColor(holder.itemView.getContext().getColor(R.color.grey_600));
        }
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textRecipeName;
        final TextView textRecipeMeta;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textRecipeName = itemView.findViewById(R.id.textRecipeName);
            textRecipeMeta = itemView.findViewById(R.id.textRecipeMeta);
        }
    }
}
