# CommonUtils
工具类
#### 主要说明
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
4、LunarSolarConverter：公农历互转工具类
