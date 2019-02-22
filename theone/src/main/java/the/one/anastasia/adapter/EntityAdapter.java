package the.one.anastasia.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityAdapter<T,Holder extends EntityAdapter.BaseHolder> extends BaseAdapter {

    private String TAG="EntityAdapter";
    protected List<T> datas;
    protected Context context;
    public int size;
    public EntityAdapter(Context context)
    {
        datas =new ArrayList<>();
        this.context=context;
    }


    public EntityAdapter(Context context, List<T> data)
    {
        this.datas =new ArrayList<>();
        this.datas.addAll(data);
        size=data.size();
        this.context=context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        if (datas.size()!=0)
        return datas.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        T t=getItem(position);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(getLayoutRes(),parent,false);
            holder= onCreateViewHolder(parent, position, convertView);
        }
        else {
            holder= (Holder) convertView.getTag();
        }
       onBindViewHolder(holder,position,t);
        return convertView;
    }

    /**
     * 清空且向头部添加数据
     * @param data
     */
    public void addHeadData(List<T> data)
    {
        this.datas.clear();
        if(data!=null) {
            this.datas.addAll(data);
            size=data.size();
        }
        notifyDataSetChanged();
    }

    /**
     * 向尾部添加数据
     * @param data
     */
    public void addFootData(List<T> data)
    {
        if(data==null)
        {
            return;
        }
        this.datas.addAll(data);
        size=data.size();
        notifyDataSetChanged();
    }

    /**
     * 向尾部添加数据
     * @param data
     */
    public void addFootData(T data)
    {
        this.datas.add(data);
        size=datas.size();
        notifyDataSetChanged();
    }

    public List<T> getData()
    {
        return this.datas;
    }

    /**
     * 得到资源id
     */
    abstract public int getLayoutRes();

    /**
     * 当convertView 为空时被调用
     *
     * 使用有布局文件的构造方法：
     *       return new ViewHolder(convertView);
     * @param parent
     * @param position
     * @param convertView
     * @return
     */
    abstract public Holder onCreateViewHolder(ViewGroup parent, int position, View convertView);

    /**
     * 刷新数据时调用
     * @param holder
     * @param position
     * @param data
     */
    abstract public void onBindViewHolder(Holder holder, int position,T data);

    static  public class BaseHolder
    {
        protected View convertView;
        public BaseHolder(View convertView){
            if(convertView==null) {
                throw new IllegalStateException("convertView不能为null");
            }
            convertView.setTag(this);
            this.convertView=convertView;
        }
        protected <T extends View> T findViewById(int id)
        {
            return (T) convertView.findViewById(id);
        }

        public View getConvertView()
        {
            return convertView;
        }
    }
    protected void i(String msg)
    {
        Log.i(TAG,msg);
    }
}
