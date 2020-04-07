package com.wondertek.bigdata.offline.common;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * 定义日志收集客户端收集得到的用户数据参数的name名称<br/>
 * 以及event_logs这张hbase表的结构信息<br/>
 * 用户数据参数的name名称就是event_logs的列名
 *
 * @author zhangcheng
 */
public class EventLogConstants {
    /**
     * 事件枚举类。指定事件的名称
     *
     * @author gerry
     */
    public enum EventEnum {
        // launch事件，表示第一次安装app访问
        LAUNCH(1, "launch event", "e_la"),
        // 启动app事件
        START(2, "start app event", "e_st"),
        // 页面浏览事件
        PAGEVIEW(3, "page view event", "e_pvi"),
        // 热词搜索事件
        KEY_SEARCH(4, "hot key search event", "e_se"),
        // 审核-视讯
        BC_SX(5, "audit SX", "e_sx"),
        // 审核-动漫
        BC_DM(6, "audit DM", "e_dm"),
        // 复审-视讯
        RW_SX(7, "audit SXRE", "sx_re"),
        // 复审-动漫
        RW_DM(8, "audit DMRE", "dm_re"),
        // 审核-卓望
        BC_ZW(9, "audit ZW", "e_zw"),
        // 直播节目点击事件
        LIVEPROGRAM(10, "live program view event", "e_lp"),
        // 播控平台截图
        BC_PIC(11, "audit PIC", "bc_pic"),
        // 咪咕音乐
        MG_MUSIC(12, "migu MUSIC", "migu_music"),

        /**
         * 播控模拟平台离线统计，审核数据明细--事件类型
         */
        //视讯初审
        SX_AUDIT(13, "sx AUDIT", "e_sim_sx"),
        //视讯复审
        SX_REVIEW(14, "sx REVIEW", "e_re_sim_sx"),
        //修改结果
        MODIFY_RESULT(15, "modify RESULT", "e_rc_sim_sx"),
        //动漫日常质检
        MG_DP(16, "mg DP", "mg_dp"),
        //动漫专项质检
        DM_SQ(17, "dm SQ", "dm_sq"),
        /**
         * 联通沃音乐播控日志时间类型
         */
        UNICOM_VOD_MUSIC(18, "unicom_vodMusic", "unicom_music");


        /**
         * id 唯一标识
         */
        public final int id;
        /**
         * 名称
         */
        public final String name;
        /**
         * 别名，用于数据收集的简写
         */
        public final String alias;

        EventEnum(int id, String name, String alias) {
            this.id = id;
            this.name = name;
            this.alias = alias;
        }

        /**
         * 获取匹配别名的event枚举对象，如果最终还是没有匹配的值，那么直接返回null。
         *
         * @param alias 事件简称
         * @return
         */
        public static EventEnum valueOfAlias(String alias) {
            for (EventEnum event : values()) {
                if (event.alias.equals(alias)) {
                    return event;
                }
            }
            return null;
        }
    }

    /**
     * 平台名称常量类
     */
    public static class PlatformNameConstants {
        public static final String PC_WEBSITE_SDK = "website";
        public static final String JAVA_SERVER_SDK = "java_server";
    }

    /**
     * 审核数据内容类型枚举
     */
    public static class AuditContentType {
        public static final String SX_TYPE = "1";
        public static final String DM_TYPE = "2";
        public static final String ZW_TYPE = "3";
        ///
        public static final String BC_PIC_TYPE = "4";
        public static final String SX_RW_TYPE = "5";
        public static final String DM_RW_TYPE = "6";
    }

    /**
     * 表名称 event_logs
     */
    public static final String HBASE_NAME_EVENT_LOGS = "event_logs";

    /**
     * 表名称 audit_dm
     */
    public static final String HBASE_NAME_AUDIT_DM = "audit_dm";
    /**
     * 表名称 audit_dm_rw
     */
    public static final String HBASE_NAME_AUDIT_DM_RW = "audit_dm_rw";
    /**
     * 表名称 audit_sx_rw
     */
    public static final String HBASE_NAME_AUDIT_SX_RW = "audit_sx_rw";

