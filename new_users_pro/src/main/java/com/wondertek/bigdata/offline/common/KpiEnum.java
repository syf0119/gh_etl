package com.wondertek.bigdata.offline.common;

/**
 * 统计kpi的名称枚举类
 * @author zhangcheng
 */
public enum KpiEnum {
    //新增用户
    CHANNEL_NEW_USERS("channel_new_users"),
    VERSION_NEW_USERS("version_new_users"),
    AREA_NEW_USERS("area_new_users"),

    NEW_INSTALL_USERS("new_install_users"),
    UPDATE_USERS("update_users"),
    NEW_MEMBERS("new_members"),

    //新增账户
    CHANNEL_NEW_ACCOUNTS("channel_new_accounts"),
    VERSION_NEW_ACCOUNTS("version_new_accounts"),
    AREA_NEW_ACCOUNTS("area_new_accounts"),

    //活跃用户和账户
    CHANNEL_ACTIVE_USERS_ACCOUNTS("channel_active_users_accounts"),
    VERSION_ACTIVE_USERS_ACCOUNTS("version_active_users_accounts"),
    AREA_ACTIVE_USERS_ACCOUNTS("area_active_users_accounts"),

    //沉默用户
    CHANNEL_SILENCE_USER("channel_silence_user"),
    VERSION_SILENCE_USER("version_silence_user"),
    AREA_SILENCE_USER("area_silence_user"),

    //流失用户
    //七天流失用户
    CHANNEL_SEVEN_DAY_LOSE("channel_seven_day_lose"),
    VERSION_SEVEN_DAY_LOSE("version_seven_day_lose"),
    AREA_SEVEN_DAY_LOSE("area_seven_day_lose"),

    //十四天流失用户
    CHANNEL_FOURTEEN_DAY_LOSE("channel_fourteen_day_lose"),
    VERSION_FOURTEEN_DAY_LOSE("version_fourteen_day_lose"),
    AREA_FOURTEEN_DAY_LOSE("area_fourteen_day_lose"),

    //三十天流失用户
    CHANNEL_THIRTY_DAY_LOSE("channel_thirty_day_lose"),
    VERSION_THIRTY_DAY_LOSE("version_thirty_day_lose"),
    AREA_THIRTY_DAY_LOSE("area_thirty_day_lose"),

    //留存用户
    //一日留存
    CHANNEL_ONE_DAY_REMAIN("channel_one_day_remain"),
    VERSION_ONE_DAY_REMAIN("version_one_day_remain"),
    AREA_ONE_DAY_REMAIN("area_one_day_remain"),

    //二日留存
    CHANNEL_TWO_DAY_REMAIN("channel_two_day_remain"),
    VERSION_TWO_DAY_REMAIN("version_two_day_remain"),
    AREA_TWO_DAY_REMAIN("area_two_day_remain"),

    //三日留存
    CHANNEL_THREE_DAY_REMAIN("channel_three_day_remain"),
    VERSION_THREE_DAY_REMAIN("version_three_day_remain"),
    AREA_THREE_DAY_REMAIN("area_three_day_remain"),

    //四日留存
    CHANNEL_FOUR_DAY_REMAIN("channel_four_day_remain"),
    VERSION_FOUR_DAY_REMAIN("version_four_day_remain"),
    AREA_FOUR_DAY_REMAIN("area_four_day_remain"),

    //五日留存
    CHANNEL_FIVE_DAY_REMAIN("channel_five_day_remain"),
    VERSION_FIVE_DAY_REMAIN("version_five_day_remain"),
    AREA_FIVE_DAY_REMAIN("area_five_day_remain"),

    //六日留存
    CHANNEL_SIX_DAY_REMAIN("channel_six_day_remain"),
    VERSION_SIX_DAY_REMAIN("version_six_day_remain"),
    AREA_SIX_DAY_REMAIN("area_six_day_remain"),

    //七日留存
    CHANNEL_SEVEN_DAY_REMAIN("channel_seven_day_remain"),
    VERSION_SEVEN_DAY_REMAIN("version_seven_day_remain"),
    AREA_SEVEN_DAY_REMAIN("area_seven_day_remain"),

    //十四日留存
    CHANNEL_FOURTEEN_DAY_REMAIN("channel_fourteen_day_remain"),
    VERSION_FOURTEEN_DAY_REMAIN("version_fourteen_day_remain"),
    AREA_FOURTEEN_DAY_REMAIN("area_fourteen_day_remain"),

    //三十日留存
    CHANNEL_THIRTY_DAY_REMAIN("channel_thirty_day_remain"),
    VERSION_THIRTY_DAY_REMAIN("version_thirty_day_remain"),
    AREA_THIRTY_DAY_REMAIN("area_thirty_day_remain"),

