# CommonUtils
工具类
### 使用方法
```
compile 'com.dh.commonutilslib:commonutilslib:1.0.0'
```
或者
```
implementation 'com.dh.commonutilslib:commonutilslib:1.0.0'
```
### 主要说明
1. SolarTermUtil：24节气相关工具类，使用前必须先调用init方法：
```
SolarTermUtil.init(context)；
```
2. LunarCalendar：节日工具类，使用前需调用init方法：
```
LunarCalendar.init(context)；
```
3. SharedPreferencesUtil：sharedPreference存储操作类，使用前需调用init方法：
```
SharedPreferencesUtil.init(context, "name", Context.MODE_PRIVATE);
```
4. LunarSolarConverter：公农历互转工具类
5. AndroidBug5497Workaround：Android WebView中的软键盘弹出，可以把布局顶上去
6. BeanUtils：对象属性的拷贝工具类
7. DateUtil：年月日时的天干地支转化工具类
8. BitmapUtils：图片处理工具类
9. ImageUtil：图片加载工具类，使用Glide
10. 其他工具类：TimeUtil，ToastUtils，MD5，Base64，FileUtil等等
