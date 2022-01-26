package com.pixelpk.task_assign_kotlin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelpk.task_assign_kotlin.Main_Section.Details_Screen;
import com.pixelpk.task_assign_kotlin.Model.Model_Article;
import com.pixelpk.task_assign_kotlin.R;

import java.util.ArrayList;

public class Adapter_Article extends RecyclerView.Adapter<Adapter_Article.Article_ViewHolder>
{
    ArrayList<Model_Article> model_articles;
    Context context;


    public Adapter_Article(ArrayList<Model_Article> model_articles, Context context)
    {
        this.model_articles = model_articles;
        this.context = context;
    }

    @Override
    public Adapter_Article.Article_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_article, parent, false);
        return new Adapter_Article.Article_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Adapter_Article.Article_ViewHolder holder, int position)
    {
        Model_Article model_article = model_articles.get(position);

        //Getting and setting article
        holder.article_title.setText(model_article.getTitle());

        //Glide to load the image in Recycler View
        Glide.with(context).load(model_article.getImg())
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.article_img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Sending Data to Second Screen via intent

                String article_details = model_article.getDetails();
                String article_img     = model_article.getImg();
                String article_title   = model_article.getTitle();
                String article_date    = model_article.getDate();
                String article_url     = model_article.getUrl();

                Intent intent = new Intent(context, Details_Screen.class);

                intent.putExtra("intent_article_details",article_details);
                intent.putExtra("intent_article_img",article_img);
                intent.putExtra("intent_article_title",article_title);
                intent.putExtra("intent_article_date",article_date);
                intent.putExtra("intent_article_url",article_url);

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount()
    {
        return model_articles.size();
    }

    class Article_ViewHolder extends RecyclerView.ViewHolder
    {
        TextView article_title;
        ImageView article_img;

        public Article_ViewHolder(View itemView)
        {
            super(itemView);

            article_title = itemView.findViewById(R.id.title_item_article);
            article_img  = itemView.findViewById(R.id.img_item_article);
        }
    }

}
