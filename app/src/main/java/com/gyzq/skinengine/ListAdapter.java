package com.gyzq.skinengine;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author: liujie
 * @date: 2021/6/8
 * @description:
 */
public class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder>  {

    public ListAdapter(List<String> mData) {
        super(R.layout.item_list, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text,item);
    }
}
