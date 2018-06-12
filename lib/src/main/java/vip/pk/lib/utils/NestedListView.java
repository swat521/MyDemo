/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vip.pk.pklib.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 专门嵌套使用的ListView，重写其onMeasure()方法使其显示所有数据不会出现滚动条
 */
public class NestedListView extends ListView {
	  
    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);   
    }   
  
    public NestedListView(Context context) {
        super(context);   
    }   
  
    public NestedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);   
    } 
    
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);   
    }   
}