    /**
     * 表名称 audit_sx
     */
    public static final String HBASE_NAME_AUDIT_SX = "audit_sx";

    /**
     * 表名称 audit_zw
     */
    public static final String HBASE_NAME_AUDIT_ZW = "audit_zw";


    /**
     * 表名称 audit_pic
     */
    public static final String HBASE_NAME_AUDIT_PIC = "audit_pic";

    /**
     * 表名称 mg_music
     */
    public static final String HBASE_NAME_MIGU_MISIC = "mg_music";


    /**
     * 视讯模拟平台初审数据
     * hbase表名称 model_sx_audit
     */
    public static final String HBASE_NAME_MODEL_AUDIT = "model_sx_audit";

    /**
     * 视讯模拟平台复审数据
     * hbase表名称 model_re_audit
     */
    public static final String HBASE_NAME_MODEL_RE = "model_re_audit";

    /**
     * 视讯模拟平台修改结果数据
     * hbase表名称 model_ck_audit
     */
    public static final String HBASE_NAME_MODEL_CK = "model_ck_audit";

    /**
     * 动漫日常质检
     * hbase表名称 mg_dm
     */
    public static final String HBASE_NAME_MG_DP = "mg_dp";
    /**
     * 动漫专项质检
     * hbase表名称 dm_Sq
     */
    public static final String HBASE_NAME_DM_SQ = "dm_sq";

    /**
     * 联通沃音乐播控日志数据
     * hbase表名称 unicom_vod_audit
     */
    public static final String HBASE_NAME_UNICOM_VOD = "unicom_vod_audit";


    /**
     * event_logs表的列簇名称 info
     */
    public static final String EVENT_LOGS_FAMILY_NAME = "info";

    /**
     * event_logs表列簇对应的字节数组
     */
    public static final byte[] EVENT_LOGS_FAMILY_NAME_BYTES = Bytes.toBytes(EVENT_LOGS_FAMILY_NAME);

    /**
     * 日志分隔符
     */
    public static final String LOG_SEPA_A = "\\^A";
    public static final String LOG_SEPA_AND = "&";
    public static final String LOG_PARAM_EQUAL = "=";
    public static final String LOG_PARAM_CHARSET = "UTF-8";

    /**
     * 用户ip地址
     */
    public static final String LOG_COLUMN_NAME_IP = "ip";


    /**
     * 事件名称
     */
    public static final String LOG_COLUMN_NAME_EVENT_NAME = "en";


    /**
     * 定义platform
     */
    public static final String LOG_COLUMN_NAME_PLATFORM = "pl";


    /**
     * 应用
     */
    public static final String LOG_COLUMN_NAME_APP = "app_id";


    /**
     * 客户端渠道
     */
    public static final String LOG_COLUMN_NAME_CHANNEL = "ch_id";


    /**
     * 接口版本
     */
    public static final String LOG_COLUMN_NAME_API_VERSION_NAME = "api_v";


    /**
     * 数据收集端的版本信息
     */
    public static final String LOG_COLUMN_NAME_VERSION = "c_ver";

    /**
     * 版本升级前的旧版本号
     */
    public static final String LOG_COLUMN_NAME_P_VER = "p_ver";


    /**
     * 用户唯一标识符
     */
    public static final String LOG_COLUMN_NAME_UUID = "c_uuid";


    /**
     * 账户唯一标识符
     */
    public static final String LOG_COLUMN_NAME_USER_ID = "user_id";


    /**
     * 会话ID
     */
    public static final String LOG_COLUMN_NAME_SESSION_ID = "c_s_id";


    /**
     * 客户端时间
     */
    public static final String LOG_COLUMN_NAME_CLIENT_TIME = "c_time";


    /**
     * 客户端网络类型，WIFI，4G
     */

    public static final String LOG_COLUMN_NAME_NET_TYPE = "net_t";


    /**
     * 客户端ip
     */

    public static final String LOG_COLUMN_NAME_CLIENT_IP = "ip";


    /**
     * 服务器时间
     */
    public static final String LOG_COLUMN_NAME_SERVER_TIME = "s_time";


