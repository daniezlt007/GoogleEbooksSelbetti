package com.deasystem.googlebooksselbetti.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deasystem.googlebooksselbetti.R;
import com.deasystem.googlebooksselbetti.listener.OnListClickInteractionListener;
import com.deasystem.googlebooksselbetti.model.Ebook;
import com.deasystem.googlebooksselbetti.utils.Common;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FavoritosAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Ebook> mListEbook;
    private List<Ebook> favoritos = new ArrayList<Ebook>();


    public FavoritosAdapter(Context context, List<Ebook> mListEbook) {
        this.context = context;
        this.mListEbook = mListEbook;
    }

    private class FavoritosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView mTextTitle;
        private TextView mTextDetail;
        private ImageView mImageView;
        private OnListClickInteractionListener mOnListClickInteractionListener;

        public FavoritosViewHolder(View itemView) {
            super(itemView);
            this.mTextTitle = itemView.findViewById(R.id.text_view_title);
            this.mTextDetail = itemView.findViewById(R.id.text_view_details);
            this.mImageView = itemView.findViewById(R.id.img_ebook);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListClickInteractionListener.onItemClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            mOnListClickInteractionListener.onItemLongClick(v, getAdapterPosition(), true);
            return true;
        }

        public void setmOnListClickInteractionListener(OnListClickInteractionListener mOnListClickInteractionListener) {
            this.mOnListClickInteractionListener = mOnListClickInteractionListener;
        }
    }


    @Override
    public FavoritosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Instancia o layout para manipulação dos elementos
        View carView = inflater.inflate(R.layout.ebook_list_item, parent, false);
        // Passa a ViewHolder
        return new FavoritosViewHolder(carView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Ebook ebook = this.mListEbook.get(position);
        FavoritosViewHolder viewHolder = (FavoritosViewHolder) holder;
        viewHolder.mTextTitle.setText(ebook.getmTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.bd_grey);
        requestOptions.error(R.drawable.bd_grey);

        Glide.with(context)
                .load(ebook.getFoto())
                .apply(requestOptions)
                .into(viewHolder.mImageView);

        ((FavoritosViewHolder) holder).setmOnListClickInteractionListener(new OnListClickInteractionListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, EbookActivity.class);
                Ebook ebook = mListEbook.get(position);
                intent.putExtra("ebook", ebook);
                context.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(final View view, final int position, boolean isLongClick) {
                Paper.init(view.getContext());

                Common.showMsgConfirm((Activity) view.getContext(), "Atenção", "Retirar da lista de favoritos?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<Ebook> favoritos = Paper.book().read("favoritos");
                        Ebook ebook = favoritos.get(position);
                        //favoritos.remove(ebook);
                        remove(ebook, favoritos);
                        /*notifyItemRemoved(position);
                        notifyDataSetChanged();*/
                        Paper.book().write("favoritos", favoritos);
                    }
                });

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListEbook.size();
    }

    public void remove(Ebook item, ArrayList<Ebook> favoritos) {
        int position = favoritos.indexOf(item);
        favoritos.remove(position);
        notifyItemRemoved(position);
    }


}