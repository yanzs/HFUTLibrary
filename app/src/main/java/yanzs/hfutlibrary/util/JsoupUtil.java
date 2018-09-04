package yanzs.hfutlibrary.util;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import yanzs.hfutlibrary.bean.Bean_Inform_Detail;
import yanzs.hfutlibrary.bean.Bean_Inform_GoodBook;
import yanzs.hfutlibrary.bean.Bean_Inform_HotTop;
import yanzs.hfutlibrary.bean.Bean_Inform_Local;
import yanzs.hfutlibrary.bean.Bean_Inform_NewBook;
import yanzs.hfutlibrary.bean.Bean_Inform_Search;
import yanzs.hfutlibrary.bean.Bean_Inform_WillBuy;
import yanzs.hfutlibrary.bean.Bean_Mine_Have;
import yanzs.hfutlibrary.bean.Bean_Mine_Now;
import yanzs.hfutlibrary.bean.Bean_News;
import yanzs.hfutlibrary.bean.responselib.Content;
import yanzs.hfutlibrary.bean.responselib.RootResponseLib;
import yanzs.hfutlibrary.bean.responsemine.Rootclass;
import yanzs.hfutlibrary.bean.responsemine.Rootclasssort;
import yanzs.hfutlibrary.bean.responsemine.Rootmonth;
import yanzs.hfutlibrary.bean.responsemine.Rootmonthsort;
import yanzs.hfutlibrary.bean.responsemine.Rootyear;
import yanzs.hfutlibrary.bean.responsemine.Rootyearsort;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Urls;

public class JsoupUtil {

    public static String getTxtFromHtml(String txt) {
        Document html = Jsoup.parse(txt);
        return html.text();
    }

    public static String getUserName(String txt) {
        Document html = Jsoup.parse(txt);
        Elements name = html.getElementsByClass("profile-name");
        return name.text();
    }

