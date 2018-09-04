package yanzs.hfutlibrary.constant;

public class Values {
    public static final int CONNECT_TIMEOUT = 5;

    public static final String DEFAULT_COLOR = "#000000";

    public static final String LOGIN_NUMBER = "number";
    public static final String LOGIN_PASSWD = "passwd";
    public static final String LOGIN_CAPTCHA = "captcha";
    public static final String LOGIN_SELECT = "select";
    public static final String LOGIN_SELECT_VALUES = "cert_no";

    public static final String HTTP_HOST = "Host";
    public static final String HTTP_ORIGIN = "Origin";
    public static final String HTTP_REFERER = "Referer";

    public static final String GSON_HEAD_CLASS_SORT="{\"rootclasssort\":";
    public static final String GSON_HEAD_YEAR_SORT="{\"rootyearsort\":";
    public static final String GSON_HEAD_MONTH_SORT="{\"rootmonthsort\":";

    public static final String HINT_SYSTEM_ERROR = "出错了。。。。";
    public static final String HINT_LOGIN_ERROR = "登录出错了。。。。";
    public static final String HINT_NET_ERROR = "网络出错了。。。。";
    public static final String HINT_INPUT_ERROR = "请输入完整信息。。。。";
    public static final String HINT_PERMISSION_ERROR = "出现错误，请检查相关权限设置。。。。";
    public static final String HINT_SHOW_ERROR = "暂无信息";

    public static final String HINT_DIALOG_LOGIN = "登录中";
    public static final String HINT_DIALOG_LOAD = "加载中";

    public static final String DETAIL_DIALOG_MINE_LEND_TITLE = "数据来源";
    public static final String DETAIL_DIALOG_MINE_LEND_CONTANT = "数据由宣城校区图书馆提供，仅供参考。";

    public static final String DETAIL_DIALOG_INFORM_DETAIL_TITLE = "数据来源";
    public static final String DETAIL_DIALOG_INFORM_DETAIL_CONTANT = "数据由宣城校区图书馆与豆瓣图书提供，仅供参考。";

    public static final String DETAIL_DIALOG_INFORM_GOODBOOK_TITLE = "详情";
    public static final String DETAIL_DIALOG_INFORM_GOODBOOK_CONTANT = "统计范围为近六个月借阅次数较高的图书，仅供参考。";

    public static final String DETAIL_DIALOG_INFORM_NEWBOOK_TITLE = "详情";
    public static final String DETAIL_DIALOG_INFORM_NEWBOOK_CONTANT = "统计范围为近几个月新购的图书，仅供参考。";

    public static final String DETAIL_DIALOG_INFORM_SEARCH_TITLE = "搜索详情";
    public static final String DETAIL_DIALOG_INFORM_SEARCH_CONTANT = "有少数存在数据丢失的情况，可能是图书馆处暂无信息";

    public static final String DETAIL_DIALOG_INFORM_HOTTOP_TITLE = "热搜详情";
    public static final String DETAIL_DIALOG_INFORM_HOTTOP_CONTANT = "     热搜选取近30天内100个热门检索词，灰色为检索次数，超过500次为热点词，火花标注。";
    public static final String DETAIL_DIALOG_SORT_TITLE = "分类详情";
    public static final String DETAIL_DIALOG_SORT_CONTANT = "   《中国图书馆图书分类法》是我国建国后编制出版的一部具有代表性的大型综合性分类法，简称《中图法》。\n"
            + "   《中图法》的编制始于1971年，先后出版了四版。\n"
            + "   《中图法》与国内其他分类法相比，编制产生年代较晚，但发展很快，它不仅系统地总结了我国分类法的编制经验，而且还吸取了国外分类法的编制理论和技术。 它按照一定的思想观点，以学科分类为基础，结合图书资料的内容和特点，分门别类组成分类表。\n"
            + "    目前，《中图法》已普遍应用于全国各类型的图书馆，国内主要大型书目、检索刊物、机读数据库，以及《中国国家标准书号》等都著录《中图法》分类号。\n"
            + "   《中图法》采用汉语拼音字母与阿拉伯数字相结合的混合号码，用一个字母代表一个大类，以字母顺序反映大类的次序， 大类下细分的学科门类用阿拉伯数字组成。为适应工业技术发展及该类文献的分类，对工业技术二级类目，采用双字母";


