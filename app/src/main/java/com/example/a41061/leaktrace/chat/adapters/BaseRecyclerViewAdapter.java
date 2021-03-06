package com.example.a41061.leaktrace.chat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;


/**
 * @author abtion.
 * @since 17/8/12 16:54.
 * email caiheng@hrsoft.net
 */

public abstract class BaseRecyclerViewAdapter<Data> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER_ITEM = 2;
    public static final int FOOTER_ITEM = 1;
    public static final int DATA_ITEM = 0;
    protected boolean hasRefreshFooter = false;
    protected boolean hasHeader = false;
    private List<Data> dataList;
    protected Context context;
    protected LayoutInflater inflater;
    private OnItemClicked<Data> onItemClickedListener;

    public BaseRecyclerViewAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置数据
     *
     * @param data 数据
     */
    public void setData(Collection<Data> data) {
        this.dataList.clear();
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加一条数据
     *
     * @param data 数据
     */
    public void add(Data data) {
        this.dataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加多条数据
     *
     * @param collection 数据
     */
    public void addAll(Collection<Data> collection) {
        this.dataList.addAll(collection);
        notifyDataSetChanged();
    }

    /**
     * 移除数据
     *
     * @param data 移除的数据
     */
    public void remove(Data data) {
        this.dataList.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 清空列表
     */
    public void clear() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        notifyDataSetChanged();
    }

    public void hasRefreshFooter(boolean hasRefreshFooter) {
        this.hasRefreshFooter = hasRefreshFooter;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }


    /**
     * 获取当前列表的数据
     */
    public List<Data> getListData() {
        return this.dataList;
    }


    /**
     * 获取position 处数据
     */
    public Data getItem(int position) {
        return dataList.get(position);
    }

    /**
     * 获取当前viewType（有底部刷新布局时）
     *
     * @param position pos
     * @return type
     */
    @Override
    public int getItemViewType(int position) {

        if (hasHeader&&hasRefreshFooter){
            if (position==1){
                return HEADER_ITEM;
            }else {
                return position<dataList.size()+1?DATA_ITEM:FOOTER_ITEM;
            }
        }else if (hasHeader){
            if (position==1){
                return HEADER_ITEM;
            }else {
                return DATA_ITEM;
            }
        }else if (hasRefreshFooter){
            if (position<dataList.size()){
                return DATA_ITEM;
            }else {
                return FOOTER_ITEM;
            }
        }else {
            return super.getItemViewType(position);
        }
    }

    /**
     * 绑定holder
     *
     * @param holder   holder
     * @param position position
     */

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if ((!hasHeader)&&position<dataList.size()) {
            Data data = dataList.get(position);
            ((ViewHolder<Data>) holder).bind(data);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(dataList.get(position), (ViewHolder) holder);
                    }
                }
            });
        }else if (hasHeader&&(position<dataList.size()+1)&&position>0){
            Data data = dataList.get(position-1);
            ((ViewHolder<Data>) holder).bind(data);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(dataList.get(position-1), (ViewHolder) holder);
                    }
                }
            });
        }
    }

    /**
     * 获取总数量
     *
     * @return num
     */
    @Override
    public int getItemCount() {
//        if (!hasRefreshFooter) {
//            return dataList == null ? 0 : dataList.size();
//        } else {
//            return dataList == null ? 1 : dataList.size() + 1;
//        }
        if ((!hasHeader) && (!hasRefreshFooter)) {
            return dataList == null ? 0 : dataList.size();
        } else if (hasHeader && hasRefreshFooter) {
            return dataList == null ? 2 : dataList.size() + 2;
        } else {
            return dataList == null ? 1 : dataList.size() + 1;
        }
    }


    /**
     * ContentViewHolder
     *
     * @param <Data>
     */
    public abstract static class ViewHolder<Data> extends RecyclerView.ViewHolder {
        private Data mData;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(Data data) {
            mData = data;
            onBind(data);
        }

        /**
         * 绑定数据
         * @param data 数据源
         */
        protected abstract void onBind(Data data);
    }

    /**
     * 底部刷新的holder
     */
    public abstract static class RefreshFooterHolder extends RecyclerView.ViewHolder {

        public RefreshFooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * Header holder
     */
    public abstract static class BaseHeaderHolder<Data> extends RecyclerView.ViewHolder {
        private Data mData;

        public BaseHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Data data) {
            mData = data;
            onBind(data);
        }

        //绑定数据
        protected abstract void onBind(Data data);
    }

    /**
     * 设置点击事件监听
     */
    public void setOnItemClickedListener(OnItemClicked<Data> onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }


    /**
     * 点击事件接口
     */
    public interface OnItemClicked<Data> {
        void onItemClicked(Data data, ViewHolder holder);
    }

}
