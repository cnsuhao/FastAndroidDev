package com.ijustyce.diancan.constant;

/**
 * Created by yc on 16-3-19.    通用常量
 */
public class Constant {

    public static final String FILE_PATH = "loveNews";

    public static final int USER = 0;
    public static final int ADMIN = 1;
    public static final int APPID = 3;

    public static final int INFO_0 = 0; //  看诊信息
    public static final int INFO_1 = 1; //  体检信息

    public static final String LIST_TUIJIAN = "http://api.sina.cn/sinago/list.json?uid=" +
            "3f9e4aa687c55acd&loading_ad_timestamp=0&platfrom_version=4.4.2&behavior=" +
            "auto&wm=b207&oldchwm=13600_0002&imei=A0000038BF5B606&from=6049595012&" +
            "connection_type=2&chwm=13600_0002&AndroidID=43e9ea9e65ffdd6811ea5539107c41d7" +
            "&v=1&IMEI=ef2606f6835a96c91387e5a0c366b3c9&length=20&MAC=" +
            "5b8643686b7db4651f23d52c49f669d8&channel=news_tuijian";

    public static final String LIST_TOP = "http://api.sina.cn/sinago/list.json?uid=&loading_ad_timestamp=0" +
            "&platfrom_version=4.4.2&behavior=manual&wm=b207&imei=A0000038BF5B606&from=" +
            "&connection_type=2&chwm=&AndroidID=&v=1&IMEI=ef2606f6835a96c91387e5a0c366b3c9&replaced_flag=0" +
            "&pull_direction=down&MAC=5b8643686b7db4651f23d52c49f669d8&channel=news_toutiao";

    public static final String LIST_YULE = "http://api.sina.cn/sinago/list.json?uid=171717171" +
            "&loading_ad_timestamp=0&platfrom_version=4.4.2&behavior=auto&wm=b207&oldchwm=13600_0002" +
            "&imei=A0000038BF5B606&from=6049595012&connection_type=2&chwm=13600_0002&AndroidID=" +
            "43e9ea9e65ffdd6811ea5539107c41d7&v=1&IMEI=ef2606f6835a96c91387e5a0c366b3c9&length=20" +
            "&offset=1&MAC=5b8643686b7db4651f23d52c49f669d8&channel=news_ent";

    public static final String LIST_TIYU = "http://api.sina.cn/sinago/list.json?uid=3f9e4aa687c55acd&" +
            "loading_ad_timestamp=0&platfrom_version=4.4.2&wm=b207&oldchwm=13600_0002&imei=" +
            "A0000038BF5B606&from=6049595012&connection_type=2&chwm=13600_0002&AndroidID=" +
            "43e9ea9e65ffdd6811ea5539107c41d7&v=1&s=20&IMEI=ef2606f6835a96c91387e5a0c366b3c9&" +
            "MAC=5b8643686b7db4651f23d52c49f669d8&channel=news_sports";

    public static final String LIST_KEJI = "http://api.sina.cn/sinago/list.json?uid=3f9e4aa687c55acd" +
            "&loading_ad_timestamp=0&platfrom_version=4.4.2&wm=b207&oldchwm=13600_0002&imei=" +
            "A0000038BF5B606&from=6049595012&connection_type=2&chwm=13600_0002&AndroidID=" +
            "43e9ea9e65ffdd6811ea5539107c41d7&v=1&s=20&IMEI=ef2606f6835a96c91387e5a0c366b3c9" +
            "&MAC=5b8643686b7db4651f23d52c49f669d8&channel=news_tech";
}
