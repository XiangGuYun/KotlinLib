# KotlinStorm
abandon old and tedious Java, make android program to be simlple!

## 代码样例

### startActivity简化
```
//in Java
btnTest.setOnClickListener(view->{
          startActivity(new Intent(this, SecondActivity.class)
                  .putExtra("name","lilei")
                  .putExtra("age",12)
                  .putExtra("isMale",false));
      });

//in kotlin
btnTest.click {
  go(SecondActivity::class.java,"name" to "lelei", "age" to 12, "isMale" to false)
}
```

### 组合设置属性
```
//in Java
tv1.setText("hello");
tv2.setText("back");
tv3.setText("66666");
tv1.setTextColor(Color.RED);
tv2.setTextColor(Color.GREEN);
tv3.setTextColor(Color.BLUE);

//in kotlin
Group(tv1,tv2,tv3).text("hello","back","66666").textColor(Color.RED,Color.GREEN,Color.BLUE)
```

### 拼接数组字符串
```
//text1 is "1,2,3,4,5,6,7,8,9,10"
val text1= appendStr((1..10).toList(),","){(1..10).toList()[it].toString()}

//text2 is "mimi,ketty,huahua"
data class Cat(var name:String)
val catList = listOf<Cat>(Cat("mimi"),Cat("ketty"),Cat("huahua"))
val text2= appendStr(catList,","){catList[it].name}
```

## 列表工具RVUtils
### 一般用法
```
RVUtils(rv)//传参是RecyclerView
        .rvMultiAdapter(list,//数据源
        { holder, pos -> //设置属性
            holder.setText(R.id.tvCell,list[pos])
            holder.setText(R.id.tvCell1,list[pos])
            holder.setImageResource(R.id.ivCell,R.mipmap.ic_launcher)
            holder.getView<View>(R.id.cell).click { (pos+1).toast() }
        },{//针对不同的列表项位置来返回不同的布局
            when(it){
                0->0 //对应R.layout.item_head
                list.size-1->2 //对应R.layout.item_body
                else->1 //对应R.layout.item_foot
            }
        },R.layout.item_head,//顶部列表项
        R.layout.item_body,//普通列表项
        R.layout.item_foot)//底部列表项
```
![](/image/p1.png)

