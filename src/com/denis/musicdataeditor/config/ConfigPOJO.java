package com.denis.musicdataeditor.config;

import com.google.gson.annotations.SerializedName;

public class ConfigPOJO
{
    //@SerializedName("mxm_api_key")
    private String mxm_api_key;

    //@SerializedName("lib_dir")
    private String lib_dir;

    //@SerializedName("filter_search")
    private int filter_search;

    public String getMxm_api_key()
    {
        return mxm_api_key;
    }

    public void setMxm_api_key(String mxm_api_key)
    {
        this.mxm_api_key = mxm_api_key;
    }

    public String getLib_dir()
    {
        return lib_dir;
    }

    public void setLib_dir(String lib_dir)
    {
        this.lib_dir = lib_dir;
    }

    public int getFilter_search()
    {
        return filter_search;
    }

    public void setFilter_search(int filter_search)
    {
        this.filter_search = filter_search;
    }
}