    public static final String[] MAIN_TITLE = {"楼层导航", "相关查询", "新闻通知"};
    public static final String[] MINE_LEND_TITLE = {"借阅时间分布", "借阅趋势分布", "借阅分类分布"};

    public static final String[] SORT_GOODBOOK_ITEM = {"马列主义、毛泽东思想、邓小平理论", "哲学、宗教", "社会科学总论", "政治、法律", " 军事", "经济", "文化、科学、教育、体育", "语言、文字", "文学", "艺术", "历史、地理", "自然科学总论", "数理科学与化学", "天文学、地球科学", "生物科学", "医药、卫生", "农业科学", "工业技术", "交通运输", "航空、航天", "环境科学,安全科学", "综合性图书", "总体排行"};
    public static final String[] SORT_NEWBOOK_ITEM = {"马列主义、毛泽东思想、邓小平理论", "哲学、宗教", "社会科学总论", "政治、法律", " 军事", "经济", "文化、科学、教育、体育", "语言、文字", "文学", "艺术", "历史、地理", "自然科学总论", "数理科学与化学", "天文学、地球科学", "生物科学", "医药、卫生", "农业科学", "工业技术", "交通运输", "航空、航天", "环境科学,安全科学", "综合性图书", "全部新书"};
    public static final String[] SORT_NEWBOOK_KEY = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "Z", "ALL"};

    public static final String SORT_INTENT_SIGN = "sort_sign";

    public static final String MINE_INTENT_RENEW_SIGN = "mine_renew_sign";

    public static final String SORT_INTENT_POS_SIGN = "sort_pos_sign";
    public static final String NEWS_INTENT_SIGN = "news_sign";
    public static final String NEWS_INTENT_PAGER_SIGN = "news_pager_sign";
    public static final String NEWS_INTENT_IMG_SIGN = "news_img_sign";

    public static final int SORT_INTENT_SIGN_NEWBOOK = 1;
    public static final int SORT_INTENT_SIGN_GOODBOOK = 3;

    public static final String SEARCH_INTENT_SIGN = "search_intent";

    public static final String[] TAB_NEWS_ITEM_TITLE = {"教务新闻", "教务通知", "图书馆通知"};
    public static final String[] TAB_NEWS_ITEM_SIGN = {"N", "E", "W"};

    public static final String[] TAB_INFORM_ITEM_TITLE = {"热门搜索", "新书通报", "图书荐购", "好书推荐"};
    public static final String[] TAB_INFORM_ITEM_SIGN = {"H", "F", "U", "T"};
    public static final int VIEW_TYPE_NORMAL = 11;//RecyclerView列表的表布局
    public static final int VIEW_TYPE_FOOTER = 10;//RecyclerView列表的表尾布局
    public static final int VIEW_TYPE_HEADER = 9;//RecyclerView列表的表头布局
    public static final String[] INFORM_GOODBOOK_URL_ITEM = {"?cls_no=A", "?cls_no=B", "?cls_no=C", "?cls_no=D", "?cls_no=E", "?cls_no=F", "?cls_no=G", "?cls_no=H", "?cls_no=I", "?cls_no=J", "?cls_no=K", "?cls_no=N", "?cls_no=O", "?cls_no=P", "?cls_no=Q", "?cls_no=R", "?cls_no=S", "?cls_no=T", "?cls_no=U", "?cls_no=V", "?cls_no=X", "?cls_no=Z", ""};

    public static final String[] SEARCH_LOCAL_ITEM={"所有区域","南区二楼《中图法》综合书库","南区五楼《中图法》工业技术类书库","翡翠湖校区《中图法》社科借阅处（A201-202)","翡翠湖校区《中图法》自科借阅处（A301-302)","南中文借书处","翡翠湖校区《科图法》书库（B302)","翡翠湖校区密集书库（B202)","数学学院","南外文借书处","南特藏书库","南过刊借阅处","南报刊阅览室","共达学院一楼借阅处","南教师研究生阅览室","仪器与光电工程学院","人文经济学院","新区报刊阅览室","土木建筑工程学院","共达学院借书处","共达学院图书阅览室","共达学院期刊阅览室","电气与自动化工程学院","外国语学院","建筑与艺术学院","校友著作展示室","宣城校区图书借阅处","南样本及工具书阅览室"};
    public static final String[] SEARCH_LOCAL_KEY_ITEM={"ALL","00036","00028","00338","00339","00021","00321","00337","01007","00022","00024","00025","00031","02004","00034","01001","01002","00335","01004","02001","02002","02003","01003","01005","01006","00045","03001","00027"};
    public static final String[] SEARCH_WAY_ITEM={"简单搜索","馆藏搜索"};

    public static final String[][] INDEX_ITEM={{"1F","报告厅","办公区","","22:15闭馆"}
                                                ,{"2F","阅览区","总服务台","期刊、报纸","自助借还书、自助打印复印、座位预约"}
                                                ,{"3F","阅览区","研讨间301-306室","A类-I类图书","马列主义、毛泽东思想、邓小平理论、哲学、 社会科学总论、政治、法律、 军事、经济、文化、教育、体育、语言、文学"}
                                                ,{"4F","阅览区","","I类-TM类图书","文学、艺术、历史、自然科学总论、数理科学与化学、 医药卫生、工业技术（冶金、金属、机械、仪表、能源与动力、电工等）"}
                                                ,{"5F","阅览区","研讨间501-506室","TN类-Z类图书","工业技术（电子、通信、自动化、计算机、化工、建工、水利等）、交通运输、环境科学、综合性图书"}
                                                ,{"6F","阅览区","研讨间601-608室","备用书库","22:15闭馆"}
                                                ,{"7F","电子阅览室","","","22:15闭馆"}
                                                ,{"8F","培训教室","","","22:15闭馆"}};

    public static final String UESR_DEFAULT_HEAD="use_head_img.jpg";
    public static final String UESR_DEFAULT_BACKGROUND="use_background_img.jpg";


    public static final String TENTENT_KEY= "bMqJvkIyqgcwXOM3RYEx_SISn_5TuKiE";

    public static final String[] SET_ABOUT_TEXT = {"作者", "内测群", "鸣谢"};
    public static final String[] SET_ABOUT_ITEM = {"yanzs", "862584787", ""};

    public static final String DETAIL_DIALOG_SET_ABOUT_THANKS_TITLE = "特别鸣谢";
    public static final String DETAIL_DIALOG_SET_ABOUT_THANKS_CONTANT = "jqorz\n画个逗号给明天〃\n咸菜(⁎⁍̴̛ᴗ⁍̴̛⁎)";
    public static final String DETAIL_DIALOG_SET_ABOUT_INFORM_TITLE = "声明";
    public static final String DETAIL_DIALOG_SET_ABOUT_INFORM_CONTANT = "1.本软件暂时只供合肥工业大学宣城校区的同学使用,合肥校区的同学无法使用此软件中的功能.\n\n" +
            "2.本软件数据皆来自于学校图书馆官网,但是查询系统可能会偶尔无法使用,请稍后再次尝试.\n\n" +
            "3.由于数据兼容性影响，数据可能出现缺失或者错误的情况,仅供参考.\n\n" +
            "4.出现问题请加内测群进行反馈.\n\n";

    public static final String[] DIALOG_MINE_NOW_ITEM = {"图书详情", "图书续借"};
    public static final String DIALOG_MINE_NOW_TITLE = "选项";
}

