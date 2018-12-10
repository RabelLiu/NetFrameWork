package com.rabel.rxretrofit.Bean;

import java.util.List;

/**
 * Created by LC on 2016/12/5.
 */

public class SongListBean extends BaseBean{


    /**
     * response : {"function":"1002","version":"1.0","family_server_id":"24590b0020a4","songs_type":"闺蜜金曲","songs_count":"100","songs_list":[{"songs_id":"郁可唯/时间煮雨$aa50679(制)@4242.b2h.mkv"},{"songs_id":"陈奕迅/十年$a50392(原+)@4242.m2i.mkv"},{"songs_id":"周杰伦/晴天$j1100009857(原+)@4242.m2j.mkv"},{"songs_id":"孙燕姿/我怀念的$u86007(原+)@4242.m2i.mkv"},{"songs_id":"范玮琪/一个像夏天一个像秋天$ag32613(原+)@4242.m2j.mkv"},{"songs_id":"林忆莲/至少还有你$ip00705(原+)@4242.m2h.mkv"},{"songs_id":"王菲/匆匆那年$hd336187(原+)@4242.a1a.mkv"},{"songs_id":"陈奕迅/好久不见$bw03529(原+)@4242.b4i#.mkv"},{"songs_id":"鹿晗/我们的明天$hd07899@4242(原+).b2a.mkv"},{"songs_id":"梁静茹/分手快乐$aa08744(原+)@4242.m2h.mkv"},{"songs_id":"周华健/朋友$aa08574(原+)@4242.m2m.mkv"},{"songs_id":"莫文蔚/如果没有你$ag32596(原+)@4242.m2h.mkv"},{"songs_id":"牛奶咖啡/明天你好$aa33738(原+)@4242.b2j.mkv"},{"songs_id":"杨千桦/可惜我是水瓶座$aa14010@4242(原+).m2i.mkv"},{"songs_id":"林俊杰/那些你很冒险的梦$hd00013(原+)@4242.b2a.mkv"},{"songs_id":"王筝 曹芳 老狼 小柯/想把我唱给你听$xfg01892(原+)@4242.b4j.mkv"},{"songs_id":"南外学生/北京东路的日子$hz11000143@4242(原+).b2m.mkv"},{"songs_id":"sweety[                     绿光森林                 ]/樱花草$ag33141(原+)@4242.m2j.mkv"},{"songs_id":"胡夏/那些年$hd02561(制)@4242.b2a.mkv"},{"songs_id":"范玮琪/最初的梦想$j1100007948(原+)@4242.m2m.mkv"},{"songs_id":"张学友/一路上有你$j1100000133(原+)@4242.m2h.mkv"},{"songs_id":"五月天/你不是真正的快乐$aa16314(原+)@4242.m2o.mkv"},{"songs_id":"范玮琪/那些花儿$a51359(原+)@4242.m2i.mkv"},{"songs_id":"信乐团/海阔天空$y003107(原+)@4242.m2m.mkv"},{"songs_id":"陈奕迅/最佳损友$u88485@4242(原+).m2m.mkv"},{"songs_id":"吕方/朋友别哭$j1100012988(原+)@4242.m2n.mkv"},{"songs_id":"陈奕迅/陪你度过漫长岁月$aa98172((影)@4242.b2a.mkv"},{"songs_id":"twins/下一站天后$a80438@4242(原+).m2m#.mkv"},{"songs_id":"容祖儿/小小$ba00825(原+)@4242.m2h.mkv"},{"songs_id":"张惠妹/我最亲爱的$aa33126(原+)@4242.b2j.mkv"},{"songs_id":"范玮琪 张韶涵/如果的事$au100383(原+)@4242.m2j.mkv"},{"songs_id":"王菲/致青春$hd333063(影)@4242.b2a.mkv"},{"songs_id":"牛奶咖啡/越长大越孤单$bw02462@4242(原+).b2i.mkv"},{"songs_id":"薛之谦 黄龄/来日方长$ab07352@4242(原+).b2a.mkv"},{"songs_id":"信乐团/天高地厚$y51389(原+)@4242.m2m.mkv"},{"songs_id":"萧敬腾/阿飞的小蝴蝶$aa22380(原+)@4242.m3j.mkv"},{"songs_id":"王若琳/有你的快乐$aa12633(原+)@4242.m2k.mkv"},{"songs_id":"张学友/祝福$aa08675(原+)@4242.m2m.mkv"},{"songs_id":"好妹妹乐队/不说再见$ab01457@4242(原+).b2a.mkv"},{"songs_id":"宋冬野/莉莉安$aa58615@4242(翻).b2a.mkv"},{"songs_id":"范玮琪/可不可以不勇敢$bw00804@4242(原+).b3i.mkv"},{"songs_id":"angela 何炅/栀子花开$hd12581@4242(原+).b2i.mkv"},{"songs_id":"薛凯琪 陈意涵 杨子姗/一起老去$aa83051@3939(原+).b2a.mkv"},{"songs_id":"苏打绿乐团/再遇见$hd333080(原+)@4242.b2a.mkv"},{"songs_id":"五月天/我不愿让你一个人$hd330488(演)@4242.b2a.mkv"},{"songs_id":"李易峰/年少有你$aa87205@4242(原+).b2a.mkv"},{"songs_id":"王菲/矜持$aa58071@4242(制).b3j.mkv"},{"songs_id":"周华健/有没有一首歌会让你想起我$xc03922(原+)@4242.m2i.mkv"},{"songs_id":"徐怀钰/友情卡片$ac25392(原+)@4242.m2a.mkv"},{"songs_id":"杨丞琳/匿名的好友$aa24062(原+)@4242.m2a.mkv"},{"songs_id":"好妹妹乐队/你曾是少年$aa60104(原+)@4242.b2a.mkv"},{"songs_id":"王筝/我们都是好孩子$ag02568@4242(原+).m3j.mkv"},{"songs_id":"刘惜君/那时候的我$aa31653(翻)@4242.b4a.mkv"},{"songs_id":"南拳妈妈/橘子汽水$ag00165(原+)@4242.m2q.mkv"},{"songs_id":"by2/不够成熟$aa11186(原+)@4242.m2a.mkv"},{"songs_id":"郭静/下一个天亮$hd00809(演)@4242.a2a.mkv"},{"songs_id":"自然卷/坐在巷口的那对男女$aa22440(原+)@4242.m2a.mkv"},{"songs_id":"范玮琪/最亲爱的你$hd330872(原+)@4242.a1a.mkv"},{"songs_id":"alin[                     黄丽玲                 ]/好朋友的祝福$hd01404(原+)@4242.b2a.mkv"},{"songs_id":"范玮琪/悄悄告诉你$hd333276(影)@4242.a1a.mkv"},{"songs_id":"bobo组合/光荣$aa09044(原+)@4242.m2j.mkv"},{"songs_id":"陶喆[                     david-tao                 ] 关诗敏/好好说再见$hd332768(原+)@4242.a1a.mkv"},{"songs_id":"可米小子/青春纪念册$bw26638@4242(原+).b2a.mkv"},{"songs_id":"莫文蔚/宝贝$aa27098(原+)@4242.m2a.mkv"},{"songs_id":"魏晨/花开那年$aa73687@4242(原+).e1a.mkv"},{"songs_id":"郭采洁/你在不在$aa21028(原+)@4242.m2a.mkv"},{"songs_id":"范玮琪/我们的纪念日$aa12040(原+)@4242.m2j.mkv"},{"songs_id":"南拳妈妈/再见小时候$aa07064(原+)@4242.m2a.mkv"},{"songs_id":"萧亚轩/潇洒小姐$gq00027(原+)@4242.m1a.mkv"},{"songs_id":"郁可唯/好朋友只是朋友$aa34757@4242(演).b2j.mkv"},{"songs_id":"薛凯琪/给十年后的我$aa19460(原+)@4242.m2a.mkv"},{"songs_id":"莫文蔚/两个女孩$ac25006(原+)@4242.m2i.mkv"},{"songs_id":"郭静/每一天都不同$aa26606(原+)@4242.m2a.mkv"},{"songs_id":"五月天/有些事现在不做一辈子都不会做了$hd03176(原+)@4242.b2a.mkv"},{"songs_id":"f4/第一时间$hd330425(原+)@4242.b2a.mkv"},{"songs_id":"金莎/大小姐$u89223(原+)@4242.m2j.mkv"},{"songs_id":"元卫觉醒/夏天的风$a507040(原+)@4242.m2j.mkv"},{"songs_id":"张惠妹/灵魂尽头$hd338543(影)@4242.a1a.mkv"},{"songs_id":"光良 曹格[                     gary                 ]/少年$g40016(原+)@4242.m2j.mkv"},{"songs_id":"by2/当时的我们$hd13230@4242(原+).b1a.mkv"},{"songs_id":"梁文音/分手后不要做朋友$aa60761@4242(制).b2a.mkv"},{"songs_id":"李治廷/岁月轻狂$aa28155(原+)@4242.l6a.mkv"},{"songs_id":"水木年华/青春再见$hd01511(制)@4242.b2a.mkv"},{"songs_id":"by2/新少女祈祷$aa21379(原+)@4242.m2a.mkv"},{"songs_id":"南拳妈妈/初恋粉色系$ag31891(原+)@4242.m2k.mkv"},{"songs_id":"水木年华/中学时代$g00181(翻)@4242.m2a.mkv"},{"songs_id":"郭书瑶[                     瑶瑶                 ]/爱的抱抱$aa22846(原+)@4242.m2k.mkv"},{"songs_id":"金莎/最近好吗$hb670812@4242(原+).b2i.mkv"},{"songs_id":"飞儿乐团[                     fir                 ]/向日葵盛开的夏天$aa24367(原+)@4242.m2a.mkv"},{"songs_id":"郭静/聊天$aa26179(原+)@4242.m2a.mkv"},{"songs_id":"谢楠/最好的我们$aa27821(原+)@4242.l6a.mkv"},{"songs_id":"twins/女校男生$lg038783@4242(演).b3i.mkv"},{"songs_id":"梁文音/爱一直存在$aa24010(影)@4242.m2a.mkv"},{"songs_id":"twins/朋友的爱$bw39168@4242(原+).b2a.mkv"},{"songs_id":"范玮琪 郭静 张韶涵/仨人$aa09428(原+)@4242.m2j.mkv"},{"songs_id":"黑涩会美眉/女生$u93475(原+)@4242.m2a.mkv"},{"songs_id":"爱朵女孩/花开半夏$aa40206@4242(影).b2a.mkv"},{"songs_id":"李克勤/告别校园时$aa03812(演)@4242.m2a.mkv"},{"songs_id":"杨丞琳/少年维持的烦恼$aa54801(原+)@4242.b2a.mkv"},{"songs_id":"李晓东/冬季校园$l00731(原+)@4242.m2a.mkv"}],"costTotalTime":"本接口总消耗:0.003385秒时长","result_code":"true","msg":""}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * function : 1002
         * version : 1.0
         * family_server_id : 24590b0020a4
         * songs_type : 闺蜜金曲
         * songs_count : 100
         * songs_list : [{"songs_id":"郁可唯/时间煮雨$aa50679(制)@4242.b2h.mkv"},{"songs_id":"陈奕迅/十年$a50392(原+)@4242.m2i.mkv"},{"songs_id":"周杰伦/晴天$j1100009857(原+)@4242.m2j.mkv"},{"songs_id":"孙燕姿/我怀念的$u86007(原+)@4242.m2i.mkv"},{"songs_id":"范玮琪/一个像夏天一个像秋天$ag32613(原+)@4242.m2j.mkv"},{"songs_id":"林忆莲/至少还有你$ip00705(原+)@4242.m2h.mkv"},{"songs_id":"王菲/匆匆那年$hd336187(原+)@4242.a1a.mkv"},{"songs_id":"陈奕迅/好久不见$bw03529(原+)@4242.b4i#.mkv"},{"songs_id":"鹿晗/我们的明天$hd07899@4242(原+).b2a.mkv"},{"songs_id":"梁静茹/分手快乐$aa08744(原+)@4242.m2h.mkv"},{"songs_id":"周华健/朋友$aa08574(原+)@4242.m2m.mkv"},{"songs_id":"莫文蔚/如果没有你$ag32596(原+)@4242.m2h.mkv"},{"songs_id":"牛奶咖啡/明天你好$aa33738(原+)@4242.b2j.mkv"},{"songs_id":"杨千桦/可惜我是水瓶座$aa14010@4242(原+).m2i.mkv"},{"songs_id":"林俊杰/那些你很冒险的梦$hd00013(原+)@4242.b2a.mkv"},{"songs_id":"王筝 曹芳 老狼 小柯/想把我唱给你听$xfg01892(原+)@4242.b4j.mkv"},{"songs_id":"南外学生/北京东路的日子$hz11000143@4242(原+).b2m.mkv"},{"songs_id":"sweety[                     绿光森林                 ]/樱花草$ag33141(原+)@4242.m2j.mkv"},{"songs_id":"胡夏/那些年$hd02561(制)@4242.b2a.mkv"},{"songs_id":"范玮琪/最初的梦想$j1100007948(原+)@4242.m2m.mkv"},{"songs_id":"张学友/一路上有你$j1100000133(原+)@4242.m2h.mkv"},{"songs_id":"五月天/你不是真正的快乐$aa16314(原+)@4242.m2o.mkv"},{"songs_id":"范玮琪/那些花儿$a51359(原+)@4242.m2i.mkv"},{"songs_id":"信乐团/海阔天空$y003107(原+)@4242.m2m.mkv"},{"songs_id":"陈奕迅/最佳损友$u88485@4242(原+).m2m.mkv"},{"songs_id":"吕方/朋友别哭$j1100012988(原+)@4242.m2n.mkv"},{"songs_id":"陈奕迅/陪你度过漫长岁月$aa98172((影)@4242.b2a.mkv"},{"songs_id":"twins/下一站天后$a80438@4242(原+).m2m#.mkv"},{"songs_id":"容祖儿/小小$ba00825(原+)@4242.m2h.mkv"},{"songs_id":"张惠妹/我最亲爱的$aa33126(原+)@4242.b2j.mkv"},{"songs_id":"范玮琪 张韶涵/如果的事$au100383(原+)@4242.m2j.mkv"},{"songs_id":"王菲/致青春$hd333063(影)@4242.b2a.mkv"},{"songs_id":"牛奶咖啡/越长大越孤单$bw02462@4242(原+).b2i.mkv"},{"songs_id":"薛之谦 黄龄/来日方长$ab07352@4242(原+).b2a.mkv"},{"songs_id":"信乐团/天高地厚$y51389(原+)@4242.m2m.mkv"},{"songs_id":"萧敬腾/阿飞的小蝴蝶$aa22380(原+)@4242.m3j.mkv"},{"songs_id":"王若琳/有你的快乐$aa12633(原+)@4242.m2k.mkv"},{"songs_id":"张学友/祝福$aa08675(原+)@4242.m2m.mkv"},{"songs_id":"好妹妹乐队/不说再见$ab01457@4242(原+).b2a.mkv"},{"songs_id":"宋冬野/莉莉安$aa58615@4242(翻).b2a.mkv"},{"songs_id":"范玮琪/可不可以不勇敢$bw00804@4242(原+).b3i.mkv"},{"songs_id":"angela 何炅/栀子花开$hd12581@4242(原+).b2i.mkv"},{"songs_id":"薛凯琪 陈意涵 杨子姗/一起老去$aa83051@3939(原+).b2a.mkv"},{"songs_id":"苏打绿乐团/再遇见$hd333080(原+)@4242.b2a.mkv"},{"songs_id":"五月天/我不愿让你一个人$hd330488(演)@4242.b2a.mkv"},{"songs_id":"李易峰/年少有你$aa87205@4242(原+).b2a.mkv"},{"songs_id":"王菲/矜持$aa58071@4242(制).b3j.mkv"},{"songs_id":"周华健/有没有一首歌会让你想起我$xc03922(原+)@4242.m2i.mkv"},{"songs_id":"徐怀钰/友情卡片$ac25392(原+)@4242.m2a.mkv"},{"songs_id":"杨丞琳/匿名的好友$aa24062(原+)@4242.m2a.mkv"},{"songs_id":"好妹妹乐队/你曾是少年$aa60104(原+)@4242.b2a.mkv"},{"songs_id":"王筝/我们都是好孩子$ag02568@4242(原+).m3j.mkv"},{"songs_id":"刘惜君/那时候的我$aa31653(翻)@4242.b4a.mkv"},{"songs_id":"南拳妈妈/橘子汽水$ag00165(原+)@4242.m2q.mkv"},{"songs_id":"by2/不够成熟$aa11186(原+)@4242.m2a.mkv"},{"songs_id":"郭静/下一个天亮$hd00809(演)@4242.a2a.mkv"},{"songs_id":"自然卷/坐在巷口的那对男女$aa22440(原+)@4242.m2a.mkv"},{"songs_id":"范玮琪/最亲爱的你$hd330872(原+)@4242.a1a.mkv"},{"songs_id":"alin[                     黄丽玲                 ]/好朋友的祝福$hd01404(原+)@4242.b2a.mkv"},{"songs_id":"范玮琪/悄悄告诉你$hd333276(影)@4242.a1a.mkv"},{"songs_id":"bobo组合/光荣$aa09044(原+)@4242.m2j.mkv"},{"songs_id":"陶喆[                     david-tao                 ] 关诗敏/好好说再见$hd332768(原+)@4242.a1a.mkv"},{"songs_id":"可米小子/青春纪念册$bw26638@4242(原+).b2a.mkv"},{"songs_id":"莫文蔚/宝贝$aa27098(原+)@4242.m2a.mkv"},{"songs_id":"魏晨/花开那年$aa73687@4242(原+).e1a.mkv"},{"songs_id":"郭采洁/你在不在$aa21028(原+)@4242.m2a.mkv"},{"songs_id":"范玮琪/我们的纪念日$aa12040(原+)@4242.m2j.mkv"},{"songs_id":"南拳妈妈/再见小时候$aa07064(原+)@4242.m2a.mkv"},{"songs_id":"萧亚轩/潇洒小姐$gq00027(原+)@4242.m1a.mkv"},{"songs_id":"郁可唯/好朋友只是朋友$aa34757@4242(演).b2j.mkv"},{"songs_id":"薛凯琪/给十年后的我$aa19460(原+)@4242.m2a.mkv"},{"songs_id":"莫文蔚/两个女孩$ac25006(原+)@4242.m2i.mkv"},{"songs_id":"郭静/每一天都不同$aa26606(原+)@4242.m2a.mkv"},{"songs_id":"五月天/有些事现在不做一辈子都不会做了$hd03176(原+)@4242.b2a.mkv"},{"songs_id":"f4/第一时间$hd330425(原+)@4242.b2a.mkv"},{"songs_id":"金莎/大小姐$u89223(原+)@4242.m2j.mkv"},{"songs_id":"元卫觉醒/夏天的风$a507040(原+)@4242.m2j.mkv"},{"songs_id":"张惠妹/灵魂尽头$hd338543(影)@4242.a1a.mkv"},{"songs_id":"光良 曹格[                     gary                 ]/少年$g40016(原+)@4242.m2j.mkv"},{"songs_id":"by2/当时的我们$hd13230@4242(原+).b1a.mkv"},{"songs_id":"梁文音/分手后不要做朋友$aa60761@4242(制).b2a.mkv"},{"songs_id":"李治廷/岁月轻狂$aa28155(原+)@4242.l6a.mkv"},{"songs_id":"水木年华/青春再见$hd01511(制)@4242.b2a.mkv"},{"songs_id":"by2/新少女祈祷$aa21379(原+)@4242.m2a.mkv"},{"songs_id":"南拳妈妈/初恋粉色系$ag31891(原+)@4242.m2k.mkv"},{"songs_id":"水木年华/中学时代$g00181(翻)@4242.m2a.mkv"},{"songs_id":"郭书瑶[                     瑶瑶                 ]/爱的抱抱$aa22846(原+)@4242.m2k.mkv"},{"songs_id":"金莎/最近好吗$hb670812@4242(原+).b2i.mkv"},{"songs_id":"飞儿乐团[                     fir                 ]/向日葵盛开的夏天$aa24367(原+)@4242.m2a.mkv"},{"songs_id":"郭静/聊天$aa26179(原+)@4242.m2a.mkv"},{"songs_id":"谢楠/最好的我们$aa27821(原+)@4242.l6a.mkv"},{"songs_id":"twins/女校男生$lg038783@4242(演).b3i.mkv"},{"songs_id":"梁文音/爱一直存在$aa24010(影)@4242.m2a.mkv"},{"songs_id":"twins/朋友的爱$bw39168@4242(原+).b2a.mkv"},{"songs_id":"范玮琪 郭静 张韶涵/仨人$aa09428(原+)@4242.m2j.mkv"},{"songs_id":"黑涩会美眉/女生$u93475(原+)@4242.m2a.mkv"},{"songs_id":"爱朵女孩/花开半夏$aa40206@4242(影).b2a.mkv"},{"songs_id":"李克勤/告别校园时$aa03812(演)@4242.m2a.mkv"},{"songs_id":"杨丞琳/少年维持的烦恼$aa54801(原+)@4242.b2a.mkv"},{"songs_id":"李晓东/冬季校园$l00731(原+)@4242.m2a.mkv"}]
         * costTotalTime : 本接口总消耗:0.003385秒时长
         * result_code : true
         * msg :
         */