    /**
     * 运营商
     */
    public static final String LOG_COLUMN_NAME_ISP = "isp";


    /**
     * ip地址解析的所属国家
     */
    public static final String LOG_COLUMN_NAME_COUNTRY = "country";

    /**
     * ip地址解析的所属省份
     */
    public static final String LOG_COLUMN_NAME_PROVINCE = "province";

    /**
     * ip地址解析的所属城市
     */
    public static final String LOG_COLUMN_NAME_CITY = "city";


    /**
     * 浏览器名称
     */
    public static final String LOG_COLUMN_NAME_BROWSER_NAME = "browser";


    /**
     * 浏览器版本
     */
    public static final String LOG_COLUMN_NAME_BROWSER_VERSION = "browser_v";


    /**
     * 搜索词类型
     */
    public static final String LOG_COLUMN_NAME_AC = "ac";


    /**
     * 关键词
     */
    public static final String LOG_COLUMN_NAME_KW = "kw";


    /**
     * 当前url
     */
    public static final String LOG_COLUMN_NAME_CURRENT_URL = "p_url";


    /**
     * 前一个url
     */
    public static final String LOG_COLUMN_NAME_PREVIOUS_URL = "p_ref";


    /**
     * 内容id
     */
    public static final String LOG_COLUMN_NAME_CONTENT_ID = "co_id";




    /**
     * 内容名称
     */
    public static final String LOG_COLUMN_NAME_CONTENT_NAME = "co_name";


    /**
     * 内容类型
     */
    public static final String LOG_COLUMN_NAME_CONTENT_TYPE = "co_type";


    /**
     * 内容所属自媒号
     */

    public static final String LOG_COLUMN_NAME_CONTENT_CP_ID = "co_cp_id";

    /**
     * 内容归属栏目id
     */

    public static final String LOG_COLUMN_NAME_CONTENT_NAVIGATION_ID = "co_n_id";


    /**
     * 内容对应展现对象ID
     */
    public static final String LOG_COLUMN_NAME_CONTENT_OBJECT_ID = "co_obj_id";


    /**
     * 是否收费
     */

    public static final String LOG_COLUMN_NAME_CONTENT_IS_FREE = "co_is_free";


    /**
     * 对内容的操作动作
     */
    public static final String LOG_COLUMN_NAME_CONTENT_OPERATION = "co_op";

    /**
     * 内容对应频道ID，无频默认值：none
     */
    public static final String LOG_COLUMN_NAME_CONTENT_CHANNEL_ID = "co_ch_id";

    /**
     * 内容对应频道名称，无频道默认值：none
     */
    public static final String LOG_COLUMN_NAME_CONTENT_CHANNEL_NAME = "co_ch_name";


    /**
     *内容对应区块ID，无区块默认值：none
     */
    public static final String LOG_COLUMN_NAME_CONTENT_AREA_ID = "co_area_id";



    /**
     *内容对应区块名称，无区块默认值：none
     */
    public static final String LOG_COLUMN_NAME_CONTENT_AREA_NAME = "co_area_name";













    /**
     * 会话id
     */
    //public static final String LOG_COLUMN_NAME_SESSION_ID = "u_sd";


    /**
     * 语言
     */
    public static final String LOG_COLUMN_NAME_LANGUAGE = "l";

    /**
     * 浏览器user agent参数
     */
    public static final String LOG_COLUMN_NAME_USER_AGENT = "b_iev";


    /**
     * 操作系统名称
     */
    public static final String LOG_COLUMN_NAME_OS_NAME = "os";

    /**
     * 操作系统版本
     */
    public static final String LOG_COLUMN_NAME_OS_VERSION = "os_v";


    // ========================审核数据字段==========================


    /**
     * 视讯审核数据中的内容id，动漫中的内容唯一主键
     */
    public static final String LOG_COLUMN_NAME_C_ID = "c_id";

    /**
     * 动漫复审上一次播控状态
     */
    public static final String LOG_COLUMN_NAME_L_BC_STATUS = "l_bc_status";

    /**
     * 动漫复审上一次播控时间
     */
    public static final String LOG_COLUMN_NAME_L_BC_TIME = "l_bc_time";

