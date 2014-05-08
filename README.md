9GAG-Android (unofficial)
===================================
	9GAG 安卓客户端，个人版。
	界面偏小清新，基本符合Android Design。（仅支持安卓4.0+）
	
	本人大三学生一枚，详见应用中的about页面。
	Email: chyechye@foxmail.com
	
###图赏
	![NineGag](https://raw.github.com/Mixiaoxiao/NineGag/master/Screenshots/device-all.jpg)

  
### 几个特点
	1.完美的支持安卓4.4的TranslucentStatus与TranslucentNavigation特性，并适当给通知栏与导航栏着色使得整个界面更和谐
	2.个人改进的支持刷新的ListView，顶部与底部使用带进度的圆形表示拉动状态
	3.查看大图使用精确的坐标缩放动画，并使用渐现的高斯模糊背景衬托，图片drawable裁剪遵循“横向充满，长图顶部对齐”规则
	4.tab栏没有使用actionBar自带的tab功能，使用PagerSlidingTabStrip（gitHub开源项目），支持了tab指示条的跟随ViewPager滑动而滑动
	5.tab栏支持“QuickReturn”效果
	6.基本完整style化的ActionBar
	7.ActionBar标题字体自定义
	8.Toast自定义
	9.支持分享图片+文本，经测试微信与微博均可正常接收图片与文本信息，不排除有些应用只可获得图片或文本单一分享内容
	10.自定义activity切换动画与安卓4.4完全一致（anim的xml从安卓源码中提取....）

### 新增英中翻译
	由于9gag为英文，故增加了翻译的功能
	翻译使用有道翻译的openapi，项目源码中的key请勿用于其他用途！！！
	打开大图之后会自动进行翻译标题
	点击标题文字可以进行选择标题句子中的单词进行单个翻译
	所有翻译结果都会自动保存在本地数据库，所有查询翻译优先遍历数据库文件，否则从网络获取翻译

### 关于GIF图
	由于使用了第三方的9gag接口，故不支持GIF图
	GIF图加载代码已经写好，在com.mixiaoxiao.ninegag.fragment.ImageFragment 中的checkGif()函数。
	
### 关于aFinal框架
	aFinal的FinalDb不支持在不同的数据库文件内建立相同的对于bean的表，个人对此进行了修改。
	aFinal的bitmap模块在调用aFinal本身的asyncTask的时候部分情况下会抛出异常runtimeException，个人对此进行了忽略，以保证程序的不崩溃。
	
###关于SwipeBack
	效果与安卓4.4的TranslucentStatus存在一定冲突，故舍去不用。

### 关于Mxx开头的Java类
    Mxx为Mixiaoxiao的缩写，Mxx开头的java类基本都是原创
        
### 注意
	这是初次提交的版本，有什么问题请联系我的邮箱
	或者在eoe论坛帖子下面回复
	
    