        private String function;
        private String version;
        private String family_server_id;
        private String songs_type;
        private String songs_count;
        private String costTotalTime;
        private String result_code;
        private String msg;
        private List<SongsListBean> songs_list;

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getFamily_server_id() {
            return family_server_id;
        }

        public void setFamily_server_id(String family_server_id) {
            this.family_server_id = family_server_id;
        }

        public String getSongs_type() {
            return songs_type;
        }

        public void setSongs_type(String songs_type) {
            this.songs_type = songs_type;
        }

        public String getSongs_count() {
            return songs_count;
        }

        public void setSongs_count(String songs_count) {
            this.songs_count = songs_count;
        }

        public String getCostTotalTime() {
            return costTotalTime;
        }

        public void setCostTotalTime(String costTotalTime) {
            this.costTotalTime = costTotalTime;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<SongsListBean> getSongs_list() {
            return songs_list;
        }

        public void setSongs_list(List<SongsListBean> songs_list) {
            this.songs_list = songs_list;
        }

        public static class SongsListBean {
            /**
             * songs_id : 郁可唯/时间煮雨$aa50679(制)@4242.b2h.mkv
             */

            private String songs_id;

            public String getSongs_id() {
                return songs_id;
            }

            public void setSongs_id(String songs_id) {
                this.songs_id = songs_id;
            }
        }
    }
}


