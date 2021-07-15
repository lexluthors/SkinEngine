package com.gyzq.skinengine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyzq.skin.SkinManager;
import com.gyzq.skin.language.LanguageConvert;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String[] permissionStrs = {Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE};
        XXPermissions.with(this)
                .permission(permissionStrs)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {

                    }
                });


        TextView jar_st_text = findViewById(R.id.jar_st_text);

        jar_st_text.setText("加强课堂教学，提高课堂效率。本学期共开校公开课2节，家长开放日1节，执教者认真备课、上课、反思，听课教师认真听课，集体评议，相互研讨，共同促进，形成良好的教研氛围");

        //app-debug.apk
        findViewById(R.id.huanfu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().loadSkin("/sdcard/app-debug.apk");
            }
        });
        findViewById(R.id.chongzhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().resetDefaultSkin();
            }
        });
        findViewById(R.id.jumpToStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TwoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.jar_to_st).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jar转换简繁体
                String st = "上世纪90年代中期，我们学校工会组织教职员工去过南京。由于时间久远，没有留下太多印象。只记得特别参观了南京大屠杀纪念馆、总统府等历史遗迹，以考察学习为目的，使大家深受教育老伴来上海已经12年有余，一直想到南京一游，特别想去瞻仰中山陵。为什么等到现在没有成行。有一个原因就是趁体力还行，往往优先选择稍远的景点，对于附近的景点先不着急。儿子没有忘记母亲的愿望，趁小学刚放暑假，我和老伴马上将回南昌老家前夕，安排了此次南京行程。本打算自驾车出游，考虑到要开车4-5个小时，且市内景点停车难。而高铁1个小时就可到南京，方便快捷、省时省力古都南京，有石头城的美誉，襟江带河，依山傍水，钟山静臥，虎踞龙盘，“六代豪华春犹在”，因厚重的历史文化底蕴，成为中国诸多旅游城市中的翘楚。是首批国家历史文化名城 ，有着7000多年历史 ，是中华文明的重要发祥地 ，中国南方的政治、经济、文化中心 。东吴孙权建都南京(当时叫建业)为南京建都之始，自此“江南佳丽地，" +
                        "金陵帝王州”的南京成为我国四大古都之一，南京因此逐渐有“六朝古都”和“十朝都会”之称。7月2日下午5点多钟我们全家从上海虹桥乘高铁列车抵达千年古城南京，找到预订好了的下榻于秦淮风景区中的酒店后，慕名来到南京大牌档用晚餐。该地是南京人展示独特菜系的品牌店，听说它是南京城里能吃遍全南京菜的地方。数百种民厨小食，田园时蔬，家常烹煮，口味地道。门店支持微信公众号预约取号点餐结账，由于食客太多，因此都要经受耐心等候排队叫号的困扰，排挡前厅及门口，坐满了等候叫号的男女老少，高音喇叭不停播放着食客排队序号，被叫到号的人会兴奋的对号入座。我们在排队近半小时后品尝了一顿金陵大餐 。用过餐后，才体会到什么叫名副其实。南京大排档用食物品质味道征服了所有本地人和外来游客的味蕾。穿梭于桌台间的古装堂倌，成为一道靓丽的风景线，充溢着中华传统民俗风情，气韵古雅，再现清末民初茶楼酒肆之旧貌。" +
                        "从大排档出来，见秦淮河两岸灯火璀璨，游人如织，我们沿着古城墙静静流淌的秦淮河畔，边走边观赏着充溢着迷离色彩的夜景，只见两岸酒家林立，食客满座。一艘艘挂满彩灯的画舫船川流不息地轻盈地驶过，灯影婆娑，歌声悠扬，俨然似一幅如梦似幻的美景奇观" +
                        "早在动身之前儿子就与我们说好，为了让父母游玩尽兴，大部分时间我们单独行动。3日上午是我们南京自由行的第一天。首站是拜蔼中山陵，中山陵是中国近代伟大的民主革命先行者孙中山先生的陵寝，及其附属纪念建筑群，面积8万余平方米。中山陵自1926年春动工，至1929年夏建成，1961年成为首批全国重点文物保护单位，2006年列为首批国家重点风景名胜区和国家AAAAA级旅游景区" +
                        "整个陵墓都用的是青色的琉璃瓦、花岗石墙面，显得庄重肃穆，青色象征青天，也符合中华民国国旗的颜色——青天白日满地红。青天象征中华民族光明磊落、崇高伟大的人格和志气。青色琉璃瓦乃含天下为公之意，以此来显示孙中山为国为民的博大胸怀。" +
                        "中山陵前临平川，背拥青嶂，东毗灵谷寺，西邻明孝陵，整个建筑群依山势而建，由南往北沿中轴线逐渐升高，主要建筑有博爱坊、墓道、陵门、石阶、碑亭、祭堂和墓室等，排列在一条中轴线上，体现了中国传统建筑的风格，从空中往下看，像一座平卧在绿绒毯上的“自由钟”， 含“唤起民众，以建民国”之意。融汇中国古代与西方建筑之精华，气势恢弘、庄严简朴，别创新格" +
                        "孙中山先生的伟大功绩和高尚人格，受到中国各族同胞和世界各国人民的尊敬，八十多年来，每年成千上万全国来自五湖四海的人们，以及世界各地的人士都怀着崇敬的心情前来拜谒中山陵。" +
                        "中山陵拾级而上，需徒步登392级台阶才能到达墓室宫殿。有好心人告诉我们：老年人最好悠着点，上不去就别逞能。然而我们却一气呵成，对于两位均已超过60多岁的老人，是不是好有成就感。我们当即以征服者自居，自豪地将这个信息告诉了亲朋好友们。" +
                        "随后是观赏美玲宫，美玲宫为蒋介石官邸，蒋介石常与宋美龄来此休息和度假，蒋介石也曾多次在此接待外国贵宾。因三四十年代，宋美龄经常在这里做礼拜，与蒋介石在此下榻，便称之为\"美龄宫\"。" +
                        "美龄宫主体建筑是一座三层重檐山式宫殿式建筑，房檐的琉璃瓦上雕着1000多只凤凰，采用传统大屋顶，气势巍峨，装饰以旋子彩绘，富丽堂皇，特别是蓝底云雀琼花图案出自工笔画家陈之佛之手，因此显得独一无二。美龄宫的主体是西洋化--钢筋混凝土结构，耐火砖外墙，大面积落地钢窗，采光充足，气势奢华。二层西侧有一个雕花月亮门，却配上了移门做隔断，细节处无不体现中西合璧的设计理念。四周林木藏盛，终年百花飘香。楼底层为接待室、秘书办公室等，二楼西边是会客室、起居室，东边是蒋介石、宋美龄夫妇卧室。" +
                        "下午游览了明孝陵，明孝陵始建于明洪武十四年，至明永乐三年建成，先后调用军工10万，历时达25年。将人文与自然和谐统一，达到天人合一的完美高度，成为中国传统建筑艺术文化与环境美学相结合的优秀典范。" +
                        "明孝陵作为中国明清皇陵之首，代表了明初建筑和石刻艺术的最高成就，在中国帝陵发展史上有着特殊的地位，故而有\"明清皇家第一陵\"的美誉。" +
                        "一路上，我们穿行在石雕古人及动物伫立两厢的步道上，不时被夸张变形的石雕大象、狮虎及文武百官造型等所触动留影。被叹为观止地石雕技艺所感染。从内心佩服古代艺人的创造才能和智慧。" +
                        "大概在下午4点，我们与儿孙汇合，共同参观了总统府。南京总统府，是中国近代建筑遗存中规模最大、保存最完整的建筑群，也是南京民国建筑的主要代表，中国近代历史的重要遗址，现已辟为中国近代史博物馆。南京总统府建筑群既有中国古代传统的江南园林，也有近代西风东渐时期的建筑遗存。其历史可追溯到明初。1912年1月1日，孙中山在此宣誓就职中华民国临时大总统，辟为大总统府，后来又为南京国民政府总统府。" +
                        "面对总统府门前游客爆棚排队进入的超高人气，不禁让我想到了由画家陈逸飞、魏景山创作的油画《占领总统府》。油画巨作《占领总统府》。也叫《蒋家王朝的覆灭》。是一副气壮山河的现实主义和浪漫主义相结合的油画作品。造型和油彩记住了一个伟大的历史瞬间：中国共产党领导的人民解放军占领南京。宣告国民党反动统治的彻底结束。画面上象征革命胜利的鲜红旗帜升起在伪总统府大楼的顶端。青天白日旗暗淡的蜷缩坠落。值得一提的是，画家大胆地从俯譀天下的角度进行构图，将人民解放军昂扬的斗志和对胜利的欢庆表现得淋漓尽致。《蒋家王朝的覆灭》是一幅鼓舞全国人民精神的优秀作品，叫人看后激动无比，过目难忘";
                System.out.println("开始》》》》》"+System.currentTimeMillis());
                String ts = LanguageConvert.s2t(st);
                System.out.println("结束》》》》》"+System.currentTimeMillis());
                jar_st_text.setText(ts);
            }
        });
    }
}