    /**
     * 动漫审核中的内容ID
     */
    public static final String LOG_COLUMN_NAME_C_CODE = "c_code";

    /**
     * 审核数据 内容类型
     */
    public static final String LOG_COLUMN_NAME_C_TYPE = "c_type";

    /**
     * 视讯审核数据 同步时间
     */
    public static final String LOG_COLUMN_NAME_SYNC_TIME = "sync_time";

    /**
     * 视讯审核数据 节目ID
     */
    public static final String LOG_COLUMN_NAME_P_ID = "p_id";

    /**
     * 视讯审核数据 内容名称
     */
    public static final String LOG_COLUMN_NAME_NAME = "name";

    /**
     * 视讯审核数据 版权所属cpid
     */
    public static final String LOG_COLUMN_NAME_CR_CP_ID = "cr_cp_id";

    /**
     * 视讯审核数据 产品包id
     */
    public static final String LOG_COLUMN_NAME_PRD_ID = "prd_id";

    /**
     * 视讯审核数据 产品包名称
     */
    public static final String LOG_COLUMN_NAME_PRD_NAME = "prd_name";

    /**
     * 视讯审核数据 业务类型
     */
    public static final String LOG_COLUMN_NAME_PRD_TYPE = "prd_type";

    /**
     * 视讯审核数据 播控状态
     */
    public static final String LOG_COLUMN_NAME_BC_STATUS = "bc_status";

    /**
     * 视讯审核数据 播控人帐号
     */
    public static final String LOG_COLUMN_NAME_BC_PERSON = "bc_person";

    /**
     * 视讯审核数据 播控时间
     */
    public static final String LOG_COLUMN_NAME_BC_TIME = "bc_time";

    /**
     * 动漫审核数据 cpid
     */
    public static final String LOG_COLUMN_NAME_CP_ID = "cp_id";

    /**
     * 动漫审核数据 cp名称/up主
     */
    public static final String LOG_COLUMN_NAME_CP_NAME = "cp_name";

    /**
     * 动漫审核数据 作品类型
     */
    public static final String LOG_COLUMN_NAME_C_TYPE_NAME = "c_type_name";

    /**
     * 动漫审核数据 内容状态
     */
    public static final String LOG_COLUMN_NAME_STATUS = "status";

    /**
     * 动漫审核数据 单集时长，单位秒，单部时长默认为0
     */
    public static final String LOG_COLUMN_NAME_DURA = "dura";
    /**
     * 动漫复审播控时间
     */
    public static final String LOG_COLUMN_NAME_RE_TIME = "re_time";
    /**
     * 动漫复审审核结果
     */
    public static final String LOG_COLUMN_NAME_RE_RESULT = "re_result";
    /**
     * 动漫审核数据，审核使用时长，单位秒，待播控为空
     */
    public static final String LOG_COLUMN_NAME_CH_DU_TIME = "ch_du_time";

    /**
     * 动漫审核 内容名称
     */
    public static final String LOG_COLUMN_DM_c_name = "c_name";
    /**
     * 动漫审核数据，审核人
     */
    public static final String LOG_COLUMN_NAME_OPERATOR = "operator";

    /**
     * 卓望审核 模板ID
     */
    public static final String LOG_COLUMN_NAME_TEMPLATE_ID = "template_Id";

    /**
     * 卓望审核 内容标识
     */
    public static final String LOG_COLUMN_NAME_T_STATUS = "t_Status";

    /**
     * 卓望审核 审核状态
     */
    public static final String LOG_COLUMN_NAME_VERIFY = "verify";

    /**
     * 卓望审核 内容优先级
     */
    public static final String LOG_COLUMN_NAME_T_PRIORITY = "t_Priority";


    /**
     * 卓望审核 变量文件ID
     */
    public static final String LOG_COLUMN_NAME_VARFILE_ID = "varfile_Id";

    /**
     * 卓望审核 模板名称
     */
    public static final String LOG_COLUMN_NAME_TEMPLATE_NAME = "template_Name";

    /**
     * 卓望审核 模板所属产品包
     */
    public static final String LOG_COLUMN_NAME_PRODUCT_NAME = "product_Name";