    public static String getUserName(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_PAGE_USER_INFO);
        Document html = Jsoup.parse(txt);
        Elements name = html.getElementsByClass("profile-name");
        return name.text();
    }

    public static int getSimpleSearchPager(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_SEARCH_PAGE_SIMPLE);
        Document html = Jsoup.parse(txt);
        Elements elements = html.getElementsByClass("red");
        if (elements.size() > 0) {
            int n = Integer.parseInt(elements.get(0).text());
            return getPagerNum(n, 20);
        } else {
            return 0;
        }
    }

    public static int getLibSearchPager(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_SEARCH_PAGE_LIB);
        RootResponseLib root = GsonUtil.getResponseRoot(txt);
        assert root != null;
        int num = root.getTotal();
        return getPagerNum(num, 20);
    }

    public static List<Rootclasssort> getMineSortClass(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_GSON_CLASS_SORT);
        Rootclass root = GsonUtil.getResponseRootclass(txt);
        assert root != null;
        return root.getRootclasssort();
    }

    public static List<Rootmonthsort> getMineSortMonth(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_GSON_MONTH_SORT);
        Rootmonth root = GsonUtil.getResponseRootmonth(txt);
        assert root != null;
        return root.getRootmonthsort();
    }

    public static List<Rootyearsort> getMineSortYear(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_GSON_YEAR_SORT);
        Rootyear root = GsonUtil.getResponseRootyear(txt);
        assert root != null;
        return root.getRootyearsort();
    }

    public static List<Bean_Inform_Search> getInformLibSearch(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_SEARCH_PAGE_LIB);
        RootResponseLib root = GsonUtil.getResponseRoot(txt);
        List<Bean_Inform_Search> dataList = new ArrayList<>();
        List<Content> t;
        if (root != null) {
            t = root.getContent();
        } else {
            return dataList;
        }
        for (int i = 0; i < t.size(); i++) {
            String url = Urls.URL_INFORM_PAGER_DETAIL_HOST + t.get(i).getMarcRecNo();
            String title = "" + t.get(i).getNum() + "." + t.get(i).getTitle();
            Bean_Inform_Search beanInformSearch = new Bean_Inform_Search(url, t.get(i).getCallNo(), title, t.get(i).getAuthor(), t.get(i).getPublisher());
            dataList.add(beanInformSearch);
        }
        return dataList;
    }

    public static int getMineHavePager(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_USER_HAVE);
        Document html = Jsoup.parse(txt);
        Elements elements = html.getElementsByTag("option");
        if (elements.size() > 2) {//导航栏上还有两个option
            return Integer.parseInt(elements.last().text());
        } else {
            return 1;
        }
    }

    public static List<Bean_Mine_Have> getMineHave(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_USER_HAVE);
        Document html = Jsoup.parse(txt);
        List<Bean_Mine_Have> dataList = new ArrayList<>();
        Elements elements = html.getElementsByClass("whitetext");
        for (int i = 0; i < elements.size() / 7; i++) {
            String title = elements.get(i * 7).text() + "." + elements.get(i * 7 + 2).text();
            String index = elements.get(i * 7 + 1).text();
            String author = elements.get(i * 7 + 3).text();
            String lendDate = elements.get(i * 7 + 4).text();
            String returnData = elements.get(i * 7 + 5).text();
            String local = elements.get(i * 7 + 6).text();
            String a = elements.get(i * 7 + 2).select("a").toString();
            String url = a.substring(a.indexOf("href") + 8, a.indexOf("\">"));
            Bean_Mine_Have beanMineHave = new Bean_Mine_Have(index, title, author, lendDate, returnData, local, url);
            dataList.add(beanMineHave);
        }
        return dataList;
    }

    public static List<Bean_Mine_Now> getMineNow(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_USER_NOW);
        Document html = Jsoup.parse(txt);
        List<Bean_Mine_Now> dataList = new ArrayList<>();
        Elements elements = html.getElementsByClass("whitetext");
        for (int i = 0; i < elements.size() / 8; i++) {
            String num = elements.get(i * 8).text() ;
            String name = elements.get(i * 8 + 1).text();
            String a = elements.get(i * 8 + 1).select("a").toString();
            String url = Urls.URL_ORIGIN+a.substring(a.indexOf("href") + 8, a.indexOf("\">"));
            String lendData = "借出日期："+elements.get(i * 8 + 2).text();
            String endDate = "归还日期："+elements.get(i * 8 + 3).text();
            String lendNum ="续借量："+ elements.get(i * 8 + 4).text();
            String local = elements.get(i * 8 + 5).text();
            String item = "附件："+elements.get(i * 8 + 6).text();
            String input=elements.get(i*8+7).getElementsByTag("input").toString();
            String check =input.substring(input.indexOf(",")+2,input.lastIndexOf(",")-1);
            Bean_Mine_Now beanMineNow = new Bean_Mine_Now(num,name,lendData,endDate,lendNum,local,item,url,check);
            dataList.add(beanMineNow);
        }
        return dataList;
    }

    public static List<String> getMineInfo(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_USER_INFO);
        Document html = Jsoup.parse(txt);
        Elements tr = html.getElementsByTag("td");
        List<String> dataList = new ArrayList<>();
        dataList.add(tr.get(1).text());
        dataList.add(tr.get(2).text());
        dataList.add(tr.get(3).text());
        dataList.add(tr.get(4).text());
        dataList.add(tr.get(5).text());
        dataList.add(tr.get(6).text());
        dataList.add(tr.get(7).text());
        dataList.add(tr.get(8).text());
        dataList.add(tr.get(12).text());
        dataList.add(tr.get(13).text());
        dataList.add(tr.get(14).text());
        return dataList;
    }

    public static List<Bean_Inform_Search> getInformSimpleSearch(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_SEARCH_PAGE_SIMPLE);
        List<Bean_Inform_Search> dataList = new ArrayList<>();
        Document html = Jsoup.parse(txt);
        Elements elements = html.getElementsByClass("book_list_info");
        for (int i = 0; i < elements.size(); i++) {
            String title = elements.get(i).getElementsByTag("a").get(0).text();
            String a = elements.get(i).getElementsByTag("a").get(0).toString();
            String url = Urls.URL_INFORM_PAGER_DETAIL_HOST + a.substring(a.indexOf("no=") + 3, a.indexOf("\">"));
            String h3 = elements.get(i).getElementsByTag("h3").toString();
            String callNo = h3.substring(h3.lastIndexOf("</a>") + 5, h3.lastIndexOf("</h3>"));
            String t = elements.get(i).getElementsByTag("p").toString();
            String tt = t.substring(t.indexOf("</span>") + 8, t.indexOf("<br> <img") - 1);
            String publisher = tt.substring(0, tt.indexOf("<br>"));
            String author = tt.substring(tt.indexOf("<br>") + 4, tt.indexOf("&nbsp"));
            Bean_Inform_Search beanInformSearch = new Bean_Inform_Search(url, callNo, title, author, publisher);
            dataList.add(beanInformSearch);
        }
        return dataList;
    }

    public static int getPagerNum(int n, int s) {
        if (n % s > 0) {
            return n / s + 1;
        } else {
            return n / s;
        }
    }

    public static int getInformNewBookPager(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_NEW_BOOK);
        Document html = Jsoup.parse(txt);
        Elements font = html.getElementsByTag("font");
        String result = font.last().text();
        if (result.length() > 0) {
            return Integer.parseInt(result);
        } else {
            return 1;
        }
    }

    public static int getInformWillBuyPager(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_WILL_BUY);
        Document html = Jsoup.parse(txt);
        Elements elements = html.getElementsByTag("option");
        if (elements.size() > 2) {//导航栏上还有两个option
            return Integer.parseInt(elements.last().text());
        } else {
            return 1;
        }
    }

    public static int getNewsPager(Context context, String sKey) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, sKey);
        Document html = Jsoup.parse(txt);
        Elements script = html.getElementsByClass("content").get(0).getElementsByTag("script");
        String s = script.toString();
        s = s.substring(s.indexOf("page >"), s.length());
        s = s.substring(7, s.indexOf("){"));
        return Integer.parseInt(s);
    }

    public static List<Bean_News> getNewsLib(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_NEWS_PAGE_LIB_NEWS);
        List<Bean_News> dataList = new ArrayList<>();
        Document html = Jsoup.parse(txt);
        Elements tr = html.getElementsByClass("articlelist2_tr");
        if (tr.size() == 0) {
            return dataList;
        }
        for (int i = 0; i < tr.size(); i++) {
            Elements at = tr.get(i).getElementsByTag("td");
            String date = at.get(2).text();
            Elements a = tr.get(i).getElementsByTag("a");
            String s = a.get(0).toString();
            String url = s.substring(s.indexOf("href=\"") + 6, s.indexOf("htm\"") + 3);
            url = Urls.URL_LIB_HOST + url;
            String title = a.get(0).text();
            String day = date.substring(date.lastIndexOf("-") + 1, date.length());
            String month = date.substring(0, date.lastIndexOf("-"));
            Bean_News beanNews = new Bean_News(day, month, title, url);
            dataList.add(beanNews);
        }
        return dataList;
    }

    public static List<Bean_News> getNewsEdu(Context context, String skey) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, skey);
        List<Bean_News> dataList = new ArrayList<>();
        Document html = Jsoup.parse(txt);
        Elements tem = html.getElementsByClass("b articlelist2_tbl ");
        if (tem.size() == 0) {
            return new ArrayList<>();
        }
        Elements tr = tem.get(0).getElementsByClass("i");
        for (Element t : tr) {
            Elements tbody = t.getElementsByTag("tbody");
            Elements td = tbody.get(0).getElementsByTag("td");
            Elements a = td.get(1).getElementsByTag("a");
            String s = a.get(0).toString();
            String sign = s.substring(s.indexOf("href=\"") + 6, s.indexOf("href=\"") + 10);

            String url = s.substring(s.indexOf("href=\"") + 6, s.indexOf("\" title="));
            if (!sign.equals("http")) {
                url = Urls.URL_EDU_HOST + url;
            }
            String title = a.get(0).text();
            String date = td.get(2).text();
            String day = date.substring(date.lastIndexOf("-") + 1, date.length());
            String month = date.substring(0, date.lastIndexOf("-"));
            Bean_News beanNews = new Bean_News(day, month, title, url);
            dataList.add(beanNews);
        }
        return dataList;
    }

    public static String getNewsHtml(String response) {
        Document html = Jsoup.parse(response);
        Elements pngs = html.select("img[src]");
        for (Element element : pngs) {//保持图片中url完整
            String imgUrl = element.attr("src");
            if (imgUrl.trim().startsWith("/")) {
                imgUrl = Urls.URL_EDU_HOST + imgUrl;
                element.attr("src", imgUrl);
            }
        }
        Elements links = html.select("link[href]");
        for (Element element : links) {//保持加载的css脚本url完整
            String linkUrl = element.attr("href");
            if (linkUrl.trim().startsWith("/")) {
                linkUrl = Urls.URL_EDU_HOST + linkUrl;
                element.attr("href", linkUrl);
            }
        }
        Elements scripts = html.select("script[src]");
        for (Element element : scripts) {//保持加载的js脚本url完整
            String scriptUrl = element.attr("src");
            if (scriptUrl.trim().startsWith("/")) {
                scriptUrl = Urls.URL_EDU_HOST + scriptUrl;
                element.attr("src", scriptUrl);
            }
        }
        Element div = html.getElementById("container_infobody");
        if (div == null) {
            return response;
        }
        String divS = div.toString();//取出所需要的新闻内容
        Elements content = html.select("div");
        content.remove();//除去其余部分
        StringBuffer buffer = new StringBuffer();
        buffer.append(html.toString());//进行html拼接
        buffer.insert(buffer.indexOf("<body>") + 6, divS);
        return buffer.toString();
    }

    public static List<Bean_Inform_NewBook> getInformNewBook(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_NEW_BOOK);
        Document html = Jsoup.parse(txt);
        List<Bean_Inform_NewBook> dataList = new ArrayList<>();
        Elements source = html.getElementsByClass("list_books");
        for (int i = 0; i < source.size(); i++) {
            Elements a = source.get(i).getElementsByTag("a");
            Elements p = source.get(i).getElementsByTag("p");
            Elements h = source.get(i).getElementsByTag("h3");
            String s = a.get(0).toString();
            String url = s.substring(s.indexOf("href=\"") + 8, s.indexOf("title") - 2);

            s = h.get(0).toString();
            String name = a.get(0).text();
            String index = s.substring(s.lastIndexOf("ong>") + 5, s.lastIndexOf("</h3>"));
            String publish = p.get(0).text();
            Bean_Inform_NewBook beanInformNewBook = new Bean_Inform_NewBook(name, url, publish, index);
            dataList.add(beanInformNewBook);
        }
        return dataList;
    }

    public static List<Bean_Inform_WillBuy> getInformWillBuy(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_WILL_BUY);
        Document html = Jsoup.parse(txt);
        List<Bean_Inform_WillBuy> dataList = new ArrayList<>();
        Elements elements = html.getElementsByClass("whitetext");
        for (int i = 0; i < elements.size() / 7; i++) {
            String publisher = elements.get(i * 7 + 3).text();
            String author = elements.get(i * 7 + 2).text();
            String title = elements.get(i * 7).text() + "." + elements.get(i * 7 + 1).text();
            String state = elements.get(i * 7 + 5).text();
            String data = elements.get(i * 7 + 4).text();
            if (publisher.length() == 0) {
                publisher = "暂无信息";
            }
            Bean_Inform_WillBuy beanInformWillBuy = new Bean_Inform_WillBuy(title, author, publisher, data, state);
            dataList.add(beanInformWillBuy);
        }
        return dataList;
    }

    public static List<Bean_Inform_HotTop> getInformHotTop(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_TOP_HOT);
        List<Bean_Inform_HotTop> dataList = new ArrayList<>();
        Document html = Jsoup.parse(txt);
        Elements source = html.getElementsByClass("blue");
        for (int i = 0; i < source.size(); i++) {
            String item = source.get(i).toString();
            String url = item.substring(item.indexOf("href=\"") + 6, item.indexOf("=on\"") + 3);
            String tem = source.get(i).text();
            String value = tem.substring(0, tem.lastIndexOf('('));
            String time = tem.substring(tem.lastIndexOf('(') + 1, tem.lastIndexOf(')'));
            Bean_Inform_HotTop beanInformHotSearch = new Bean_Inform_HotTop(value, time, url);
            dataList.add(beanInformHotSearch);
        }
        return dataList;
    }

    public static List<Bean_Inform_GoodBook> getInformGoodBook(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_GOOD_BOOK);
        List<Bean_Inform_GoodBook> dataList = new ArrayList<>();
        Document html = Jsoup.parse(txt);
        Elements td = html.getElementsByClass("whitetext");
        for (int i = 0; i < td.size() / 8; i++) {
            Elements a = td.get(i * 8 + 1).getElementsByClass("blue");
            String name = a.text();
            String s = a.get(0).toString();
            String url = s.substring(s.indexOf("href=\"") + 8, s.indexOf("\">") - 2);
            String author = td.get(i * 8 + 2).text();
            String publish = td.get(i * 8 + 3).text();
            String index = td.get(i * 8 + 4).text();
            String store = td.get(i * 8 + 5).text();
            String time = td.get(i * 8 + 6).text();
            String scale = td.get(i * 8 + 7).text();
            Bean_Inform_GoodBook beanInformGoodBook = new Bean_Inform_GoodBook(name, url, author, publish, index, store, time, scale);
            dataList.add(beanInformGoodBook);
        }
        return dataList;
    }

    public static Bean_Inform_Detail getInformBookDetail(Context context) {
        String txt = ShareUtil.loadStringData(context, ShareKey.SHARED_KEY, ShareKey.KEY_INFORM_PAGE_BOOK_DETAIL);
        Bean_Inform_Detail beanInformDetail = new Bean_Inform_Detail();
        Document html = Jsoup.parse(txt);
        Elements dt = html.getElementsByClass("booklist");
        Elements tr = html.getElementsByClass("whitetext");
        String book = dt.get(0).select("dd").text();
        int n = book.indexOf("/");
        String name;
        if (n > 0) {
            name = book.substring(0, n);
        } else {
            name = book;
        }
        String writer = book.substring(book.indexOf("/") + 1, book.length());
        beanInformDetail.setName(name);
        beanInformDetail.setWriter(writer);
        for (int i = 0; i < dt.size(); i++) {
            Elements item = dt.get(i).select("dt");
            if (item.text().contains("ISBN") || item.text().equals("ISBN及定价:")) {
                String dd = dt.get(i).select("dd").text();
                int sub = dd.length();
                for (int j = 0; j < dd.length(); j++) {
                    if (!(Character.isDigit(dd.charAt(j)) || dd.charAt(j) == '-')) {
                        sub = j;
                        break;
                    }
                }
                String num = dd.substring(0, sub);
                if (num.length() > 0) {
                    beanInformDetail.setImgUrl(Urls.URL_INFORM_PAGER_DETAIL + num);
                } else {
                    beanInformDetail.setImgUrl("");
                }
                break;
            }
        }
        List<Bean_Inform_Local> beanInformLocals = new ArrayList<>();
        for (int j = 0; j < tr.size(); j++) {
            Elements td = tr.get(j).select("td");
            if (td.size() > 3) {
                Bean_Inform_Local beanInformLocal = new Bean_Inform_Local();
                beanInformLocal.setIndex(td.get(0).text());
                beanInformLocal.setNumber(td.get(1).text());
                beanInformLocal.setLocal(td.get(3).text());
                beanInformLocal.setDate(td.get(4).text());
                beanInformLocals.add(beanInformLocal);
            }
        }
        beanInformDetail.setBeanInformLocals(beanInformLocals);
        return beanInformDetail;
    }


}
