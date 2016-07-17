#lzh-android

分渠道打包相关说明：

1. 分渠道打包需要将manifest中UMENG_CHANNEL对应的value改成"{CHANNEL_VALUE}"

2. 渠道号在工程目录下channel文件中添加


代码结构说明

网络相关：

1. 采用的volley网络框架 + Gson自动解析

2. 网络相关的在com.lzhplus.lzh.net包中

3. 返回数据继承至ApiResponse, 可以自定义一些数据model(com.lzhplus.lzh.model)

4. 如果返回是列表形式，考虑实现IResList接口，因为在BaseRecyclerListFragment封装的列表逻辑中可能会用到

5. 返回的监听，可以用BaseApiListener

	5.1 onError 通常是网络错误

	5.2 onResponseError 通常是业务逻辑错误

	5.3 onResponseSuccess 正常结果

	5.4 getApiResponseType 需要通过这个函数，告诉GSON自动化解析的类型，这样可以一步到位直接从网络数据得到数据model


UI相关

1. 基本采用的Activity+Fragment的结构，页面继承基类BaseFragment就OK

2. BaseActivity基础的Activity类

3. BaseRecyclerListFragment： 采用RecyclerView封装的列表页面

4. 一些自定义控件在com.lzhplus.lzh.ui.widget中


消息通知

	EventBus
	相关event: com.lzhplus.lzh.event


图片库

	Fresco

	DraweeImageView 集成框架
	xml配置介绍
	先启用属性-> xmlns:drawee="http://schemas.android.com/apk/res-auto"
	设置圆形->   drawee:type="circle"

	设置圆角和每个半径->
	            drawee:type="round"
	            drawee:radius_left_top="10"
                drawee:radius_left_bottom="10"
                drawee:radius_right_top="10"
                drawee:radius_right_bottom="10"

    设置加载中图片和scaleType->
                drawee:placeholderScaleType="FIT_XY"
                drawee:placeholderDrawableId="@drawable/ic_launcher"

    设置加载错误图片->
                drawee:failureDrawableId="@drawable/ic_launcher"

    设置图片url->
                drawee:url="https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png"