    /**
     * 卓望审核 模板所属产品包
     */
    public static final String LOG_COLUMN_NAME_CREATE_TIME = "create_Time";


    /**
     * 卓望审核 驳回
     */
    public static final String LOG_COLUMN_NAME_JSON_REFUSE = "jsonRefuse";


    /**
     * 播控驳回原因
     */
    public static final String LOG_COLUMN_NAME_REFUSE_R = "bc_refuse_r";

    /**
     * 播控驳回类型
     */
    public static final String LOG_COLUMN_NAME_REFUSE_T = "refuse_t";

    /**
     * 审核剩余时间
     */
    public static final String LOG_COLUMN_NAME_SP_TIME = "sp_time";

    /**
     * 是否删除
     */
    public static final String LOG_COLUMN_NAME_IS_DEL = "is_delete";
    /**
     * 是否是播控平台审核用户
     */
    public static final String LOG_COLUMN_NAME_SRC_LOG = "src_log";

    /**
     * 上次播控人
     */
    public static final String LOG_COLUMN_NAME_L_BC_P = "l_bc_person";

    /**
     * 是否复审 视讯 、动漫字段一致
     */
    public static final String LOG_COLUMN_NAME_IS_BC_RE = "is_bc_review";
    //动漫复审
    public static final String DM_COLUMN_NAME_IS_BC_RE = "bc_is_review";

    //播控平台截图数据
    //操作人
    public static final String LOG_COLUMN_NAME_OPERTOR = "opertor";
    //图片来源
    public static final String LOG_COLUMN_NAME_SRC_TYPE = "src_type";
    //操作时间
    public static final String LOG_COLUMN_NAME_OPT_TIME = "opt_time";
    //图片地址
    public static final String LOG_COLUMN_NAME_RE_PIC_ADDR = "re_pic_addr";
    //图片描述
    public static final String LOG_COLUMN_NAME_RE_PIC_DESC = "re_pic_desc";

    //=========================咪咕音乐日志字段================
    //审核方
    public static final String LOG_COLUMN_NAME_CHECK_TYPE = "check_type";
    //    //平台生成的唯一主键ID
//    public static final String LOG_COLUMN_NAME_CONTENT_ID = "content_id";
    //无锡播控结果
    public static final String LOG_COLUMN_NAME_WX_BC_STATUS = "bc_status";
    //易橙播控结果
    public static final String LOG_COLUMN_NAME_YC_BC_STATUS = "z_bc_status";
    //所属磁盘
    public static final String LOG_COLUMN_NAME_DISC_NUM = "disc_num";
    //无锡播控人
    public static final String LOG_COLUMN_NAME_WX_BC_PERSON = "bc_person";
    //易橙播控人
    public static final String LOG_COLUMN_NAME_YC_BC_PERSON = "z_bc_Person";
    //创建时间
    public static final String LOG_COLUMN_NAME_MG_CREATE_TIME = "create_time";
    //无锡播控时间
    public static final String LOG_COLUMN_NAME_WX_BC_TIME = "bc_time";
    //易橙播控时间
    public static final String LOG_COLUMN_NAME_YC_BC_TIME = "z_bc_time";

    //=========================视讯播控模拟平台日志字段================
    //内容ID
    public static final String LOG_COLUMN_NAME_CID = "c_id";
    //节目ID
    public static final String LOG_COLUMN_NAME_PID = "p_id";
    //内容名称
    public static final String LOG_COLUMN_NAME_CNAME = "name";
    //一级分类ID
    public static final String LOG_COLUMN_NAME_DISPLAY_TYPE = "displayType";
    //一级分类名称
    public static final String LOG_COLUMN_NAME_DISPLAY_NAME = "displayName";
    //播控状态
    public static final String LOG_COLUMN_NAME_MODEL_BC_STATUS = "bc_status";
    //播控人
    public static final String LOG_COLUMN_NAME_MODEL_BC_PERSON = "bc_person";
    //播控时间
    public static final String LOG_COLUMN_NAME_MODEL_BC_TIME = "bc_time";
    //上次播控状态
    public static final String LOG_COLUMN_NAME_MODEL_L_BC_STATUS = "l_bc_status";
    //当en = e_rc_sim_sx 时有值，否则为null
    public static final String LOG_COLUMN_NAME_CHECK_RESULT = "check_result";

