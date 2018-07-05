package lcsd.com.whirlpool.http;

/**
 * Created by Administrator on 2017/6/26.
 */
public class AppConfig {
    public static String mainurl="http://180.76.234.185/";
    /**
     * 首页接口
     * 详情页：上传id=news；
     * 产品宝典：上传id=products；
     * 新闻内容：上传：id
     *培训专题: 上传 id=train;
     * 新品快讯：上传:id=new_products
     * 销售技巧 上传：id=skill
     * 团队活动 上传：id=team
     * 直播平台id=live&cate=zhibo
     *加入收藏  上传: c=fav ,f=add,id;
     * 取消收藏1 上传: c=fav&f=cancel,id;
     *取消收藏2 上传: c=fav&f=delete,id;
     * 我的收藏分类: 上传：c=usercp&f=fav_cate
     * 我的收藏： 上传:35、c=usercp&f=fav&pid(pid为收藏的分类，默认为0调用所有信息)
     * 是否收藏: 上传：c=usercp&f=is_fav和id
     *  搜索：上传id=products&cate=goods（查询所有产品）&keywords=()关键词
     *  考试分类：id=exam
     *  cate        //分类标识
        type=rand   //考试类型，rand随机测试，为空时表示专项测试，默认为空
        psize       //调用考题数量
     top榜：c=usercp&f=rank

     */
    public static String Sy=mainurl+"index.php";
    //版本更新
    public static String request_update=mainurl+"index.php?f=version";
    //分公司和职务
    //上传：c=register
    public static String request_register=mainurl+"index.php";
    /**
     * 考试结果提交
     * Post数据：c=usercp&f=test
     * 信息保存成功，会自动增加积分
     *id        //产品id
     *type      //考试类型，prostest产品测试（默认），randtest随机测试，onetest专项测试
     *为空时，默认产品测试
     */
    public static String request_tijiao=mainurl+"index.php";
}