    //一周留存
    CHANNEL_ONE_WEEK_REMAIN("channel_one_week_remain"),
    VERSION_ONE_WEEK_REMAIN("version_one_week_remain"),
    AREA_ONE_WEEK_REMAIN("area_one_week_remain"),

    //二周留存
    CHANNEL_TWO_WEEK_REMAIN("channel_two_week_remain"),
    VERSION_TWO_WEEK_REMAIN("version_two_week_remain"),
    AREA_TWO_WEEK_REMAIN("area_two_week_remain"),

    //三周留存
    CHANNEL_THREE_WEEK_REMAIN("channel_three_week_remain"),
    VERSION_THREE_WEEK_REMAIN("version_three_week_remain"),
    AREA_THREE_WEEK_REMAIN("area_three_week_remain"),

    //四周留存
    CHANNEL_FOUR_WEEK_REMAIN("channel_four_week_remain"),
    VERSION_FOUR_WEEK_REMAIN("version_four_week_remain"),
    AREA_FOUR_WEEK_REMAIN("area_four_week_remain"),

    //五周留存
    CHANNEL_FIVE_WEEK_REMAIN("channel_five_week_remain"),
    VERSION_FIVE_WEEK_REMAIN("version_five_week_remain"),
    AREA_FIVE_WEEK_REMAIN("area_five_week_remain"),

    //六周留存
    CHANNEL_SIX_WEEK_REMAIN("channel_six_week_remain"),
    VERSION_SIX_WEEK_REMAIN("version_six_week_remain"),
    AREA_SIX_WEEK_REMAIN("area_six_week_remain"),

    //七周留存
    CHANNEL_SEVEN_WEEK_REMAIN("channel_seven_week_remain"),
    VERSION_SEVEN_WEEK_REMAIN("version_seven_week_remain"),
    AREA_SEVEN_WEEK_REMAIN("area_seven_week_remain"),

    //八周留存
    CHANNEL_EIGHT_WEEK_REMAIN("channel_eight_week_remain"),
    VERSION_EIGHT_WEEK_REMAIN("version_eight_week_remain"),
    AREA_EIGHT_WEEK_REMAIN("area_eight_week_remain"),

    //九周留存
    CHANNEL_NINE_WEEK_REMAIN("channel_nine_week_remain"),
    VERSION_NINE_WEEK_REMAIN("version_nine_week_remain"),
    AREA_NINE_WEEK_REMAIN("area_nine_week_remain"),

    //一月留存
    CHANNEL_ONE_MONTH_REMAIN("channel_one_month_remain"),
    VERSION_ONE_MONTH_REMAIN("version_one_month_remain"),
    AREA_ONE_MONTH_REMAIN("area_one_month_remain"),

    //二月留存
    CHANNEL_TWO_MONTH_REMAIN("channel_two_month_remain"),
    VERSION_TWO_MONTH_REMAIN("version_two_month_remain"),
    AREA_TWO_MONTH_REMAIN("area_two_month_remain"),

    //三月留存
    CHANNEL_THREE_MONTH_REMAIN("channel_three_month_remain"),
    VERSION_THREE_MONTH_REMAIN("version_three_month_remain"),
    AREA_THREE_MONTH_REMAIN("area_three_month_remain"),

    //四月留存
    CHANNEL_FOUR_MONTH_REMAIN("channel_four_month_remain"),
    VERSION_FOUR_MONTH_REMAIN("version_four_month_remain"),
    AREA_FOUR_MONTH_REMAIN("area_four_month_remain"),

    //五月留存
    CHANNEL_FIVE_MONTH_REMAIN("channel_five_month_remain"),
    VERSION_FIVE_MONTH_REMAIN("version_five_month_remain"),
    AREA_FIVE_MONTH_REMAIN("area_five_month_remain"),

    //六月留存
    CHANNEL_SIX_MONTH_REMAIN("channel_six_month_remain"),
    VERSION_SIX_MONTH_REMAIN("version_six_month_remain"),
    AREA_SIX_MONTH_REMAIN("area_six_month_remain"),

    //七月留存
    CHANNEL_SEVEN_MONTH_REMAIN("channel_seven_month_remain"),
    VERSION_SEVEN_MONTH_REMAIN("version_seven_month_remain"),
    AREA_SEVEN_MONTH_REMAIN("area_seven_month_remain"),

    //八月留存
    CHANNEL_EIGHT_MONTH_REMAIN("channel_eight_month_remain"),
    VERSION_EIGHT_MONTH_REMAIN("version_eight_month_remain"),
    AREA_EIGHT_MONTH_REMAIN("area_eight_month_remain"),

