package com.pixelpk.task_assign_kotlin.Urls

class Main_Url
{
    // Setting companion object
    companion object
    {
        //Setting Function to access the get_popular_article
        fun get_popular_article(): String
        {
            //Main Url
            val main_url = "https://api.nytimes.com/svc"

            //Popular Article
            val get_popular_article = "$main_url/mostpopular/v2/mostviewed/all-sections/7.json?api-key=PnCGD1A05kutiwyCv0HbXMzey56jnzor"
            return get_popular_article   //Returning popular_site

        }
    }
}