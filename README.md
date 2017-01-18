# EX_Recyclerview
A simple ex_recyclerview just like chat list in QQ or WeChat.
## Installation
``` xml
compile 'com.alexanderathust.chatlistview:app:0.0.1'
```
## Usage
### Extending Class
#### EX_ViewHolder<T>

Override 2 methods of it.

``` java
@Override
public void bindData(Integer data);
```

To inject View in the above-view gotten by getAboveView() to bind data.

``` java
@Override
public void bindButtonBelow();
```

To inject View in the below-view gotten by getBelowView() to add some click listener etc.

And there are two default methods floatLine() and removeLine() in EX_ViewHolder we can simply use them.

#### EX_Adapter<T> 

Override 1 method of it.

``` java
@Override
public EX_ViewHolder<Integer> onCreateViewHolder(ViewGroup parent, int viewType);
```

To return an Object that is the instance of the Class extending EX_ViewHolder.
    
### Initialize EX_ChatListView

``` java
setAdapter(EX_Adapter adapter)
```

Set an adapter to your view.

``` java
setBackgroudImageByDrawable(@Nullable Drawable d)
```

Set the background of the list view. (Blank but white color if input null)

``` java
setCanFlash(Boolean canFlash, @Nullable Integer layoutId, @Nullable Integer layoutHeight, @Nullable Integer waitTime)
```

Set whether it can slide down to flash.

``` java
setOnFlashListener(OnFlashListener listener)
```

Set the listener.

``` java
addData(List<T> list) or setData(List<T> list) or deleteData(int pos)
```

These methods are in the Class EX_Adapter.  
	
## Appreciation

If there's anyone else who wants to use this USELESS stuff, I really apprecate a lot. Thanks!