    public static final String LOG_COLUMN_NAME_SRC = "c_src";

    //=========================动漫专项质检日志字段================
    //记录ID
    public static final String LOG_COLUMN_NAME_REPORT_ID = "report_id";
    //内容ID
    public static final String LOG_COLUMN_NAME_CONTENT_CODE = "content_code";
    //内容ID
    public static final String LOG_COLUMN_NAME_OBJECT_ID = "object_id";
    //cpID
    public static final String LOG_COLUMN_NAME_CPID = "cpID";
    //内容名称
    //public static final String LOG_COLUMN_NAME_CONTENT_NAME = "content_name";
    //内容类型
    public static final String LOG_COLUMN_NAME_CONTENTYPE_NAME = "contentType_name";
    //审核状态
    public static final String LOG_COLUMN_NAME_AUDIT_STATUS = "audit_status";
    //审核人
    public static final String LOG_COLUMN_NAME_OPERATOR_ = "operator_";
    //审核驳回原因
    public static final String LOG_COLUMN_NAME_APPROVE_OPINION = "approve_opinion";
    //审核时间
    public static final String LOG_COLUMN_NAME_APPROVE_TIME = "approveTime";

    //=========================联通沃音乐播控日志字段================
    //内容ID
    public static final String LOG_COLUMN_NAME_UNICOM_CID = "cont_id";
    //节目ID
    public static final String LOG_COLUMN_NAME_UNICOM_PID = "program_id";
    //内容名称
    public static final String LOG_COLUMN_UNICOM_NAME_CNAME = "content_name";
    //播控状态  --  0：待播控  1：播控通过  2：播控拒绝
    public static final String LOG_COLUMN_NAME_UNICOM_BC_STATUS = "bc_status";
    //播控人
    public static final String LOG_COLUMN_NAME_UNICOM_BC_PERSON = "bc_person";
    //注入时间
    public static final String LOG_COLUMN_NAME_UNICOM_CREATE_TIME = "create_time";
    //播控理由
    public static final String LOG_COLUMN_NAME_UNICOM_BC_REASON = "bc_reason";
    //播控时间
    public static final String LOG_COLUMN_NAME_UNICOM_BC_TIME = "bc_time";
    //审核级别
    public static final String LOG_COLUMN_NAME_UNICOM_PRIORITY = "priority";
    //最终审核剩余时间
    public static final String LOG_COLUMN_NAME_UNICOM_CHECK_LASTTIME = "check_lastTime";

    //=========================客户端访问日志字段================

    /**
     * 节目属性
     */
    public static final String LOG_COLUMN_NAME_ATTR_VALUE = "attr_value";

    /**
     * 内容名称
     */
    public static final String LOG_COLUMN_NAME_C_NAME = "c_name";

    /**
     * 区块id
     */
    public static final String LOG_COLUMN_NAME_AREA_ID = "area_id";

    /**
     * 区块名称
     */
    public static final String LOG_COLUMN_NAME_AREA_NAME = "area_name";

    /**
     * 频道id
     */
    public static final String LOG_COLUMN_NAME_MENU_ID = "menu_id";

    /**
     * 频道名称
     */
    public static final String LOG_COLUMN_NAME_MENU_NAME = "menu_name";

    /**
     * 播放类型 1、看视屏 2、听视频 0、all
     */
    public static final String LOG_COLUMN_NAME_P_TYPE = "p_type";

    /**
     * 直播类型 1、直播 2、回放 0、all
     */
    public static final String LOG_COLUMN_NAME_L_TYPE = "l_type";

    /**
     * 正在播放的节目id
     */
    public static final String LOG_COLUMN_NAME_L_ID = "l_id";

    /**
     * 正在播放的节目名称
     */
    public static final String LOG_COLUMN_NAME_L_NAME = "l_name";

    /**
     * 分层分级属性 栏目
     */
    public static final String LOG_COLUMN_NAME_LANMU = "栏目";
}