    //九月留存
    CHANNEL_NINE_MONTH_REMAIN("channel_nine_month_remain"),
    VERSION_NINE_MONTH_REMAIN("version_nine_month_remain"),
    AREA_NINE_MONTH_REMAIN("area_nine_month_remain"),

    //版本升级
    VERSION_UPGRADE("version_upgrade"),

    //回访用户
    CHANNEL_REVISIT_USER("channel_revisit_user"),
    VERSION_REVISIT_USER("version_revisit_user"),
    AREA_REVISIT_USER("area_revisit_user"),

    //ip,uv,pv
    CHANNEL_FLOW_DAY("channel_flow_day"),
    VERSION_FLOW_DAY("version_flow_day"),
    AREA_FLOW_DAY("area_flow_day"),

    //启动次数
    CHANNEL_START_TIME("channel_start_time"),
    VERSION_START_TIME("version_start_time"),
    AREA_START_TIME("area_start_time"),

    //客户端节目访问-统计频道信息
    CHANNEL_PROGRAM_VIEW_MENU("channel_program_view_menu"),
    VERSION_PROGRAM_VIEW_MENU("version_program_view_menu"),
    AREA_PROGRAM_VIEW_MENU("area_program_view_menu"),

    //客户端节目访问-统计频道信息
    CHANNEL_PROGRAM_VIEW_AREA("channel_program_view_area"),
    VERSION_PROGRAM_VIEW_AREA("version_program_view_area"),
    AREA_PROGRAM_VIEW_AREA("area_program_view_area"),

    //客户端节目访问-统计频道信息
    CHANNEL_PROGRAM_VIEW_CONT("channel_program_view_cont"),
    VERSION_PROGRAM_VIEW_CONT("version_program_view_cont"),
    AREA_PROGRAM_VIEW_CONT("area_program_view_cont"),

    //客户端直播节目
    CHANNEL_LIVE_PROGRAM("channel_live_program"),
    VERSION_LIVE_PROGRAM("version_live_program"),
    AREA_LIVE_PROGRAM("area_live_program"),

    //统计分层分级栏目 uv,pv,ip
    FENCENG_FENJI_LANMU("fenceng_fenji_lanmu"),

    //视讯审核人审核内容统计
    AUDITOR_SX("auditor_sx"),

    //动漫审核人审核内容统计
    AUDITOR_DM("auditor_dm"),

    //卓望审核人审核内容统计
    AUDITOR_ZW("auditor_zw"),

    //卓望明细统计
    ZW_DETAIL("zw_detail"),

    //卓望驳回明细统计
    ZW_REFUSE_DETAIL("zw_refuse_detail"),

    //视讯节目变更统计
    PROGRAM_CHANGE("program_change"),

    //视讯审核月度总结
    MONTH_SUMMARY("month_summary"),

    //视讯审核数据去重
    AUDIT_DATA_DISTINCT("audit_data_distinct"),

    //审核驳回类型统计
    REFUSE_TYPE("refuse_type"),

    //审核驳回类型统计
    BC_OVERVIEW("bc_overview"),

    //重复审核明细数据统计
    REPEAT_AUDIT("repeat_audit"),

    //复审数据统计
    BC_REVIEW("bc_review"),

    //动漫复审统计
    DM_REVIEW("dm_review"),
    //动漫/视讯复审截图
    REVIEW_PIC("review_pic"),
    //审核剩余时间明细数据统计
    SP_TIME("sp_time"),

    //咪咕音乐数据统计
    MG_MUSIC("migu_music"),

    //咪咕音乐明细数据统计
    MIGU_MUSIC_DETAIL("migu_music_detail"),

    //视讯模拟平台初审信息明细数据统计
    SX_MODEL_AUDIT("sx_model_audit"),

    //视讯模拟平台复审信息明细数据统计
    RE_MODEL_AUDIT("re_model_audit"),

    //视讯模拟平台修改审批结果明细数据统计
    CK_MODEL_AUDIT("ck_model_audit"),

    //联通沃音乐审核明细统计
    VOD_MUSIC_AUDIT("vod_music_audit"),

    //动漫日常质检
    MG_DP("mg_dp"),

    //动漫专项质检
    DM_SQ("dm_sq"),

    //phoenix 视讯
    PHOENIX_SX("phoenix_sx"),

    //phoenix 动漫
    PHOENIX_DM("phoenix_dm"),

    //热词
    HOT_KEY("search_hot_word");

    public final String name;

    KpiEnum(String name) {
        this.name = name;
    }

    /**
     * 根据kpiType的名称字符串值，获取对应的kpitype枚举对象
     * 
     * @param name
     * @return
     */
    public static KpiEnum valueOfName(String name) {
        for (KpiEnum type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new RuntimeException("指定的name不属于该KpiType枚举类：" + name);
    }

}
